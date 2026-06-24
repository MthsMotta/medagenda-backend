package br.com.motta.medagenda.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_medico")
public class Medico{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="crm", length =20, nullable = false, unique = true)
    private String crm;

    @OneToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horarios;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private List<Consulta> consultas;

    @OneToMany(mappedBy = "medico", fetch = FetchType.LAZY)
    private List<MedicoEspecialidade> especialidades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<HorarioTrabalho> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioTrabalho> horarios) {
        this.horarios = horarios;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    public List<MedicoEspecialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<MedicoEspecialidade> especialidades) {
        this.especialidades = especialidades;
    }
}
