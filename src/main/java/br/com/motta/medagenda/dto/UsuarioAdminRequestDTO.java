package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioAdminRequestDTO(@NotBlank @Size(max = 100) String nome,
                                     @NotBlank @Email @Size(max = 100)  String email,
                                     @NotBlank @Size(min = 8, max = 100)  String senha,
                                     @NotNull Role role) {
}
