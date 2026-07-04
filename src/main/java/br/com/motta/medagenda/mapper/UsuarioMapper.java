package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;
import br.com.motta.medagenda.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }

    public static void updateEntityFromDTO(UsuarioUpdateDTO dto, Usuario usuario){
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
    }
}
