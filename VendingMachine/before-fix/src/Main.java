import constant.CASH;
import constant.Coin;
import constant.Money;
import entity.VendingMachine;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        VendingMachine vendingMachine = VendingMachine.getInstance();

        vendingMachine.addItem("A1", "Coke", 250, 3);
        vendingMachine.addItem("A2", "Pepsi", 250, 2);
        vendingMachine.addItem("B1", "Water", 100, 5);

        //select item
        System.out.println("\n --- Step 1: Select an item ---");
        vendingMachine.selectItem("A1");

        //insert coin or cash
        System.out.println("\n--- Step 2: Insert coins ---");
        vendingMachine.insertCoin(Coin.HUNDRED); // 100
        vendingMachine.insertCoin(Coin.FIVE_HUNDRED); // 500
        vendingMachine.insertCash(CASH.THOUSAND); //1000
        vendingMachine.insertCash(CASH.FIVE_THOUSAND); //5000

        //Dispense the product
        System.out.println("\n --- Step 3: Dispense item ---");
        vendingMachine.dispense();

        // Select another item
        System.out.println("\n--- Step 4: Select another item ---");
        vendingMachine.selectItem("B1");

        // Insert more amount
        System.out.println("\n--- Step 5: Insert more than needed ---");
        vendingMachine.insertCoin(Coin.FIVE_HUNDRED); // 250

        // Try to dispense the product
        System.out.println("\n--- Step 6: Dispense and return change ---");
        vendingMachine.dispense();

    }
}