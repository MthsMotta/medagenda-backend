package br.com.motta.medagenda.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MedicoEspecialidadeId implements Serializable {

    private Long medicoId;
    private Long especialidadeId;

    public MedicoEspecialidadeId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicoEspecialidadeId that = (MedicoEspecialidadeId) o;
        return Objects.equals(medicoId, that.medicoId) && Objects.equals(especialidadeId, that.especialidadeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medicoId, especialidadeId);
    }

    public MedicoEspecialidadeId(Long medicoId, Long especialidadeId) {
        this.medicoId = medicoId;
        this.especialidadeId = especialidadeId;
    }
}
