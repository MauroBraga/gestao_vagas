package br.com.mrb.gestao_vagas.modules.cadidate.usecase;

import br.com.mrb.gestao_vagas.exceptions.UserFoundException;
import br.com.mrb.gestao_vagas.modules.cadidate.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;


    public CandidateEntity execute(CandidateEntity candidateEntity){
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
                .ifPresent(candidate -> {
                    throw new UserFoundException();
                });
        var cadidate = this.candidateRepository.save(candidateEntity);
        return cadidate;
    }
}
