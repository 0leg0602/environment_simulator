package project;

public abstract class Entity {
    Position pos;
    int DELAY;
    int current_delay = 0;
    public abstract void tick();

}
