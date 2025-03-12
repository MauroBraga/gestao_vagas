package br.com.mrb.gestao_vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErroMessageDto {
    private String field;
    private String message;
}
