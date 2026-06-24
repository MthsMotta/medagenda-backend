package br.com.motta.medagenda.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

import java.time.LocalDateTime;

@Entity
@Table(name="tb_consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_paciente", nullable=false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name="id_medico", nullable=false)
    private Medico medico;

    @Column(name="data_hora", nullable=false)
    private LocalDateTime dataHora;

    @Column(name="duracao_minutos", nullable=false)
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length=30)
    private StatusConsulta statusConsulta;

    @Column(name="valor", nullable=false)
    private BigDecimal valor;

    @Column(name="observacao", columnDefinition = "TEXT")
    private String observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
