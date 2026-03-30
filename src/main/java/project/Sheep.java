package project;

import java.awt.Color;
import java.util.Collections;

import static project.Main.getWorld;

public class Sheep extends Animal{

    public Sheep() {
        setHunger(20);
        setTarget_food(5);
        setColor(Color.white);
    }

    public void tick() {
        setCurrent_delay(getCurrent_delay() + 1);
        setHunger(getHunger() - 1);

        if (getHunger() < 0 ){
            World.setSheep_death(World.getSheep_death() + 1);
            getWorld().despawn(this);
            return;
        }

        Entity beneath = getWorld().get_grass(this.getPos());
        if (beneath != null){
            getWorld().despawn(beneath);
            World.setGrass_death(World.getGrass_death() + 1);
            setHunger(20);
            setCurrent_food(getCurrent_food() + 1);
        }
        if (getCurrent_food() >= getTarget_food()){
            setCurrent_food(0);
            unforeseen_anomalous_impactful_sheep_mitosis();
        }

        if (getCurrent_delay() >= getTarget_delay()){
            setCurrent_delay(0);
            setTarget_delay(Randomizer.rand_range(0, 4));
            move_random();

        }
    }

    private void move_random() {
        Collections.shuffle( getWorld().getDirections());

        for (int[] dir :  getWorld().getDirections()) {
            int guess_x = dir[0] + this.getPos().getX();
            int guess_y = dir[1] + this.getPos().getY();

            Object obj =  getWorld().get(new Position(guess_x, guess_y));

            Position move_pos = new Position(guess_x, guess_y);

            if (obj == null &&  getWorld().is_not_out_of_bound(move_pos)) {
                 getWorld().move(this, move_pos);
                return;
            }
        }
    }

    private void unforeseen_anomalous_impactful_sheep_mitosis() {
        Collections.shuffle( getWorld().getDirections());

        for (int[] dir :  getWorld().getDirections()) {
            int guess_x = dir[0] + this.getPos().getX();
            int guess_y = dir[1] + this.getPos().getY();

            Object obj =  getWorld().get_grass(new Position(guess_x, guess_y));

            Position create_pos = new Position(guess_x, guess_y);

            if (obj == null &&  getWorld().is_not_out_of_bound(create_pos)) {
                 getWorld().spawn_pos(Sheep::new, create_pos);
                return;
            }
        }
    }
}
