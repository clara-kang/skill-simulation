package main;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Clara on 2016-04-16.
 */
public class AvailabilityTest {

    @Test public void testAvailabilities() {
        Assert.assertEquals(Availability.getAvailabilities().length, Skill.values().length);
    }

    @Test
    public void testIsAvailable() throws Exception {
        Assert.assertFalse(Availability.isAvailable(Skill.BURN));
    }
}