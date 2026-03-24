package project;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static final int GRID_HEIGHT = 100;
    public static final int GRID_WIDTH = 100;
    public static World world = new World();

    static void main() {
        Painter painter = new Painter();
        painter.create_window();
        init_entities();
    }

    private static void init_entities() {
        for (int i = 0; i < 300; i++) {
            Entity rand_sheep = new Sheep();
            rand_sheep.pos = new Position(Randomizer.rand_range(0, GRID_HEIGHT), Randomizer.rand_range(0, GRID_WIDTH));
            rand_sheep.DELAY = 4;
            world.spawn(rand_sheep);
        }

    }
}