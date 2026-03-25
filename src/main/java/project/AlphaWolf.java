package project;

import java.awt.*;

import static project.Main.world;

public class AlphaWolf extends Animal{
    boolean is_hunting = false;
    public Position prev_pos = null;

    public AlphaWolf() {
        world.alpha_wolf = this;
        hunger = 10;
//        target_food = 30;
        color = Color.black;
    }

    @Override
    public void tick() {
//        System.out.println("wolf is ticking");
        if (is_hunting) {
            hunt();
        }

        hunger -= 1;
        if (hunger < 0) {
            is_hunting = true;
        }

    }

    private void hunt() {
//        System.out.println("wolf is hunting");

        for (int search_circle = 1; search_circle < 50; search_circle++) {
            for (int i_x = -search_circle; i_x <= search_circle; i_x++) {
                int glob_x = i_x + this.pos.x;
//                if (glob_x < 0 || glob_x > World.GRID_WIDTH){
//                    continue;
//                }
                for (int i_y = -search_circle; i_y <= search_circle; i_y++) {
                    if (i_x == 0 && i_y == 0) continue;

                    int glob_y = i_y + this.pos.y;
//                    Object obj = world.get(new Position(glob_x, glob_y));
//                    System.out.println(obj);
                    Position glob_pos = new Position(glob_x, glob_y);
                    if (world.get(glob_pos) instanceof Sheep shep) {
//                        System.out.println("shep");
                        world.despawn(shep);
                        prev_pos = pos;
                        world.move(this, glob_pos);
                        hunger ++;
                        return;
                    }

//                    world.spawn_pos(Grass::new, new Position(glob_x, glob_y));
//                directions.add(new int[]{i_x, i_y});
                }
            }
        }


//        for (int search_circle = 1; search_circle < World.GRID_WIDTH; search_circle++) {
//            for (int x = 0; x < 0; x++) {
//
//            }
//        }
    }
}
