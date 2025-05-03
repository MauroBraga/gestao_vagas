package br.com.mrb.gestao_vagas.modules.cadidate.usecase;


import br.com.mrb.gestao_vagas.modules.cadidate.dto.JobDTO;
import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import br.com.mrb.gestao_vagas.modules.company.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsFilterUseCase {


    @Autowired
    private JobRepository jobRepository;

    public List<JobDTO> execute(String filter) {
        var jobs =  this.jobRepository.findByDescriptionContainingIgnoreCase(filter);

        return jobs.stream().map(job -> {
            return JobDTO.builder()
                    .id(job.getId())
                    .description(job.getDescription())
                    .benefits(job.getBenefits())
                    .level(job.getLevels())
                    .companyId(job.getCompanyId())
                    .createdAt(job.getCreatedAt())
                    .build();
        }).toList();
    }
}
