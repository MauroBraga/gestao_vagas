package br.com.mrb.gestao_vagas.modules.cadidate.usecase;


import br.com.mrb.gestao_vagas.modules.company.entities.JobEntity;
import br.com.mrb.gestao_vagas.modules.company.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsFilterUseCase {


    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContaining(filter);
    }
}
