package br.com.motta.medagenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MedicoUpdateDTO(@NotBlank @Size(max=20) String crm) {
}
