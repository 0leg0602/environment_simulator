package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class World {
    private final Map<Position, Entity> world_grid = new HashMap<>();
    private final Map<Position, Entity> grass_grid = new HashMap<>();
    private final List<Entity> all_entities = new ArrayList<>();
    private final List<Entity> grass = new ArrayList<>();
    private final List<Position> empty_spaces = new ArrayList<>();
    private final List<int[]> directions = new ArrayList<>();
    private AlphaWolf alpha_wolf;

    private static int sheep_death = 0;
    private static int sheep_eaten = 0;
    private static int grass_death = 0;

    private static final int common_multiplier = 2;

    private final static int GRID_HEIGHT = 100 * getCommon_multiplier();
    private final static int GRID_WIDTH = 100 * getCommon_multiplier();



    public World() {
        for (int i_x = -1; i_x <= 1; i_x++) {
            for (int i_y = -1; i_y <= 1; i_y++) {
                if (i_x == 0 && i_y == 0) continue;
                getDirections().add(new int[]{i_x, i_y});
            }
        }
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                getEmpty_spaces().add(new Position(x, y));
            }
        }
    }

    public static int getSheep_death() {
        return sheep_death;
    }

    public static void setSheep_death(int sheep_death) {
        World.sheep_death = sheep_death;
    }

    public static int getSheep_eaten() {
        return sheep_eaten;
    }

    public static void setSheep_eaten(int sheep_eaten) {
        World.sheep_eaten = sheep_eaten;
    }

    public static int getGrass_death() {
        return grass_death;
    }

    public static void setGrass_death(int grass_death) {
        World.grass_death = grass_death;
    }

    public static int getCommon_multiplier() {
        return common_multiplier;
    }

    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    private void spawn(Entity entity) {
        if (entity instanceof Grass){
            grass_grid.put(entity.getPos(), entity);
            getGrass().add(entity);
        } else {
            world_grid.put(entity.getPos(), entity);
            getAll_entities().add(entity);
        }
    }

    public void spawn_random(Supplier<Entity> solver) {
        Entity entity = solver.get();
        int i = Randomizer.rand_range(0, getEmpty_spaces().size());
        entity.setPos(getEmpty_spaces().get(i));
        getEmpty_spaces().remove(i);
        spawn(entity);
    }

    public void spawn_pos(Supplier<Entity> solver, Position pos) {
        Entity entity = solver.get();
        entity.setPos(pos);
        spawn(entity);
    }

    public void despawn(Entity entity) {
        if (entity instanceof Grass){
            grass_grid.remove(entity.getPos());
            getGrass().remove(entity);
        } else {
            world_grid.remove(entity.getPos());
            getAll_entities().remove(entity);
        }
    }

    public void move(Entity entity, Position pos) {
        world_grid.remove(entity.getPos());
        entity.setPos(pos);
        world_grid.put(pos, entity);
    }

    public Entity get(Position pos) {
        return world_grid.get(pos);
    }

    public boolean is_not_out_of_bound(Position pos) {
        if (pos.getX() < 0 || pos.getX() > World.getGridWidth()){
            return false;
        }
        if  (pos.getY() < 0 || pos.getY() > World.getGridHeight()) {
            return false;
        }
        return true;
    }


    public Entity get_grass(Position pos) {
        return grass_grid.get(pos);
    }

    public List<Entity> getAll_entities() {
        return all_entities;
    }

    public List<Entity> getGrass() {
        return grass;
    }

    private List<Position> getEmpty_spaces() {
        return empty_spaces;
    }

    public List<int[]> getDirections() {
        return directions;
    }

    public AlphaWolf getAlpha_wolf() {
        return alpha_wolf;
    }

    public void setAlpha_wolf(AlphaWolf alpha_wolf) {
        this.alpha_wolf = alpha_wolf;
    }
}
