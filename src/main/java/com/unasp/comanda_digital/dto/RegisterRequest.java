package com.unasp.comanda_digital.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
}
