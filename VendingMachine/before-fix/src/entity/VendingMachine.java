package entity;

import constant.CASH;
import constant.Coin;
import service.Inventory;
import state.IdleState;
import state.VendingMachineState;

import java.util.PrimitiveIterator;

public class VendingMachine {
    private final static VendingMachine Instance = new VendingMachine();
    private final Inventory inventory = new Inventory();

    private VendingMachineState currentVendingMachineState;
    private int balance = 0;
    private String selectedItemId;

    public static VendingMachine getInstance() {
        return Instance;
    }
    public void insertCoin(Coin coin){
        currentVendingMachineState.insertCoin(coin);
    }

    public void insertCash(CASH cash){
        currentVendingMachineState.insertCash(cash);
    }

    public Item addItem(String id, String name, int price, int quantity){
        Item item = new Item(id, name, price);
        inventory.addItem(id, item, quantity);
        return item;
    }


    public void selectItem (String id){
        currentVendingMachineState.selectItem(id);
    }

    public void dispense(){
        currentVendingMachineState.dispense();
    }

    public void dispenseItem(){
        Item item = inventory.getItem(selectedItemId);
        if(balance >= item.getPrice()){
            inventory.reduceStock(selectedItemId);
            balance -= item.getPrice();
            System.out.println("Dispense: " + item.getName());
            if (balance > 0) {
                System.out.println("Returning change: " + balance);
            }

        }
        reset();
        setState(new IdleState(this));
    }

    public void refundBalance() {
        System.out.println("Refunding: " + balance);
        balance = 0;
    }

    public void reset () {
        selectedItemId = null;
        balance = 0;
    }

    public void addBalance(int value){
        balance += value;
    }

    public Item getSelectedItem(){
        return inventory.getItem(selectedItemId);
    }

    public void setSelectedItemId(String id) {
        this.selectedItemId = id;
    }

    public void setState(VendingMachineState vendingMachineState){
        this.currentVendingMachineState = vendingMachineState;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public int getBalance(){
        return balance;
    }
}
