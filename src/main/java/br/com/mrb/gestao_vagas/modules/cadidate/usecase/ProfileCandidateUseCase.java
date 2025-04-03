package br.com.mrb.gestao_vagas.modules.cadidate.usecase;


import br.com.mrb.gestao_vagas.exceptions.UserNotFoundException;
import br.com.mrb.gestao_vagas.modules.cadidate.dto.ProfileCandidateResponseDTO;
import br.com.mrb.gestao_vagas.modules.cadidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID id){

        var candidate = this.candidateRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());
        var candidateResponseDTO = ProfileCandidateResponseDTO.builder()
                .description(candidate.getDescription())
                .id(candidate.getId())
                .name(candidate.getName())
                .email(candidate.getEmail())
                .username(candidate.getUsername())
                .id(candidate.getId())
                .build();
        return candidateResponseDTO;
    }
}
