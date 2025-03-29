package br.com.mrb.gestao_vagas.modules.cadidate.entities;

import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "apply_jobs")
@Data
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "candidate_id",insertable = false, updatable = false)
    private CandidateEntity candidate;

    @ManyToOne
    @JoinColumn(name = "job_id",insertable = false, updatable = false)
    private JobEntity job;

    @Column(name = "candidate_id")
    private UUID candidateId;

    @Column(name = "job_id")
    private UUID jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
