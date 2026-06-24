package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
    Optional<Especialidade> findByNome(String nome);
    List<Especialidade> findByNomeContainingIgnoreCase(String nome);
}
