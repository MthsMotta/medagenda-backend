package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Role;
import br.com.motta.medagenda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
