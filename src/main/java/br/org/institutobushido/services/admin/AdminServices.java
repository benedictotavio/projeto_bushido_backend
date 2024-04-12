package br.org.institutobushido.services.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.mappers.admin.AdminMapper;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.repositories.AdminRepositorio;
import br.org.institutobushido.resources.exceptions.AlreadyRegisteredException;

@Service
public class AdminServices implements AdminServiceInterface, UserDetailsService {

    @Value("${jwt.secret}")
    private String secret;

    private AdminRepositorio adminRepositorio;
    private MongoTemplate mongoTemplate;

    public AdminServices(AdminRepositorio adminRepositorio, MongoTemplate mongoTemplate) {
        this.adminRepositorio = adminRepositorio;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void signup(SignUpDTORequest adminDTORequest) {

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

        adminRepositorio.save(admin);
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
                    .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000))
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

    public List<AdminDTOResponse> buscarAdminPorNome(String nome) {
        Query query = new Query();
        query.fields().exclude("senha");
        query.addCriteria(Criteria.where("nome").regex(nome, "si"));
        return AdminMapper.mapToListAdminDTOResponse(this.mongoTemplate.find(query, Admin.class));
    }
}
