package br.com.motta.medagenda.dto;

import br.com.motta.medagenda.model.Role;

public record UsuarioResponseDTO(Long id,
                                 String nome,
                                 String email,
                                 Role role) {
}
