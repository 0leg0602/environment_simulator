package project;

import java.awt.*;
import java.util.Collections;

import static project.Main.world;

public class Sheep extends Animal{

    public Sheep() {
        hunger = 20;
        target_food = 5;
        color = Color.white;
    }

    public void tick() {
        current_delay++;
        hunger--;

        if (hunger < 0 ){
            World.sheep_death += 1;
            world.despawn(this);
            return;
        }

        Entity beneath = world.get_grass(this.pos);
        if (beneath != null){
            world.despawn(beneath);
            World.grass_death += 1;
            hunger = 20;
            current_food++;
        }
        if (current_food >= target_food){
            current_food = 0;
            unforeseen_anomalous_impactful_sheep_mitosis();
        }

        if (current_delay >= target_delay){
            current_delay = 0;
            target_delay = Randomizer.rand_range(0, 4);
            move_random();

        }
    }

    private void move_random() {
        Collections.shuffle(world.directions);

        for (int[] dir : world.directions) {
            int guess_x = dir[0] + this.pos.x;
            int guess_y = dir[1] + this.pos.y;

            Object obj = world.get(new Position(guess_x, guess_y));

            Position move_pos = new Position(guess_x, guess_y);

            if (obj == null && world.is_not_out_of_bound(move_pos)) {
                world.move(this, move_pos);
                return;
            }
        }
    }

    private void unforeseen_anomalous_impactful_sheep_mitosis() {
        Collections.shuffle(world.directions);

        for (int[] dir : world.directions) {
            int guess_x = dir[0] + this.pos.x;
            int guess_y = dir[1] + this.pos.y;

            Object obj = world.get_grass(new Position(guess_x, guess_y));

            Position create_pos = new Position(guess_x, guess_y);

            if (obj == null && world.is_not_out_of_bound(create_pos)) {
                world.spawn_pos(Sheep::new, create_pos);
                return;
            }
        }
    }
}
