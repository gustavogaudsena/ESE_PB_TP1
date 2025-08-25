package br.com.housemanager.stockflow.model.Item;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Item {
    private Long id;
    private String name;
    private ItemCategory category;

    protected Item(String name, ItemCategory category) {
        this.name = name;
        this.category = category;
    }
}
