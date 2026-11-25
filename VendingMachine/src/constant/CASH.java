package constant;

public enum CASH implements Money {
    THOUSAND(1000),
    FIVE_THOUSAND(5000);

    private final int value;
    CASH(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }

}
