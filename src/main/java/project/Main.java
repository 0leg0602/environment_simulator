package project;


public class Main {
    public static World world = new World();

    static void main() {
        Painter painter = new Painter();
        painter.create_window();
        init_entities();
    }

    private static void init_entities() {
        for (int i = 0; i < 3000; i++) {
            Animal rand_sheep = new Sheep();
            rand_sheep.pos = new Position(Randomizer.rand_range(0, World.GRID_HEIGHT), Randomizer.rand_range(0, World.GRID_WIDTH));
            rand_sheep.DELAY = 4;
            rand_sheep.hunger = 25;
//            rand_sheep.hunger = Randomizer.rand_range(20, 30);
            world.spawn(rand_sheep);
        }

    }
}