package br.com.housemanager.stockflow.model.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {
    private String name;
    private ItemCategory category;
}
