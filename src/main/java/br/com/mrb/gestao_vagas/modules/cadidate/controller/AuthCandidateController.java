package br.com.mrb.gestao_vagas.modules.cadidate.controller;

import br.com.mrb.gestao_vagas.modules.cadidate.dto.AuthCandidateRequestDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.AuthCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    private AuthCandidateUseCase authCandidateUseCase;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        try{
            var auth = this.authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok(auth);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
