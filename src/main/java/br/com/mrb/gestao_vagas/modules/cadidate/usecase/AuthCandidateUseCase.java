package br.com.mrb.gestao_vagas.modules.cadidate.usecase;

import br.com.mrb.gestao_vagas.modules.cadidate.dto.AuthCandidateRequestDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.AuthCandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secret;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate= this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                .orElseThrow(() ->{
                    throw new UsernameNotFoundException("Username/password incorreto");
                }) ;

        if(passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword())){
            throw new AuthenticationException("Password not match");
        }
        var experis_in = Instant.now().plus(Duration.ofMinutes(10));
        var roles = Arrays.asList("CANDIDATE");
        //Gerar Token
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var token = JWT.create()
                .withIssuer("javagas")
                .withExpiresAt(experis_in)
                .withClaim("roles",roles)
                .withSubject(candidate.getId().toString())
                .sign(algorithm);

        return AuthCandidateResponseDTO.builder().accessToken(token).roles(roles).expires_in(experis_in.toEpochMilli()).build();

    }
}
