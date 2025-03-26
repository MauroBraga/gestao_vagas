package br.com.mrb.gestao_vagas.modules.cadidate.controller;

import br.com.mrb.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.CreateCandidateUseCase;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.ListAllJobsFilterUseCase;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.ProfileCandidateUseCase;
import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsFilterUseCase listAllJobsFilterUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid  @RequestBody CandidateEntity candidateEntity) {
        try {
            var candidate = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok(candidate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidato", description = "informações do candidato")
    @Operation(summary = "Perfil do candidato",description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema =@Schema(implementation = ProfileCandidateResponseDTO.class) ), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "UserNotFound")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> getId(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok(profile);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Tag(name = "Candidato", description = "informações do candidato")
    @Operation(summary = "Listagem de vagas disponíveis parar o candidato",description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema =@Schema(implementation = JobEntity.class) ), mediaType = "application/json")
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findJobByFilter(@RequestParam String filter) {
        try {
            return ResponseEntity.ok(this.listAllJobsFilterUseCase.execute(filter));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
