package br.com.mrb.gestao_vagas.modules.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

public class TestUtils {

    public static String objectToJson(Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static String generateTokenCompany(UUID companyId) {

        var expireIn = Instant.now().plus(Duration.ofHours(2));
        //Gerar Token
        Algorithm algorithm = Algorithm.HMAC256("JAVAGAS_@123#");
        var token = JWT.create().withIssuer("javagas")
                .withExpiresAt(expireIn)
                .withSubject(companyId.toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        return token;
    }
}
