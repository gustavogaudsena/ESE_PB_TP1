package br.com.housemanager.stockflow.repository;

import br.com.housemanager.stockflow.model.Item.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class ItemRepository {
    private final List<Item> items = new ArrayList<>();
    private Random random = new Random();

    public List<Item> getAll() {
        return items;
    }

    public Optional<Item> getById(Long id) {
        return items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    public Item create(Item item) {
        item.setId(Math.abs(random.nextLong()));
        items.add(item);
        return item;
    }

    public Item update(Item item) {
        return items
                .stream()
                .filter(i -> i.getId().equals(item.getId()))
                .findFirst()
                .map(existingItem -> {
                    existingItem.setName(item.getName());
                    return existingItem;
                })
                .orElse(null);

    }

    public Boolean delete(Long id) {
        return items.removeIf(i -> i.getId().equals(id));
    }
}
