package br.com.motta.medagenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EspecialidadeRequestDTO(@NotBlank @Size(max = 100) String nome,
                                      @NotBlank @Size(max = 500) String descricao) {
}
