package constant;

public sealed interface Money permits CASH, Coin {
    int getValue();
}
