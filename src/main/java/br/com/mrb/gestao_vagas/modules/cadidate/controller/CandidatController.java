package br.com.mrb.gestao_vagas.modules.cadidate.controller;

import br.com.mrb.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.CreateCandidateUseCase;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidates")
public class CandidatController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid  @RequestBody CandidateEntity candidateEntity) {
        try {
            var candidate = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(candidate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/")
    public ResponseEntity<Object> getId(HttpServletRequest request) {
        var idCandidate = request.getSession().getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok(profile);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
