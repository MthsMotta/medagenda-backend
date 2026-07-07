package br.com.motta.medagenda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(@NotBlank @Email @Size(max = 100)  String email,
                       @NotBlank @Size(max = 100) String senha) {
}
