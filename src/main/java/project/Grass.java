package project;

import java.awt.*;
import java.util.Collections;

import static project.Main.world;

public class Grass extends Entity {

    public Grass() {
        color = new Color(Randomizer.rand_range(0, 40), Randomizer.rand_range(210, 255), Randomizer.rand_range(0, 40));
        change_delay();
    }

    @Override
    public void tick() {
        current_delay++;

        if (current_delay >= target_delay){
            current_delay = 0;
            change_delay();
            spread_and_conquer();
        }

    }

    private void change_delay(){
        if (world.grass.size() < 100){
            target_delay = Randomizer.rand_range(1, 2);
        } else if (world.grass.size() < 500){
            target_delay = Randomizer.rand_range(3, 7);
        } else {
            target_delay = Randomizer.rand_range(7, 13);
        }
    }
    
    private void spread_and_conquer() {
        Collections.shuffle(world.directions);

        for (int[] dir : world.directions) {
            int guess_x = dir[0] + this.pos.x;
            int guess_y = dir[1] + this.pos.y;

            Object obj = world.get_grass(new Position(guess_x, guess_y));

            Position create_pos = new Position(guess_x, guess_y);

            if (obj == null && world.is_not_out_of_bound(create_pos)) {
                world.spawn_pos(Grass::new, create_pos);
                if (Randomizer.rand_range(1, 3) != 1){
                    return;
                }
            }
        }
    }
}
