package br.com.motta.medagenda.specification;

import br.com.motta.medagenda.model.Consulta;
import br.com.motta.medagenda.model.StatusConsulta;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Objects;

public class ConsultaSpecification {

    public static Specification<Consulta> comPacienteId(Long pacienteId) {
     return (root, query, criteriaBuilder) -> {
         if (Objects.isNull(pacienteId)) {
             return null;
         }
         return criteriaBuilder.equal(root.get("paciente").get("id"), pacienteId);
     };
    }

    public static Specification<Consulta> comMedicoId(Long medicoId) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(medicoId)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("medico").get("id"), medicoId);
        };
    }

    public static Specification<Consulta> comStatus(StatusConsulta statusConsulta) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(statusConsulta)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("statusConsulta"), statusConsulta);
        };
    }

    public static Specification<Consulta> entreDataHora(LocalDateTime inicio, LocalDateTime fim) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(inicio) && Objects.isNull(fim)) {
                return null;
            }
            return criteriaBuilder.between(root.get("dataHora"), inicio, fim);
        };
    }
}
