package br.com.mrb.gestao_vagas.modules.company.usecase;

import br.com.mrb.gestao_vagas.exceptions.UserFoundException;
import br.com.mrb.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.mrb.gestao_vagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.mrb.gestao_vagas.modules.company.repository.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secret;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = companyRepository.findByUsername(authCompanyDTO.getUsername())
                .orElseThrow(() -> {throw new UserFoundException("Username/password incorrect");});

        //Verificar a senha são iguais
        var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());
        if (!passwordMatches) {
            throw new AuthenticationException("Password not match");
        }
        var expireIn = Instant.now().plus(Duration.ofHours(2));
        //Gerar Token
        Algorithm algorithm = Algorithm.HMAC256(secret);

        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expireIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var roles = Arrays.asList("COMPANY");

        return AuthCompanyResponseDTO.builder().accessToken(token).roles(roles).expires_in(expireIn.toEpochMilli()).build();
    }
}
