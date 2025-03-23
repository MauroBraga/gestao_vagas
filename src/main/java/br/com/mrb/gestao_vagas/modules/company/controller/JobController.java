package br.com.mrb.gestao_vagas.modules.company.controller;

import br.com.mrb.gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import br.com.mrb.gestao_vagas.modules.company.usecase.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    public ResponseEntity<Object> create(@Valid  @RequestBody CreateJobDTO dto, HttpServletRequest request) {
        try {
            var company_id=request.getAttribute("company_id");

            var entity=JobEntity.builder().benefits(dto.getBenefits())
                    .description(dto.getDescription())
                    .companyId(UUID.fromString(company_id.toString()))
                    .levels(dto.getLevel()).build();
            var retorno = this.createJobUseCase.execute(entity);
            return ResponseEntity.ok(retorno);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
