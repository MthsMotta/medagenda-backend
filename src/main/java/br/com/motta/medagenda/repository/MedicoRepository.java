package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Optional<Medico> findByCrm(String crm);
    List<Medico> findByUsuarioNomeContainingIgnoreCase(String nome);
    Optional<Medico> findByUsuarioId(Long usuarioId);
}
