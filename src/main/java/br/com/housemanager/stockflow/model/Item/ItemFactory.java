package br.com.housemanager.stockflow.model.Item;

public class ItemFactory {

    public static Item create(String name, ItemCategory category) {

        Item newItem;
        if (category.equals(ItemCategory.FOOD)) newItem = new FoodItem(name);
        else if (category.equals(ItemCategory.SERVICE)) newItem = new ServiceItem(name);
        else if (category.equals(ItemCategory.HOUSEWARE)) newItem = new HousewareItem(name);
        else if (category.equals(ItemCategory.CLEANING)) newItem = new CleaningItem(name);
        else throw new IllegalArgumentException("Categoria inválida");

        return newItem;
    }
}
