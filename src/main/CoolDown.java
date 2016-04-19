package main;

import java.util.HashMap;

/**
 * Created by Clara on 2016-04-14.
 */
public class CoolDown extends Thread {

    private static int[] cooldowns = {45000, 30000, 45000, 18000, 0, 0, 0, 0, 0, 0, 6000, 15000};
    private Skill skill;
    private double offset;
    private static HashMap<Skill, CoolDown> coolDownMap = new HashMap<>();

    public static int[] getCooldowns() {
        return cooldowns;
    }

    public static int getCoolDown(Skill skill) {
        return cooldowns[skill.getId()];
    }

    public CoolDown(Skill skill, double offset) {
        this.skill = skill;
        this.offset = offset;
    }

    public void run() {
        if (skill == null) {
            throw new IllegalArgumentException("skill cannot be null");
        }
        if (coolDownMap.containsKey(skill)) {
            CoolDown oldCoolDown = coolDownMap.get(skill);
            oldCoolDown.interrupt();
            coolDownMap.remove(skill);
        }
        coolDownMap.put(skill, this);
        try {
            long sleepTime = (long) (cooldowns[skill.getId()] + offset);
            if (skill == Skill.BURN || skill == Skill.BUFF) {
                System.out.println(skill + " available");
                Availability.setAvailable(skill);
                this.sleep(sleepTime);
                Availability.setUnavailable(skill);
                System.out.println(skill + " unavailable");
            } else {
                System.out.println(skill + " unavailable");
                Availability.setUnavailable(skill);
                this.sleep(sleepTime);
                Availability.setAvailable(skill);
                System.out.println(skill + " available");
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public static void startCoolDown(Skill skill, int addtionalTime) {
        new CoolDown(skill, addtionalTime).start();
    }

    public static void main(String[] args) throws Exception{
        startCoolDown(Skill.BURN, 0);
        Thread.sleep(2000);
        startCoolDown(Skill.BURN, 0);
    }
}
