package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static project.Main.world;

public class Sheep extends Entity{
    @Override
    public void tick() {
        current_delay++;

        if (current_delay >= DELAY){
            current_delay = 0;
            DELAY = Randomizer.rand_range(0, 4);
            move_random();

        }
    }



    private void move_random() {


        Collections.shuffle(world.directions);

        for (int[] dir : world.directions) {
            int guess_x = dir[0] + this.pos.x;
            int guess_y = dir[1] + this.pos.y;

            Object obj = world.get(new Position(guess_x, guess_y));

            if (obj == null) {
                world.move(this, new Position(guess_x, guess_y));
                return;
            }
        }



    }
}
