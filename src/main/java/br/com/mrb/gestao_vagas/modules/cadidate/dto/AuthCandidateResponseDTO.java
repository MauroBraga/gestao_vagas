package br.com.mrb.gestao_vagas.modules.cadidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCandidateResponseDTO {
    private String accessToken;
    private Long expires_in;
    private List<String> roles;
}
