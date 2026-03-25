package project;


public class Main {
    public static World world = new World();

    static void main() {
        Painter painter = new Painter();
        painter.create_window();
        init_entities();
    }

    private static void init_entities() {
        for (int i = 0; i < 100 * World.common_mult; i++) {
            world.spawn_random(Sheep::new);
        }
//        for (int i = 0; i < 10 * World.common_mult; i++) {
//            world.spawn_random(Grass::new);
//        }
        world.spawn_random(AlphaWolf::new);

    }
}