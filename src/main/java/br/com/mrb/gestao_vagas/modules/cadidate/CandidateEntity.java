package br.com.mrb.gestao_vagas.modules.cadidate;

import lombok.Data;

@Data
public class CandidateEntity {
    private String name;
    private String username;
    private String email;
    private String password;
    private String description;
    private String curriculum;
}
