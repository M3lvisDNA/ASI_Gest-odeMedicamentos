package co.mz.isutc.asi.projecto.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "farmacia") //serve para criar uma tabela no mysql com nome farmacia
public class Medicamentos{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome") //serve para criar uma coluna na nossa tabela no mysql com o nome "nome"
    private String nome;

    @Column(name = "preco") //serve para criar uma coluna na nossa tabela no mysql com o nome preco
    private double preco;

    @Column(name = "quantidade") //serve para criar uma coluna na nossa tabela no mysql com o nome quantidade
    private int quantidade;

    @Column(name = "fabricante") //serve para criar uma coluna na nossa tabela no mysql com o nome fabricante
    private String fabricante;

    @Column(name = "validade") //serve para criar uma coluna na nossa tabela no mysql com o nome validade
    private LocalDate validade;

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
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
    public LocalDate getValidade() {
        return validade;
    }
    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    
}
