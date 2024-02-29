package br.org.institutobushido.services.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.org.institutobushido.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.dtos.admin.signup.SignUpDTOResponse;
import br.org.institutobushido.model.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;

@Service
public class AdminServices implements AdminServiceInterface, UserDetailsService {

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private AdminRepositorio adminRepositorio;

    @Override
    public SignUpDTOResponse signup(SignUpDTORequest adminDTORequest) {

        UserDetails adminEncontrado = adminRepositorio.findByEmail(adminDTORequest.email());

        if (adminEncontrado != null) {
            throw new AlreadyRegisteredException(
                    "O Administrador com o rg " + adminDTORequest.email() + " ja esta cadastrado!");
        }

        Admin admin = new Admin();
        admin.setNome(adminDTORequest.nome());
        admin.setEmail(adminDTORequest.email());
        admin.setSenha(new BCryptPasswordEncoder().encode(adminDTORequest.senha()));
        admin.setCargo(adminDTORequest.cargo());
        admin.setRole(adminDTORequest.role());

        Admin novoAdmin = adminRepositorio.save(admin);

        return SignUpDTOResponse.builder()
                .withEmail(novoAdmin.getEmail())
                .withNome(novoAdmin.getNome())
                .build();
    }

    @Override
    public LoginDTOResponse login(Admin admin) {
        var token = this.generateToken(admin);
        return new LoginDTOResponse(token);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails adminEncontrado = adminRepositorio.findByEmail(username);
        if (adminEncontrado == null) {
            throw new UsernameNotFoundException("O Administrador com o email " + username + " nao foi encontrado!");
        }
        return adminEncontrado;
    }

    public String generateToken(Admin admin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.create()
                    .withIssuer(secret)
                    .withClaim("email", admin.getEmail())
                    .withSubject(admin.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new JWTCreationException("Error ao gerar token", e);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(secret).build().verify(token);
        return decodedJWT.getSubject();
    }
}
