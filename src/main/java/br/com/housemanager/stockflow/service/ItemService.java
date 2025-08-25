package br.com.housemanager.stockflow.service;

import br.com.housemanager.stockflow.model.Item.Item;
import br.com.housemanager.stockflow.model.Item.ItemDTO;
import br.com.housemanager.stockflow.model.Item.ItemFactory;
import br.com.housemanager.stockflow.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<Item> getAll() {
        return itemRepository.getAll();
    }

    public Optional<Item> getById(@PathVariable Long id) {
        return itemRepository.getById(id);
    }

    public Item create(@RequestBody ItemDTO item) {
        Item createdItem = ItemFactory.create(item.getName(), item.getCategory());
        return itemRepository.create(createdItem);
    }

    public Item update(@PathVariable Long id, @RequestBody ItemDTO item) {
        Item createdItem = ItemFactory.create(item.getName(), item.getCategory());
        createdItem.setId(id);
        return itemRepository.update(createdItem);
    }

    public boolean delete(@PathVariable Long id) {
        return itemRepository.delete(id);

    }
}
