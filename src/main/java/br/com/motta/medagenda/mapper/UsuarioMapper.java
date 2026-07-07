package br.com.motta.medagenda.mapper;

import br.com.motta.medagenda.dto.CadastroDTO;
import br.com.motta.medagenda.dto.UsuarioResponseDTO;
import br.com.motta.medagenda.dto.UsuarioUpdateDTO;
import br.com.motta.medagenda.model.Role;
import br.com.motta.medagenda.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioMapper {

    public static UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }

    public static Usuario toEntity(CadastroDTO cadastro) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(cadastro.senha());
        Usuario usuario = new Usuario();
        usuario.setNome(cadastro.nome());
        usuario.setEmail(cadastro.email());
        usuario.setSenha(encryptedPassword);
        usuario.setRole(Role.PACIENTE);
        return usuario;
    }

    public static void updateEntityFromDTO(UsuarioUpdateDTO dto, Usuario usuario){
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
    }
}
