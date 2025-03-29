package br.com.mrb.gestao_vagas.modules.cadidate.repository;

import br.com.mrb.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository  extends JpaRepository<ApplyJobEntity, UUID> {
}
