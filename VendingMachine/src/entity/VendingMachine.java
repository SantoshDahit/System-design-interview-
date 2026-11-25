package entity;

import constant.CASH;
import constant.Coin;
import constant.Money;
import service.Inventory;
import state.IdleState;
import state.VendingMachineState;

public class VendingMachine {

    private static final VendingMachine INSTANCE = new VendingMachine();

    private final Inventory inventory = new Inventory();
    private VendingMachineState currentState;
    private int balance = 0;
    private String selectedItemId;

    // Fix #1: the machine always starts in Idle (before, the state was null and every call crashed).
    private VendingMachine() {
        this.currentState = new IdleState(this);
    }

    public static VendingMachine getInstance() {
        return INSTANCE;
    }

    // ---------- actions a customer can do (all go to the current state) ----------

    public void selectItem(String id) {
        currentState.selectItem(id);
    }

    public void insertMoney(Money money) {
        currentState.insertMoney(money);
    }

    // Kept so old code still works. Both coins and notes are Money.
    public void insertCoin(Coin coin) {
        insertMoney(coin);
    }

    public void insertCash(CASH cash) {
        insertMoney(cash);
    }

    public void dispense() {
        currentState.dispense();
    }

    public void refund() {
        currentState.refund();
    }

    // ---------- admin ----------

    public Item addItem(String id, String name, int price, int quantity) {
        Item item = new Item(id, name, price);
        inventory.addItem(id, item, quantity);
        return item;
    }

    // ---------- used by the states ----------

    // Called only from HasMoneyState, so balance >= price is already true here.
    public void dispenseItem() {
        Item item = getSelectedItem();
        inventory.reduceStock(selectedItemId);
        int change = balance - item.getPrice();

        System.out.println("Dispense: " + item.getName());
        if (change > 0) {
            System.out.println("Returning change: " + change);
        }
        reset();
        setState(new IdleState(this));
    }

    // Fix #4: money is always given back before the balance is cleared.
    public void refundBalance() {
        if (balance > 0) {
            System.out.println("Refunding: " + balance);
        }
        reset();
    }

    public void reset() {
        selectedItemId = null;
        balance = 0;
    }

    public void addBalance(int value) {
        balance += value;
    }

    public Item getSelectedItem() {
        return inventory.getItem(selectedItemId);
    }

    public void setSelectedItemId(String id) {
        this.selectedItemId = id;
    }

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public VendingMachineState getState() {
        return currentState;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getBalance() {
        return balance;
    }
}
