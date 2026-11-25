package state;

import constant.CASH;
import constant.Coin;
import entity.VendingMachine;

public class HasMoneyState extends VendingMachineState{

    public HasMoneyState(VendingMachine vendingMachine){
        super(vendingMachine);
    }
    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Already received full amount.");
    }

    @Override
    public void insertCash(CASH cash) {
        System.out.println("Already received full amount.");
    }

    @Override
    public void selectItem(String id) {
        System.out.println("Item already selected");
    }

    @Override
    public void dispense() {
        vendingMachine.setState(new DispensingState(vendingMachine));
        vendingMachine.dispenseItem();
    }

    @Override
    public void refund() {
        vendingMachine.refundBalance();
        vendingMachine.reset();
        vendingMachine.setState(new IdleState(vendingMachine));
    }
}
