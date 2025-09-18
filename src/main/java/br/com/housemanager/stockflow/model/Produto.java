package br.com.housemanager.stockflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stock_item")
public class Produto {
    @Id@GeneratedValue()
    private Long id;
    private String nome;
    private CategoriaDeProduto categoria;

    protected Produto(String nome, CategoriaDeProduto categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }
}
