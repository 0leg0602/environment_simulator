package project;


public class Main {
    public static World world = new World();

    public static void main(String[] args){
        Painter painter = new Painter();
        init_entities();
        painter.create_window();
    }

    private static void init_entities() {
        for (int i = 0; i < 100 * World.common_mult; i++) {
            world.spawn_random(Sheep::new);
        }
        for (int i = 0; i < 10 * World.common_mult; i++) {
            world.spawn_random(Grass::new);
        }
        AlphaWolf woof = new AlphaWolf();
        woof.pos = new Position(World.GRID_WIDTH/2, World.GRID_HEIGHT/2);

    }
}