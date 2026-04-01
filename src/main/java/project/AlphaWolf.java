package project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import static project.Main.getWorld;

/**
 * Represents a wolf entity in the simulation.
 */
public class AlphaWolf extends Animal{
    private boolean hunting = false;
    private final List<Position> prev_pos = new ArrayList<>();

    /**
     * Creates an AlphaWolf and registers it in the world.
     */
    public AlphaWolf() {
        getWorld().setAlpha_wolf(this);
        setHunger(200 * World.getCommon_multiplier());
        setColor(Color.black);
    }

    /**
     * Main logic, simulation tick.
     */
    @Override
    public void tick() {
        if (isHunting()) {
            for (int i = 0; i < World.getCommon_multiplier(); i++) {
                hunt();
            }
        } else {
            setHunger(getHunger() - 3);
            if (getHunger() < 0) {
                setHunting(true);
            }
        }
        int sheep_target = (int) (80 * World.getCommon_multiplier() + Math.sqrt(getWorld().getAll_entities().size()*10));

        if (getHunger() > sheep_target) {
            getPrev_pos().clear();
            setHunting(false);
        }

        if (getHunger() < -20){
            getWorld().setAlpha_wolf(null);
        }

    }

    /**
     * Searches for sheep to eat within a radius and moves the wolf.
     */
    private void hunt() {
        for (int search_circle = 1; search_circle < 50; search_circle++) {
            for (int i_x = -search_circle; i_x <= search_circle; i_x++) {
                int glob_x = i_x + this.getPos().getX();
                for (int i_y = -search_circle; i_y <= search_circle; i_y++) {
                    if (i_x == 0 && i_y == 0) continue;

                    int glob_y = i_y + this.getPos().getY();
                    Position glob_pos = new Position(glob_x, glob_y);
                    if (getWorld().get(glob_pos) instanceof Sheep shep) {
                        getWorld().despawn(shep);
                        World.setSheep_eaten(World.getSheep_eaten() + 1);
                        getPrev_pos().add(getPos());
                        getWorld().move(this, glob_pos);
                        setHunger(getHunger() + 1);
                        return;
                    }
                }
            }
        }
        int x = Randomizer.rand_range(-50, 50) + getPos().getX();
        int y = Randomizer.rand_range(-50, 50) + getPos().getY();
        x = Math.clamp(x, 0, World.getGridWidth());
        y = Math.clamp(y, 0, World.getGridHeight());
        getPrev_pos().add(getPos());
        getWorld().move(this, new Position(x, y));
        setHunger(getHunger() - 1);

    }

    /**
     * @return Returns whether the wolf is currently hunting.
     */
    public boolean isHunting() {
        return hunting;
    }

    /**
     * @param hunting Sets the hunting state of the wolf.
     */
    public void setHunting(boolean hunting) {
        this.hunting = hunting;
    }

    /**
     * @return Returns the list of previous positions the wolf visited.
     */
    public List<Position> getPrev_pos() {
        return prev_pos;
    }
}
