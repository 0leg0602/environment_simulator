package project;

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position pos)) return false;
        return getX() == pos.getX() && getY() == pos.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }


    @Override
    public String toString() {
        return "Position{" +
                "x=" + getX() +
                ", y=" + getY() +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
