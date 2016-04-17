package main;

import java.util.Random;

/**
 * Created by Clara on 2016-04-15.
 */
public class Cast {

    private static int generateBetween(int min, int max) {
        Random r = new Random();
        return  r.nextInt(max-min) + min;
    }

    private static void burn(int additionalTime) {
        CoolDown.startCoolDown(Skill.BURN, additionalTime);
    }

    private static int buffed(int damage) {
        return (int) (damage * 1.4 + 100);
    }

    public static int getSkillDamage(Skill skill) {
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
            default:
                throw new IllegalArgumentException("no such skill");
        }
        if(Availability.isAvailable(Skill.BUFF)) {
            return buffed(damage);
        }
        return damage;
    }

    public static int pressX() {
        burn(0);
        int add = generateBetween(223, 243) * Item.getEmber();
        Item.setEmber(0);
        CoolDown.startCoolDown(Skill.X, 0);
        return generateBetween(6848, 7587) + add;
    }

    public static int pressC() {
        burn(4000);
        CoolDown.startCoolDown(Skill.C, 0);
        return generateBetween(5191, 5751);
    }

    public static int pressV() {
        Item.increaseEmber(3);
        CoolDown.startCoolDown(Skill.V, 0);
        return generateBetween(6223, 6895);
    }

    public static int pressFDD() {
        CoolDown.startCoolDown(Skill.FDD, 0);
        return generateBetween(9350, 10358);
    }

    public static int pressFFF() {
        CoolDown.startCoolDown(Skill.FFF, 0);
        return generateBetween(2353, 2605);
        
    }

    public static void main(String[] args) {
        System.out.println(generateBetween(0,3));
    }
}
