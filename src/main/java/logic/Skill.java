package logic;

/**
 * Created by Clara on 2016-04-14.
 */
public enum Skill {
    X(0, 45000), C(1, 30000), V(2, 45000), 
    FDD(3, 18000), FFF(4, 0), FDB(5, 0), 
    ONE(6, 0), TWO(7, 0), L(8, 0), 
    R(9, 0), BURN(10, 6000), BUFF(11, 15000);

    private final int id;
    private final int cooldown;

    Skill(int id, int cooldown) {
        this.id = id;
        this.cooldown = cooldown;
    }

    public int getId() {
        return this.id;
    }
    
    public int getCooldown() {
      return this.cooldown;
    }
}
