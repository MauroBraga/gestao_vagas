package br.com.mrb.gestao_vagas.modules.cadidate.controller;

import br.com.mrb.gestao_vagas.modules.cadidate.dto.CandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.CandidateResquestDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.ApplyJobCandidateUseCase;
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
@Tag(name = "Candidato", description = "informações do candidato")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsFilterUseCase listAllJobsFilterUseCase;

    @Autowired
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @PostMapping
    @Operation(summary = "Cadastro do candidato",description = "Essa função é responsável por cadastrar um candidato.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema =@Schema(implementation = CandidateResponseDTO.class) ), mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid  @RequestBody CandidateResquestDTO candidateResquestDTO) {
        try {
            var candidate = this.createCandidateUseCase.execute(candidateResquestDTO);
            return ResponseEntity.ok(candidate);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
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
    @Operation(summary = "Listagem de vagas disponíveis parar o candidato",description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema =@Schema(implementation = JobEntity.class) ), mediaType = "application/json")
            })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findJobByFilter(@RequestParam String filter) {
        try {
            var jobs =this.listAllJobsFilterUseCase.execute(filter);
            return ResponseEntity.ok(jobs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga", description = "Essa função é responsável por realizar a inscrição do candidato em uma vaga.")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody UUID idJob) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var applyJob =  this.applyJobCandidateUseCase.execute(UUID.fromString(idCandidate.toString()),idJob);
            return ResponseEntity.ok(applyJob);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
