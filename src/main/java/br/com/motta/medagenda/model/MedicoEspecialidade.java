package br.com.motta.medagenda.model;

import jakarta.persistence.*;

@Entity
@Table(name="tb_medico_especialidade")
public class MedicoEspecialidade {

    @EmbeddedId
    private MedicoEspecialidadeId id;

    @ManyToOne
    @MapsId("medicoId")
    @JoinColumn(name="id_medico")
    private Medico medico;

    @ManyToOne
    @MapsId("especialidadeId")
    @JoinColumn(name="id_especialidade")
    private Especialidade especialidade;

    public MedicoEspecialidadeId getId() {
        return id;
    }

    public void setId(MedicoEspecialidadeId id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }
}
