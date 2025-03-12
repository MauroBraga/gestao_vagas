package br.com.mrb.gestao_vagas.modules.cadidate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CandidateEntity {
    private String name;
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    private String username;
    @Email(message = "O campo [email] deve conter um e-mail válido")
    private String email;
    @Length(min = 10, max = 100, message = "O campo de senha deve conter entre 10 e 100 caracteres")
    private String password;
    private String description;
    private String curriculum;
}
