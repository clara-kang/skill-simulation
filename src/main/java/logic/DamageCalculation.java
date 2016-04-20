package logic;

import javax.websocket.Session;

public class DamageCalculation {

    private static Skill[] orderedSkills = {Skill.C, Skill.X, Skill.ONE, Skill.V, Skill.FFF, Skill.FDB, Skill.FDD, Skill.TWO};

    public static void calculateDamage(Session session) throws Exception{

        int sum = 0;

        while (true) {
             for (Skill skill : orderedSkills) {
                 if (Availability.isAvailable(skill)) {
                    if (skill == Skill.ONE && Availability.isAvailable(Skill.BURN)) {
                        continue;
                    }
                    sum += Cast.getSkillDamage(skill);
                     Thread.sleep(400);
                    sum += Cast.getSkillDamage(Skill.L);
                     Thread.sleep(400);
                    sum += Cast.getSkillDamage(Skill.R);
                     Thread.sleep(400);
                    break;
                }
                 Thread.sleep(400);
            }
            System.out.println("cumulated damage" + sum);
            session.getBasicRemote().sendText("cumulated damage" + sum);
        }
    }
}