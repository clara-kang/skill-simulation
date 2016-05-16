package logic;

import java.io.IOException;

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

    public synchronized static void setIceOrbit(int numIceOrbit) throws IOException{
        if (numIceOrbit < 0) {
            throw new IllegalArgumentException("ember number cannot be negative");
        }
        if (numIceOrbit > ICEORBIT_MAX) {
            throw new IllegalArgumentException("ember number cannot be more than" + ICEORBIT_MAX);
        }
        iceOrbit = numIceOrbit;
        DamageCalculation.getSession().getBasicRemote().sendText("IceOrbit:" + getIceOrbit());
    }

    public synchronized static void increaseIceOrbit(int numToIncrease) throws IOException{
        iceOrbit += numToIncrease;
        if (iceOrbit > ICEORBIT_MAX) {
            iceOrbit = ICEORBIT_MAX;
        }
        DamageCalculation.getSession().getBasicRemote().sendText("IceOrbit:" + getIceOrbit());
    }

    public synchronized static int getEmber() {
        return ember;
    }

    public synchronized static void setEmber(int numEmber) throws IOException{
        if (numEmber < 0) {
            throw new IllegalArgumentException("ember number cannot be negative");
        }
        if (numEmber > EMBER_MAX) {
            throw new IllegalArgumentException("ember number cannot be more than" + EMBER_MAX);
        }
        ember = numEmber;
        DamageCalculation.getSession().getBasicRemote().sendText("Ember:" + getEmber());
    }

    public synchronized static void increaseEmber(int numToIncrease) throws IOException{
        ember += numToIncrease;
        if (ember > EMBER_MAX) {
            ember = EMBER_MAX;
        }
        DamageCalculation.getSession().getBasicRemote().sendText("Ember:" + getEmber());
    }
}
