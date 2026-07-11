package br.com.motta.medagenda.repository;

import br.com.motta.medagenda.model.Consulta;
import br.com.motta.medagenda.model.Medico;
import br.com.motta.medagenda.model.Paciente;
import br.com.motta.medagenda.model.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository extends JpaRepository<Consulta, Long>, JpaSpecificationExecutor<Consulta> {
    Optional<Consulta> findByMedicoIdAndPacienteIdAndDataHora(Long medicoId, Long pacienteId, LocalDateTime dataHora);
    List<Consulta> findByMedicoIdAndDataHoraBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fim);
    Boolean existsByMedicoAndStatusConsultaIn(Medico medico, List<StatusConsulta> statusConsulta);
    Boolean existsByPacienteAndStatusConsultaIn(Paciente paciente, List<StatusConsulta> statusConsulta);
    Boolean existsByMedicoIdAndPacienteId(Long medicoId, Long pacienteId);

}
