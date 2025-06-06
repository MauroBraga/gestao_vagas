package br.com.mrb.gestao_vagas.modules.company.usecase;

import br.com.mrb.gestao_vagas.exceptions.UserNotFoundException;
import br.com.mrb.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.mrb.gestao_vagas.modules.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository  companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent(user -> {
                    throw new UserNotFoundException();
                });
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return this.companyRepository.save(companyEntity);
    }
}

