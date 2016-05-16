package logic;

import javax.websocket.Session;

public class DamageCalculation extends Thread{

    private static Skill[] orderedSkills = {Skill.C, Skill.X, Skill.ONE, Skill.V, Skill.TWO};
    private static Session session;
    private static DamageCalculation calculationThread;
    private int totalDamage = 0;
    private long startingTime;

    private DamageCalculation(Session session, long startingTime) {
        this.session = session;
        this.startingTime = startingTime;
    }

    public static Session getSession() {
        return session;
    }
    public static void calculateDamage(Session session, long startingTime) throws Exception{
        calculationThread = new DamageCalculation(session, startingTime);
        calculationThread.start();
    }

    public void run(){
        totalDamage = 0;
        int skillDamage;
        try {
            while (true) {
                for (Skill skill : orderedSkills) {
                    if (Availability.isAvailable(skill)) {
                        skillDamage = Cast.getSkillDamage(skill);
                        if(skillDamage == 0) {
                            continue;
                        }
                        totalDamage += skillDamage;
                        //System.out.println("SkillDamage:" + skill + ":" + skillDamage);
                        session.getBasicRemote().sendText("totalDamage:" + totalDamage);
                        sleep(400);

                        skillDamage = Cast.getSkillDamage(Skill.L);
                        totalDamage += skillDamage;
                        session.getBasicRemote().sendText("totalDamage:" + totalDamage);
                        sleep(400);

                        skillDamage = Cast.getSkillDamage(Skill.R);
                        totalDamage += skillDamage;
                        session.getBasicRemote().sendText("totalDamage:" + totalDamage);
                        sleep(400);
                        break;
                    }
                    sleep(400);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopCalculating() {
        if(calculationThread != null) {
            calculationThread.interrupt();
        }
    }

    public static void sendDamagePerSecond(long endTime) {
        if(calculationThread != null) {
            calculationThread.interrupt();
        }
        long damagePerSecond = calculationThread.totalDamage * 1000 / (endTime - calculationThread.startingTime);
        try {
            session.getBasicRemote().sendText("damagePerSecond:" + damagePerSecond);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
