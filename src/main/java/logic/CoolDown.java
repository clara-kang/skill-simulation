package logic;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Clara on 2016-04-14.
 */
public class CoolDown extends Thread {

    private Skill skill;
    private double offset;

    private static HashMap<Skill, CoolDown> coolDownMap = new HashMap<Skill, CoolDown>();
    private static String CANNOT_PRESS_WHEN_NOT_AVAILABLE = "cannot press skill when not available";

    public CoolDown(Skill skill, double offset) {
        this.skill = skill;
        this.offset = offset;
    }

    public static void stopAll() {
        for (CoolDown coolDown : coolDownMap.values()) {
            coolDown.interrupt();
        }
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
            long sleepTime = (long) (skill.getCooldown() + offset);
            if (skill == Skill.BURN || skill == Skill.BUFF) {

                System.out.println("effectAvailable:"+skill);
                Availability.setAvailable(skill);
                DamageCalculation.getSession().getBasicRemote().sendText("effectAvailable:"+skill);

                this.sleep(sleepTime);

                Availability.setUnavailable(skill);
                System.out.println("effectUnavailable:"+skill);
                DamageCalculation.getSession().getBasicRemote().sendText("effectUnavailable:"+skill);
            } else {
                if(!Availability.isAvailable(skill)) {
                    throw new RuntimeException(CANNOT_PRESS_WHEN_NOT_AVAILABLE);
                }

                System.out.println("skillUnavailable:"+skill+":"+skill.getCooldown());
                Availability.setUnavailable(skill);
                DamageCalculation.getSession().getBasicRemote().sendText("skillUnavailable:"+skill+":"+skill.getCooldown());

                this.sleep(sleepTime);

                Availability.setAvailable(skill);
                System.out.println("skillAvailable:"+skill);
                DamageCalculation.getSession().getBasicRemote().sendText("skillAvailable:"+skill);
            }
        } catch (InterruptedException e) {
            return;
        } catch (IOException e) {
            e.printStackTrace();
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
