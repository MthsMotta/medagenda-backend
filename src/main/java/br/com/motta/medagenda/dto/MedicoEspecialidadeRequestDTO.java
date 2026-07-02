package br.com.motta.medagenda.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MedicoEspecialidadeRequestDTO(@NotNull @Positive Long medicoId,
                                            @NotNull @Positive Long especialidadeId) {
}
