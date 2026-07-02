package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.MedicoEspecialidade;
import br.com.motta.medagenda.model.MedicoEspecialidadeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoEspecialidadeRepository extends JpaRepository<MedicoEspecialidade, MedicoEspecialidadeId> {
    List<MedicoEspecialidade> findByMedicoId(Long medicoId);
    List<MedicoEspecialidade> findByEspecialidadeNomeContainingIgnoreCase(String especialidadeNome);
}
