package state;

import constant.Money;
import entity.VendingMachine;

// An item is chosen, but not enough money has been inserted yet.
public class ItemSelectedState extends VendingMachineState {

    public ItemSelectedState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectItem(String id) {
        System.out.println("Item already selected. Refund first to choose another item.");
    }

    @Override
    public void insertMoney(Money money) {
        vendingMachine.addBalance(money.getValue());
        int price = vendingMachine.getSelectedItem().getPrice();
        int balance = vendingMachine.getBalance();
        System.out.println("Inserted " + money.getValue() + ". Balance: " + balance);

        if (balance >= price) {
            vendingMachine.setState(new HasMoneyState(vendingMachine));
        }
    }

    @Override
    public void dispense() {
        int missing = vendingMachine.getSelectedItem().getPrice() - vendingMachine.getBalance();
        System.out.println("Not enough money. Please insert " + missing + " more.");
    }

    @Override
    public void refund() {
        vendingMachine.refundBalance();
        vendingMachine.setState(new IdleState(vendingMachine));
    }
}
