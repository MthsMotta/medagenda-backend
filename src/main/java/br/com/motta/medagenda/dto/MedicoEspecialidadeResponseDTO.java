package br.com.motta.medagenda.dto;

public record MedicoEspecialidadeResponseDTO(Long medicoId,
                                             Long especialidadeId,
                                             String nomeMedico,
                                             String nomeEspecialidade) {
}
