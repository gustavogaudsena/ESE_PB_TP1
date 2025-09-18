package br.com.housemanager.stockflow.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CategoriaDeProduto {
    ALIMENTOS("Alimentos"),
    LIMPEZA("Material de Limpeza"),
    HIGIENE_PESSOAL("Higiene pessoal"),
    UTENSILIOS_DOMESTICOS("Utensílio Doméstico"),
    SERVICOS("Serviços");
    private final String nomeExibicao;

    CategoriaDeProduto(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    @JsonValue
    public String getNomeExibicao() {
        return nomeExibicao;
    }
}