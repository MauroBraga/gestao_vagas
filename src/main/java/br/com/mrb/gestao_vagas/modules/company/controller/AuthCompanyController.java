package br.com.mrb.gestao_vagas.modules.company.controller;

import br.com.mrb.gestao_vagas.modules.company.dto.AuthCompanyDTO;
import br.com.mrb.gestao_vagas.modules.company.usecase.AuthCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping(value = "/auth")
    public ResponseEntity create(@RequestBody AuthCompanyDTO authCompanyDTO){

        try {
            var token=this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}
