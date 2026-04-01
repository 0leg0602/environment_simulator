package project;

import java.util.Objects;

/**
 * A simple 2‑dimensional position used as a key in a {@code World#world_grid} and {@code World#grass_grid}.
 */
public class Position {
    private int x;
    private int y;

    /**
     * Creates a new position.
     *
     * @param x the horizontal coordinate
     * @param y the vertical coordinate
     */
    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position pos)) return false;
        return getX() == pos.getX() && getY() == pos.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Position{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

    /**
     * @return Returns the x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x the new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Returns the y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate.
     *
     * @param y the new y value
     */
    public void setY(int y) {
        this.y = y;
    }
}
