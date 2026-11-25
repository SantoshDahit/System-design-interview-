package state;

import constant.CASH;
import constant.Coin;
import entity.VendingMachine;

public class DispensingState extends VendingMachineState{
    public DispensingState(VendingMachine  vendingMachine){
        super(vendingMachine);
    }

    @Override
    public void insertCoin(Coin coin) {
        System.out.println("Currently dispensing. Please wait..");
    }

    @Override
    public void insertCash(CASH cash) {
        System.out.println("Currently dispensing. Please wait..");
    }

    @Override
    public void selectItem(String id) {
        System.out.println("Currently dispensing. Please wait..");

    }

    @Override
    public void dispense() {

    }

    @Override
    public void refund() {
        System.out.println("Dispensing in progress. Refund not allowed.");
    }
}
