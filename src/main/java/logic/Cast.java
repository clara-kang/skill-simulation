package logic;

import java.io.IOException;
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
        if(damage == 0) {
            return 0;
        }
        return (int) (damage * 1.4 + 100);
    }

    public static int getSkillDamage(Skill skill) throws InterruptedException, IOException{
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

    private static int pressX() throws IOException{
        burn(0);
        int add = generateBetween(223, 243) * Item.getEmber();
        Item.setEmber(0);
        CoolDown.startCoolDown(Skill.X, 0);
        int XDamage = generateBetween(6848, 7587) + add;
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.X + ":" + XDamage);
        return XDamage;
    }

    private static int pressC() throws IOException{
        burn(4000);
        CoolDown.startCoolDown(Skill.C, 0);
        int CDamage = generateBetween(5191, 5751);
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.C + ":" + CDamage);
        return CDamage;
    }

    private static int pressV() throws IOException{
        Item.increaseEmber(3);
        CoolDown.startCoolDown(Skill.V, 0);
        int VDamage = generateBetween(6223, 6895);
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.V + ":" + VDamage);
        return VDamage;
    }

    private static int pressFDD() throws IOException{
        Item.increaseEmber(5);
        Item.setIceOrbit(0);
        CoolDown.startCoolDown(Skill.FDD, 0);
        int FDDDamage = generateBetween(9350, 10358);
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.FDD + ":" + FDDDamage);
        return FDDDamage;
    }

    private static int pressFFF() throws IOException{
        Skill next;
        if(!Availability.isAvailable(Skill.BUFF)){
            next = Skill.FDB;
        } else if(Availability.isAvailable(Skill.FDD)){
            next = Skill.FDD;
        } else {
            return 0;
        }
        int add = 0;
        if (Availability.isAvailable(Skill.BURN)) {
            add = generateBetween(547, 603);
        }
        int FFFDamage = generateBetween(2353, 2605) + add;
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.FFF + ":" + FFFDamage);

        if(next == Skill.FDB) {
            FFFDamage += pressFDB();
        } else if(next == Skill.FDD) {
            FFFDamage += pressFDD();
        }
        return FFFDamage;
    }

    private static int pressFDB() throws IOException {
        if (!Availability.isAvailable(Skill.BUFF)) {
            Item.increaseEmber(5);
            CoolDown.startCoolDown(Skill.BUFF, 0);
            int add = 0;
            if (Availability.isAvailable(Skill.BURN)) {
                add = generateBetween(1063, 1175);
            }
            int FDBDamage = generateBetween(4675, 5179) + add;
            DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.FDB + ":" + FDBDamage);
            return FDBDamage;
        }
        return 0;
    }

    private static int pressOne() throws IOException {
        if (Item.getEmber() == Item.EMBER_MAX && !Availability.isAvailable(Skill.BURN)) {
            Item.setEmber(0);
            burn(0);
            DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.ONE + ":" + 5400);
            return 5400;
        }
        return 0;
    }

    private static int pressTwo() throws IOException{
        Item.increaseEmber(1);
        int add = 0;
        if (Availability.isAvailable(Skill.BURN)) {
            add = generateBetween(2095, 2319);
        }
        int TWODamage = generateBetween(3127, 3463) + add;
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.TWO + ":" + TWODamage);
        System.out.println("TTTTTTTTTTTTTWWWWWWWWWWOOOOOOOOOO!!!");
        return TWODamage;
    }

    private static int pressL() throws IOException{
        boolean critical = false;
        Item.increaseEmber(1);
        int LDamage = generateBetween(1713, 1892);
        if (tossDice(0.5688)) {
            critical = true;
            LDamage = (int)(LDamage * 1.9755);
        }
        if (Availability.isAvailable(Skill.BURN)) {
            LDamage += generateBetween(960, 1061);
        }
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.L + ":" + LDamage);
        if(critical) LDamage += pressFFF();
        return LDamage;
    }

    private static int pressR() throws IOException{
        if (tossDice(0.5688)) {
            Item.increaseIceOrbit(1);
        }
        int RDamage = generateBetween(805, 889);
        DamageCalculation.getSession().getBasicRemote().sendText("SkillDamage:" + Skill.R + ":" + RDamage);
        return RDamage;
    }

}
