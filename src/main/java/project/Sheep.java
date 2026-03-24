package project;

import java.util.Collections;

import static project.Main.world;

public class Sheep extends Animal{
    public void tick() {
        current_delay++;
        hunger--;

        if (hunger < 0 ){
            world.despawn(this);
            return;
        }

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

            Position move_pos = new Position(guess_x, guess_y);

            if (obj == null && world.is_not_out_of_bound(move_pos)) {
                world.move(this, move_pos);
                return;
            }
        }
    }
}
