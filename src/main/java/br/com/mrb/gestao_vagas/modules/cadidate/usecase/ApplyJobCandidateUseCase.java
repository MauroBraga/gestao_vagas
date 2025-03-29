package br.com.mrb.gestao_vagas.modules.cadidate.usecase;

import br.com.mrb.gestao_vagas.exceptions.JobNotFoundException;
import br.com.mrb.gestao_vagas.exceptions.UserNotFoundException;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.ApplyJobRepository;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.CandidateRepository;
import br.com.mrb.gestao_vagas.modules.company.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID idCandidate, UUID idJob) {

        this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
           throw new UserNotFoundException();
        });

        this.jobRepository.findById(idJob).orElseThrow(() ->{
            throw new JobNotFoundException();
        });
        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();
        var result = this.applyJobRepository.save(applyJob);

        return result;
    }
}
