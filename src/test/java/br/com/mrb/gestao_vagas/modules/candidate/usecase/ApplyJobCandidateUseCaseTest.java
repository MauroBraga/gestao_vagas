package br.com.mrb.gestao_vagas.modules.candidate.usecase;

import br.com.mrb.gestao_vagas.exceptions.JobNotFoundException;
import br.com.mrb.gestao_vagas.exceptions.UserNotFoundException;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.ApplyJobEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.ApplyJobRepository;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.CandidateRepository;
import br.com.mrb.gestao_vagas.modules.cadidate.usecase.ApplyJobCandidateUseCase;
import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import br.com.mrb.gestao_vagas.modules.company.repository.JobRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @InjectMocks
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Shoud not be albe to aply job with candidate not found")
    public void tshould_not_be_able_to_apply_job_with_candidate_not_found() {
        try{
            applyJobCandidateUseCase.execute(null, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    public void should_not_be_able_to_apply_job_with_job_found() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));
        try{
            applyJobCandidateUseCase.execute(idCandidate, null);
        } catch (Exception e){
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    public void should_be_able_to_create_a_new_apply_job() {
        var idCandidate = UUID.randomUUID();
        var idJob = UUID.randomUUID();

        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

        var job = new JobEntity();
        job.setId(idJob);
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(job));

        var applyId = UUID.randomUUID();
        var applyJob = ApplyJobEntity.builder().candidateId(idCandidate).jobId(idJob).build();
        var applyJobSave = ApplyJobEntity.builder().id(applyId).candidateId(idCandidate).jobId(idJob).build();
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobSave);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);
        assertThat(result).isNotNull();
        assertNotNull(result.getId());
    }

}
