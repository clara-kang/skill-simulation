package main;

import java.util.Random;

/**
 * Created by Clara on 2016-04-15.
 */
public class Cast {

    private static int generateBetween(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    private static Boolean tossDice(double probability) {
        return Math.random() < probability;
    }

    private static void burn(int additionalTime) {
        CoolDown.startCoolDown(Skill.BURN, additionalTime);
    }

    private static int buffed(int damage) {
        return (int) (damage * 1.4 + 100);
    }

    public static int getSkillDamage(Skill skill) throws InterruptedException{
        System.out.println("press" + skill);
        int damage;
        switch (skill) {
            case X:
                damage = pressX();
                break;
            case C:
                damage = pressC();
                break;
            case V:
                damage = pressV();
                break;
            case FDD:
                damage = pressFDD();
                break;
            case FFF:
                damage = pressFFF();
                break;
            case FDB:
                damage = pressFDB();
                break;
            case ONE:
                damage = pressOne();
                break;
            case TWO:
                damage = pressTwo();
                break;
            case L:
                damage = pressL();
                break;
            case R:
                damage = pressR();
                break;
            default:
                throw new IllegalArgumentException("no such skill");
        }
        if (Availability.isAvailable(Skill.BUFF)) {
            return buffed(damage);
        }
        //Thread.sleep(400);
        return damage;
    }

    private static int pressX() {
        burn(0);
        int add = generateBetween(223, 243) * Item.getEmber();
        Item.setEmber(0);
        CoolDown.startCoolDown(Skill.X, 0);
        return generateBetween(6848, 7587) + add;
    }

    private static int pressC() {
        burn(4000);
        CoolDown.startCoolDown(Skill.C, 0);
        return generateBetween(5191, 5751);
    }

    private static int pressV() {
        Item.increaseEmber(3);
        CoolDown.startCoolDown(Skill.V, 0);
        return generateBetween(6223, 6895);
    }

    private static int pressFDD() {
        Item.increaseEmber(5);
        CoolDown.startCoolDown(Skill.FDD, 0);
        return generateBetween(9350, 10358);
    }

    private static int pressFFF() {
        //CoolDown.startCoolDown(Skill.FFF, 0);
        int add = 0;
        if (Availability.isAvailable(Skill.BURN)) {
            add = generateBetween(547, 603);
        }
        return generateBetween(2353, 2605) + add;
    }

    private static int pressFDB() {
        //CoolDown.startCoolDown(Skill.FDB, 0);
        Item.increaseEmber(5);
        CoolDown.startCoolDown(Skill.BUFF, 0);
        int add = 0;
        if (Availability.isAvailable(Skill.BURN)) {
            add = generateBetween(1063, 1175);
        }
        return generateBetween(4675, 5179) + add;
    }

    private static int pressOne() {
        if (Item.getEmber() == Item.EMBER_MAX) {
            burn(0);
        }
        return 5400;
    }

    private static int pressTwo() {
        Item.increaseEmber(1);
        int add = 0;
        if (Availability.isAvailable(Skill.BURN)) {
            add = generateBetween(2095, 2319);
        }
        return generateBetween(3127, 3463) + add;
    }

    private static int pressL() {
        Item.increaseEmber(1);
        int accDamage = 0;
        if (tossDice(0.5688)) {
            if (Availability.isAvailable(Skill.FDD) && Item.getIceOrbit() == 3) {
                accDamage = accDamage + pressFFF() + pressFDD();
            } else if (!Availability.isAvailable(Skill.BUFF)) {
                accDamage = accDamage + pressFFF() + pressFDB();
            }
            accDamage += (int)(generateBetween(1713, 1892) * 1.9755);
        }
        if (Availability.isAvailable(Skill.BURN)) {
            accDamage += generateBetween(960, 1061);
        }
        return accDamage;
    }

    private static int pressR() {
        if (tossDice(0.5688)) {
            Item.increaseIceOrbit(1);
        }
        return generateBetween(805, 889);
    }

    public static void main(String[] args) {
        System.out.println(generateBetween(0, 3));
    }
}
