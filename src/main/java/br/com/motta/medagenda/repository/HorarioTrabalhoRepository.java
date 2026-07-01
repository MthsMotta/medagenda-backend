package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.DiaSemana;
import br.com.motta.medagenda.model.HorarioTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface HorarioTrabalhoRepository extends JpaRepository<HorarioTrabalho, Long> {
    List<HorarioTrabalho> findByMedicoId(Long medicoId);
    List<HorarioTrabalho> findByDiaSemana(DiaSemana diaSemana);
    Optional<HorarioTrabalho> findByMedicoIdAndDiaSemanaAndHoraInicio(Long medicoId, DiaSemana diaSemana, LocalTime inicio);
}
