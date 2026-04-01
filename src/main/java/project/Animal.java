package project;

/**
 * Abstract class representing an animal.
 */
public abstract class Animal extends Entity {
    private int hunger;
    private int current_food = 0;
    private int target_food;

    /**
     * @return Returns the animal’s hunger level.
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * Sets the animal’s hunger level.
     *
     * @param hunger the new hunger value
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }


    /**
     * @return Returns the amount of animal's food.
     */
    public int getCurrent_food() {
        return current_food;
    }


    /**
     * Sets the amount of animal's food.
     *
     * @param current_food the new current food value
     */
    public void setCurrent_food(int current_food) {
        this.current_food = current_food;
    }

    /**
     * @return Returns the target food amount the animal aims for.
     */
    public int getTarget_food() {
        return target_food;
    }


    /**
     * Sets the target food amount the animal aims for.
     *
     * @param target_food the new target food value
     */
    public void setTarget_food(int target_food) {
        this.target_food = target_food;
    }
}
