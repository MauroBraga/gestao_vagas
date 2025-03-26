package br.com.mrb.gestao_vagas.modules.cadidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {

    private UUID id;
    @Schema(example = "Maria de Souza")
    private String name;
    @Schema(example = "maria@gmail.com")
    private String email;
    @Schema(example = "maria")
    private String username;
    @Schema(example = "Desenvolvedora Java")
    private String description;
}
