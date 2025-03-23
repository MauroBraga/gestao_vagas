package br.com.mrb.gestao_vagas.modules.company.controller;

import br.com.mrb.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.mrb.gestao_vagas.modules.company.usecase.CreateCompanyUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid  @RequestBody CompanyEntity company) {
        try {
            var candidate = this.createCompanyUseCase.execute(company);
            return ResponseEntity.ok(candidate);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
