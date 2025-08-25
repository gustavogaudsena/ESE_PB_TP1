package br.com.housemanager.stockflow.model.Item;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ItemCategory {
    FOOD("Alimentos"),
    CLEANING("Material de Limpeza"),
    HOUSEWARE("Utensílio Doméstico"),
    SERVICE("Serviços");
    private final String displayName;

    ItemCategory(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }
}
