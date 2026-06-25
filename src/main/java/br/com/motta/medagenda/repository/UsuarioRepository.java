package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
