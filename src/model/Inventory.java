package model;

import api.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class Inventory {
    private final List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return List.copyOf(items);
    }

    public void clear() {
        items.clear();
    }

    public boolean hasItems() {
        return !items.isEmpty();
    }

    public Optional<Item> firstUsableItem() {
        // TODO: return the first usable item
        return items.stream().findFirst();
    }

    public void consume(Item item) {
        items.remove(item)
        // TODO: remove the used item
    }
}
