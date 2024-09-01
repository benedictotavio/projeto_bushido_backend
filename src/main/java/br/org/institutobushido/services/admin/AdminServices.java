package br.org.institutobushido.services.admin;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import br.org.institutobushido.controllers.dtos.admin.AdminDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.login.LoginDTOResponse;
import br.org.institutobushido.controllers.dtos.admin.signup.SignUpDTORequest;
import br.org.institutobushido.models.admin.Admin;
import br.org.institutobushido.providers.mappers.admin.AdminMapper;
import br.org.institutobushido.providers.utils.resources.exceptions.AlreadyRegisteredException;
import br.org.institutobushido.providers.utils.resources.exceptions.EntityNotFoundException;
import br.org.institutobushido.repositories.AdminRepositorio;

@Service
public class AdminServices implements AdminServicesInterface, UserDetailsService {

    @Value("${jwt.secret}")
    private String secret;

    private static final long ONE_HOUR_IN_MILLIS = 3600000;

    private final AdminRepositorio adminRepositorio;
    private final MongoTemplate mongoTemplate;

    public AdminServices(AdminRepositorio adminRepositorio, MongoTemplate mongoTemplate) {
        this.adminRepositorio = adminRepositorio;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void signup(SignUpDTORequest adminDTORequest) {

        UserDetails adminEncontrado = adminRepositorio.findByEmail(adminDTORequest.email());

        if (adminEncontrado != null) {
            throw new AlreadyRegisteredException(
                    "O Administrador com o email " + adminDTORequest.email() + " ja esta cadastrado!");
        }

        Admin admin = new Admin(
                adminDTORequest.nome(),
                adminDTORequest.email(),
                new BCryptPasswordEncoder().encode(adminDTORequest.senha()),
                adminDTORequest.cargo(),
                adminDTORequest.role());

        adminRepositorio.save(admin);
    }

    @Override
    public LoginDTOResponse login(Admin admin) {
        String token = this.generateToken(admin);
        return new LoginDTOResponse(token, admin.getRole().getValue(), admin.getTurmas());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws EntityNotFoundException {
        UserDetails adminEncontrado = adminRepositorio.findByEmail(username);
        if (adminEncontrado == null) {
            throw new EntityNotFoundException("O Administrador com o email " + username + " nao foi encontrado!");
        }
        return adminEncontrado;
    }

    protected String generateToken(Admin admin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
            return JWT.create()
                    .withIssuer(secret)
                    .withClaim("email", admin.getEmail())
                    .withSubject(admin.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + ONE_HOUR_IN_MILLIS))
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
