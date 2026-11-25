package state;

import constant.Money;
import entity.VendingMachine;

// Waiting for a customer. The only useful action is to select an item.
public class IdleState extends VendingMachineState {

    public IdleState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectItem(String id) {
        if (!vendingMachine.getInventory().exists(id)) {
            System.out.println("Unknown item: " + id);
            return;
        }
        if (!vendingMachine.getInventory().isAvailable(id)) {
            System.out.println("Sorry, " + id + " is out of stock.");
            return;
        }
        vendingMachine.setSelectedItemId(id);
        System.out.println("Selected " + vendingMachine.getSelectedItem().getName()
                + " (price " + vendingMachine.getSelectedItem().getPrice() + "). Please insert money.");
        vendingMachine.setState(new ItemSelectedState(vendingMachine));
    }

    @Override
    public void insertMoney(Money money) {
        System.out.println("Please select an item first.");
    }

    @Override
    public void dispense() {
        System.out.println("Please select an item first.");
    }

    @Override
    public void refund() {
        System.out.println("Nothing to refund.");
    }
}
