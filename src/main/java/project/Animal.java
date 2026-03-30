package project;

public abstract class Animal extends Entity {
    private int hunger;
    private int current_food = 0;
    private int target_food;

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getCurrent_food() {
        return current_food;
    }

    public void setCurrent_food(int current_food) {
        this.current_food = current_food;
    }

    public int getTarget_food() {
        return target_food;
    }

    public void setTarget_food(int target_food) {
        this.target_food = target_food;
    }
}
