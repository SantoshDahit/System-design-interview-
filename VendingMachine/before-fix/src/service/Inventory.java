package service;

import entity.Item;

import java.util.HashMap;
import java.util.Map;

//inventory as itemService(where we are adding it (service logic))
public class Inventory {

    private final Map<String, Item> itemMap= new HashMap<>();
    private final Map<String, Integer> stockMap = new HashMap<>();

    public void addItem(String id, Item item, int quantity){
        itemMap.put(id, item);
        stockMap.put(id, quantity);
    }

    public Item getItem(String id){
        return itemMap.get(id);
    }

    public boolean isAvailable(String id) {
        return stockMap.getOrDefault(id, 0) > 0;
    }

    public void reduceStock(String id){
        stockMap.put(id, stockMap.get(id) - 1);
    }
}
