package state;

import constant.Money;
import entity.VendingMachine;

// Item chosen and enough money paid. Ready to dispense.
public class HasMoneyState extends VendingMachineState {

    public HasMoneyState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectItem(String id) {
        System.out.println("Item already selected. Press dispense or refund.");
    }

    @Override
    public void insertMoney(Money money) {
        // Design choice: we already have enough, so we don't take more money.
        System.out.println("Enough money received. Returning extra " + money.getValue() + ".");
    }

    @Override
    public void dispense() {
        vendingMachine.setState(new DispensingState(vendingMachine));
        vendingMachine.dispenseItem();               // gives item + change, then goes back to Idle
    }

    @Override
    public void refund() {
        vendingMachine.refundBalance();
        vendingMachine.setState(new IdleState(vendingMachine));
    }
}
