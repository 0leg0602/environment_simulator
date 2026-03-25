package project;

import java.awt.*;

public abstract class Entity {
    Position pos;
    Color color;
    int target_delay;
    int current_delay = 0;

    public abstract void tick();

}
