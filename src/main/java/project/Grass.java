package project;

import java.awt.Color;
import java.util.Collections;

import static project.Main.getWorld;

/**
 * Represents a grass entity in the simulation.
 */
public class Grass extends Entity {

    /**
     * Creates a new Grass entity with a random color
     */
    public Grass() {
        setColor(new Color(Randomizer.rand_range(0, 40), Randomizer.rand_range(210, 255), Randomizer.rand_range(0, 40)));
        change_delay();
    }

    /**
     * Main logic, simulation tick
     */
    @Override
    public void tick() {
        setCurrent_delay(getCurrent_delay() + 1);

        if (getCurrent_delay() >= getTarget_delay()){
            setCurrent_delay(0);
            change_delay();
            spread_and_conquer();
        }

    }

    /**
     * Updates the target delay based on the current number of grass entities.
     * The delay is decreased as the grass count grows, allowing more frequent
     * spread when the population is small.
     */
    private void change_delay(){
        if (getWorld().getGrass().size() > 10000){
            setTarget_delay(Randomizer.rand_range(40, 60));
        } else if (getWorld().getGrass().size() > 2000){
            setTarget_delay(Randomizer.rand_range(14, 20));
        } else if (getWorld().getGrass().size() > 500) {
            setTarget_delay(Randomizer.rand_range(3, 7));
        } else {
            setTarget_delay(Randomizer.rand_range(1, 2));
        }
    }

    /**
     * Attempts to spread grass to adjacent cells.
     * The directions are shuffled to randomize spread order.
     */
    private void spread_and_conquer() {
        Collections.shuffle(getWorld().getDirections());

        for (int[] dir : getWorld().getDirections()) {
            int guess_x = dir[0] + this.getPos().getX();
            int guess_y = dir[1] + this.getPos().getY();

            Object obj = getWorld().get_grass(new Position(guess_x, guess_y));

            Position create_pos = new Position(guess_x, guess_y);

            if (obj == null && getWorld().is_not_out_of_bound(create_pos)) {
                getWorld().spawn_pos(Grass::new, create_pos);
                if (Randomizer.rand_range(1, 3) != 1){
                    return;
                }
            }
        }
    }
}
