package service;

import entity.Item;

import java.util.HashMap;
import java.util.Map;

// Inventory keeps the items and how many of each are left.
public class Inventory {

    private final Map<String, Item> itemMap = new HashMap<>();
    private final Map<String, Integer> stockMap = new HashMap<>();

    public void addItem(String id, Item item, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity can't be negative: " + quantity);
        }
        itemMap.put(id, item);
        stockMap.put(id, quantity);
    }

    public Item getItem(String id) {
        return itemMap.get(id);          // null if the id is unknown
    }

    public boolean exists(String id) {
        return id != null && itemMap.containsKey(id);
    }

    public boolean isAvailable(String id) {
        return stockMap.getOrDefault(id, 0) > 0;
    }

    public int getStock(String id) {
        return stockMap.getOrDefault(id, 0);
    }

    public void reduceStock(String id) {
        int stock = getStock(id);
        if (stock <= 0) {
            throw new IllegalStateException("Out of stock: " + id);
        }
        stockMap.put(id, stock - 1);
    }
}
