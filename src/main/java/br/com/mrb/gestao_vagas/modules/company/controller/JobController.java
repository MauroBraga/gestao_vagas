package br.com.mrb.gestao_vagas.modules.company.controller;

import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import br.com.mrb.gestao_vagas.modules.company.usecase.CreateJobUseCase;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping
    public ResponseEntity<Object> create(@Valid  @RequestBody JobEntity entity) {
        try {
            var retorno = this.createJobUseCase.execute(entity);
            return ResponseEntity.ok(retorno);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
}
