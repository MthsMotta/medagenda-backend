package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.DiaSemana;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;

public record HorarioTrabalhoRequestDTO(@NotNull DiaSemana diaSemana,
                                        @NotNull LocalTime horaInicio,
                                        @NotNull LocalTime horaFim,
                                        @NotNull @Positive Long medicoId) {
}
