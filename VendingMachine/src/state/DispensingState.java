package state;

import constant.Money;
import entity.VendingMachine;

// The machine is giving out the item. All actions must wait.
public class DispensingState extends VendingMachineState {

    public DispensingState(VendingMachine vendingMachine) {
        super(vendingMachine);
    }

    @Override
    public void selectItem(String id) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void insertMoney(Money money) {
        System.out.println("Currently dispensing. Please wait.");
    }

    @Override
    public void dispense() {
        System.out.println("Already dispensing.");
    }

    @Override
    public void refund() {
        System.out.println("Dispensing in progress. Refund not allowed.");
    }
}
