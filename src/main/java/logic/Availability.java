package logic;

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

    static public void reset() {
        for (int i = 0; i < 10; i++) {
            availabilities[i] = true;
        }
        availabilities[10] = false;
        availabilities[11] = false;
    }

    static synchronized public void setAvailable(Skill skill) {
        availabilities[skill.getId()] = true;
    }

    static synchronized public void setUnavailable(Skill skill) {
        availabilities[skill.getId()] = false;
    }
}
