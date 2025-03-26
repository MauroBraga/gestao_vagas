package br.com.mrb.gestao_vagas.modules.cadidate.usecase;

import br.com.mrb.gestao_vagas.exceptions.UserFoundException;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.CandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.CandidateResquestDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.entities.CandidateEntity;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateResponseDTO execute(CandidateResquestDTO candidateResquestDTO){
        this.candidateRepository.findByUsernameOrEmail(candidateResquestDTO.getUsername(), candidateResquestDTO.getEmail())
                .ifPresent(candidate -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(candidateResquestDTO.getPassword());
        var candidate = new CandidateEntity();
        candidate.setUsername(candidateResquestDTO.getUsername());
        candidate.setEmail(candidateResquestDTO.getEmail());
        candidate.setPassword(password);
       this.candidateRepository.save(candidate);
        return CandidateResponseDTO.builder().description(candidate.getDescription())
                .id(candidate.getId())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .id(candidate.getId())
                .build();
    }
}
