package project;

import java.awt.Color;

/**
 * Abstract class representing an entity.
 */
public abstract class Entity {
    private Position pos;
    private Color color;
    private int target_delay;
    private int current_delay = 0;

    /** Called each game tick to update the entity. */
    public abstract void tick();

    /** @return Returns the current position of the entity. */
    public Position getPos() {
        return pos;
    }

    /** Sets the position of the entity.
     * @param pos the new Position of the entity. */
    public void setPos(Position pos) {
        this.pos = pos;
    }

    /** @return Returns the color of the entity. */
    public Color getColor() {
        return color;
    }

    /** Sets the color of the entity.
     * @param color the new Color of the entity. */
    public void setColor(Color color) {
        this.color = color;
    }

    /** @return Returns the target delay for the entity. */
    public int getTarget_delay() {
        return target_delay;
    }

    /** Sets the target delay for the entity.
     * @param target_delay the new target delay. */
    public void setTarget_delay(int target_delay) {
        this.target_delay = target_delay;
    }

    /** @return Returns the current delay of the entity. */
    public int getCurrent_delay() {
        return current_delay;
    }

    /** Sets the current delay of the entity.
     * @param current_delay the new current delay. */
    public void setCurrent_delay(int current_delay) {
        this.current_delay = current_delay;
    }
}
