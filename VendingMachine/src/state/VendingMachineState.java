package state;

import constant.Money;
import entity.VendingMachine;

// Each state decides what each action does, and which state comes next.
public abstract class VendingMachineState {

    protected final VendingMachine vendingMachine;

    protected VendingMachineState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public abstract void selectItem(String id);

    public abstract void insertMoney(Money money);

    public abstract void dispense();

    public abstract void refund();
}
