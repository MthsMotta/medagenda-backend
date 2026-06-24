package br.com.motta.medagenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MedicoRequestDTO(@NotBlank @Size(max = 20) String crm,
                               @NotNull @Positive Long usuarioId){ }
