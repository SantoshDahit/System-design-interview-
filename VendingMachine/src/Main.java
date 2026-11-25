import constant.CASH;
import constant.Coin;
import entity.VendingMachine;

public class Main {
    public static void main(String[] args) {

        VendingMachine vendingMachine = VendingMachine.getInstance();

        vendingMachine.addItem("A1", "Coke", 250, 3);
        vendingMachine.addItem("A2", "Pepsi", 250, 2);
        vendingMachine.addItem("B1", "Water", 100, 1);

        System.out.println("\n--- 1. Buy Coke with 1000 (change 750) ---");
        vendingMachine.selectItem("A1");
        vendingMachine.insertCash(CASH.THOUSAND);
        vendingMachine.dispense();

        System.out.println("\n--- 2. Not enough money, then add more ---");
        vendingMachine.selectItem("A2");
        vendingMachine.insertCoin(Coin.HUNDRED);       // 100 of 250
        vendingMachine.dispense();                      // asks for 150 more
        vendingMachine.insertCoin(Coin.HUNDRED);       // 200
        vendingMachine.insertCoin(Coin.HUNDRED);       // 300 -> enough
        vendingMachine.dispense();                      // Pepsi + change 50

        System.out.println("\n--- 3. Cancel and get money back ---");
        vendingMachine.selectItem("B1");
        vendingMachine.insertCoin(Coin.FIVE_HUNDRED);
        vendingMachine.refund();                        // refund 500, back to Idle

        System.out.println("\n--- 4. Wrong order and bad inputs ---");
        vendingMachine.insertCoin(Coin.HUNDRED);       // select first
        vendingMachine.dispense();                      // select first
        vendingMachine.selectItem("Z9");               // unknown item

        System.out.println("\n--- 5. Out of stock ---");
        vendingMachine.selectItem("B1");
        vendingMachine.insertCoin(Coin.HUNDRED);
        vendingMachine.dispense();                      // last Water
        vendingMachine.selectItem("B1");               // now out of stock
    }
}
