package main;

/**
 * Created by Clara on 2016-04-14.
 */
public class Availability {

    static private Boolean[] availabilities = {true,true,true,true,true,true,true,true,true,true,false,false};

    static public Boolean[] getAvailabilities() {
        return availabilities;
    }

    static public Boolean isAvailable(Skill skill) {
        return availabilities[skill.getId()];
    }

    static synchronized public void setAvailable(Skill skill) {
        availabilities[skill.getId()] = true;
    }

    static synchronized public void setUnavailable(Skill skill) {
        availabilities[skill.getId()] = false;
    }
}
