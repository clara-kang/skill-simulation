package main;

import org.junit.*;

public class CoolDownTest {

    private long WAIT_TIME = 200;

    @Test public void testCoolDowns() {
        Assert.assertEquals(CoolDown.getCooldowns().length, Skill.values().length);
    }

    @Test public void testRun() throws Exception {
        Skill testSkill = Skill.BURN;
        CoolDown first = new CoolDown(testSkill, 0);
        CoolDown second = new CoolDown(testSkill, 0);
        Assert.assertFalse(Availability.isAvailable(testSkill));
        first.start();
        Thread.sleep(WAIT_TIME);
        Assert.assertTrue(Availability.isAvailable(testSkill));
        Thread.sleep(2000);
        second.start();
        Thread.sleep(WAIT_TIME);
        Assert.assertFalse(first.isAlive());
        Thread.sleep(CoolDown.getCoolDown(testSkill));
        Assert.assertFalse(Availability.isAvailable(Skill.BURN));
    }
}