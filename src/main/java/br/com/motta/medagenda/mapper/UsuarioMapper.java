package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.UsuarioAdminRequestDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;
import br.com.motta.medagenda.model.Role;
import br.com.motta.medagenda.model.Usuario;

public class UsuarioMapper {

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }

    public static Usuario toEntity(CadastroDTO cadastro, String encryptedPassword) {
        Usuario usuario = new Usuario();
        usuario.setNome(cadastro.nome());
        usuario.setEmail(cadastro.email());
        usuario.setSenha(encryptedPassword);
        usuario.setRole(Role.PACIENTE);
        return usuario;
    }

    public static Usuario toEntity(UsuarioAdminRequestDTO dto, String encryptedPassword) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(encryptedPassword);
        usuario.setRole(dto.role());
        return usuario;
    }

    public static void updateEntityFromDTO(UsuarioUpdateDTO dto, Usuario usuario){
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
    }
}
