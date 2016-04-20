package logic;

/**
 * Created by Clara on 2016-04-14.
 */
public class Item {
    static private int ember = 0;
    static private int iceOrbit = 0;

    static public final int EMBER_MAX = 5;
    static public final int ICEORBIT_MAX = 3;

    public synchronized static int getIceOrbit() {
        return iceOrbit;
    }

    public synchronized static void setIceOrbit(int numIceOrbit) {
        if (numIceOrbit < 0) {
            throw new IllegalArgumentException("ember number cannot be negative");
        }
        if (numIceOrbit > ICEORBIT_MAX) {
            throw new IllegalArgumentException("ember number cannot be more than" + ICEORBIT_MAX);
        }
        iceOrbit = numIceOrbit;
    }

    public synchronized static void increaseIceOrbit(int numToIncrease) {
        iceOrbit += numToIncrease;
        if (iceOrbit > ICEORBIT_MAX) {
            iceOrbit = ICEORBIT_MAX;
        }
    }

    public synchronized static int getEmber() {
        return ember;
    }

    public synchronized static void setEmber(int numEmber) {
        if (numEmber < 0) {
            throw new IllegalArgumentException("ember number cannot be negative");
        }
        if (numEmber > EMBER_MAX) {
            throw new IllegalArgumentException("ember number cannot be more than" + EMBER_MAX);
        }
        ember = numEmber;
    }

    public synchronized static void increaseEmber(int numToIncrease) {
        ember += numToIncrease;
        if (ember > EMBER_MAX) {
            ember = EMBER_MAX;
        }
    }
}
