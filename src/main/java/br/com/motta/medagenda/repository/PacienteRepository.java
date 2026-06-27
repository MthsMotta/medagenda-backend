package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Paciente> findByCpf(String cpf);
    Optional<Paciente> findByUsuarioId(Long usuarioId);
}
