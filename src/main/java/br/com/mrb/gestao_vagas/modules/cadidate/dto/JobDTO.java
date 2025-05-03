package br.com.mrb.gestao_vagas.modules.cadidate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    private UUID id;
    private String description;
    private String benefits;
    private String level;
    private UUID companyId;
    private LocalDateTime createdAt;
}
