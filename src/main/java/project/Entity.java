package project;

import java.awt.Color;

public abstract class Entity {
    private Position pos;
    private Color color;
    private int target_delay;
    private int current_delay = 0;

    public abstract void tick();

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getTarget_delay() {
        return target_delay;
    }

    public void setTarget_delay(int target_delay) {
        this.target_delay = target_delay;
    }

    public int getCurrent_delay() {
        return current_delay;
    }

    public void setCurrent_delay(int current_delay) {
        this.current_delay = current_delay;
    }
}
