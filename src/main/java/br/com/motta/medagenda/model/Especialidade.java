package br.com.motta.medagenda.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="tb_especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nome", length = 100, nullable = false, unique = true)
    private String nome;

    @Column(name="descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "especialidade", fetch = FetchType.LAZY)
    private List<MedicoEspecialidade> medicosEspecialistas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<MedicoEspecialidade> getMedicosEspecialistas() {
        return medicosEspecialistas;
    }

    public void setMedicosEspecialistas(List<MedicoEspecialidade> medicosEspecialistas) {
        this.medicosEspecialistas = medicosEspecialistas;
    }
}
