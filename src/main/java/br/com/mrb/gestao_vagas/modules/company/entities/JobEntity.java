package br.com.mrb.gestao_vagas.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String description;
    private String  benefits;
    @NotBlank(message = "Esse campo [levels] é obrigatório")
    private String levels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id",nullable = false, updatable = false, insertable = false)
    private CompanyEntity companyEntity;

    @Column(name = "company_id",nullable = false)
    private UUID companyId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
