package br.com.mrb.gestao_vagas.modules.cadidate.controller;

import br.com.mrb.gestao_vagas.modules.cadidate.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.CreateCandidateUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidates")
public class CandidatController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid  @RequestBody CandidateEntity candidateEntity) {
        try {
            var candidate = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(candidate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
