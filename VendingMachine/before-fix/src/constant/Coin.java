package constant;

public enum Coin implements Money{
    HUNDRED(100),
    FIVE_HUNDRED(500);

    private final int value;
    Coin(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

}
