package state;

import constant.CASH;
import constant.Coin;
import entity.VendingMachine;

public abstract class VendingMachineState {
    VendingMachine vendingMachine;
    VendingMachineState(VendingMachine vendingMachine){
        this.vendingMachine = vendingMachine;
    }

    public  abstract void insertCoin(Coin coin);
    public abstract void insertCash(CASH cash);
    public abstract void selectItem(String id);
    public abstract void dispense();
    public abstract void refund();

}
