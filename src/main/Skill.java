package main;

/**
 * Created by Clara on 2016-04-14.
 */
public enum Skill {
    X(0), C(1), V(2), FDD(3), FFF(4), FDB(5), ONE(6), TWO(7), L(8), R(9), BURN(10), BUFF(11);

    private final int id;

    Skill(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
