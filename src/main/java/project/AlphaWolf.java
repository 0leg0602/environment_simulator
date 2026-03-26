package project;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static project.Main.world;

public class AlphaWolf extends Animal{
    boolean is_hunting = false;
    public List<Position> prev_pos = new ArrayList<>();

    public AlphaWolf() {
        world.alpha_wolf = this;
        hunger = 200 * World.common_mult;
        color = Color.black;
    }

    @Override
    public void tick() {
        if (is_hunting) {
            for (int i = 0; i < World.common_mult; i++) {
                hunt();
            }
        } else {
            hunger -= 3;
            if (hunger < 0) {
                is_hunting = true;
            }
        }
        int sheep_target = (int) (80 * World.common_mult + Math.sqrt(world.all_entities.size()*10));
        System.out.println(sheep_target);

        if (hunger > sheep_target) {
            prev_pos.clear();
            is_hunting = false;
        }

        if (hunger < -20){
            world.alpha_wolf = null;
        }

    }

    private void hunt() {
//        System.out.println("wolf is hunting");

        for (int search_circle = 1; search_circle < 50; search_circle++) {
            for (int i_x = -search_circle; i_x <= search_circle; i_x++) {
                int glob_x = i_x + this.pos.x;
                for (int i_y = -search_circle; i_y <= search_circle; i_y++) {
                    if (i_x == 0 && i_y == 0) continue;

                    int glob_y = i_y + this.pos.y;
                    Position glob_pos = new Position(glob_x, glob_y);
                    if (world.get(glob_pos) instanceof Sheep shep) {
                        world.despawn(shep);
                        World.sheep_eaten += 1;
                        prev_pos.add(pos);
                        world.move(this, glob_pos);
                        hunger += 1;
                        return;
                    }
                }
            }
        }
        int x = Randomizer.rand_range(-50, 50) + pos.x;
        int y = Randomizer.rand_range(-50, 50) + pos.y;
        x = Math.clamp(x, 0, World.GRID_WIDTH);
        y = Math.clamp(y, 0, World.GRID_HEIGHT);
        prev_pos.add(pos);
        world.move(this, new Position(x, y));
        hunger -= 1;

    }
}
