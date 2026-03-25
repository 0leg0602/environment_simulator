package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class World {
    private Map<Position, Entity> world_grid = new HashMap<>();
    private Map<Position, Entity> grass_grid = new HashMap<>();
    public List<Entity> all_entities = new ArrayList<>();
    public List<Entity> grass = new ArrayList<>();
    public List<Position> empty_spaces = new ArrayList<>();
//    public List<Position> empty_grass_spaces = new ArrayList<>();
    List<int[]> directions = new ArrayList<>();

    public static int sheep_death = 0;
    public static int grass_death = 0;

    public static int common_mult = 10;

    public static int GRID_HEIGHT = 100 * common_mult;
    public static int GRID_WIDTH = 100 * common_mult;



    public World() {
        for (int i_x = -1; i_x <= 1; i_x++) {
            for (int i_y = -1; i_y <= 1; i_y++) {
                if (i_x == 0 && i_y == 0) continue;
                directions.add(new int[]{i_x, i_y});
            }
        }
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                empty_spaces.add(new Position(x, y));
            }
        }
    }

    public void spawn(Entity entity){
        if (entity instanceof Grass){
            grass_grid.put(entity.pos, entity);
            grass.add(entity);
        } else {
            world_grid.put(entity.pos, entity);
            all_entities.add(entity);
        }
    }

    public void spawn_random(Supplier<Entity> solver){
        Entity entity = solver.get();
        int i = Randomizer.rand_range(0, empty_spaces.size());
        entity.pos = empty_spaces.get(i);
        empty_spaces.remove(i);
        spawn(entity);
    }

    public void spawn_pos(Supplier<Entity> solver, Position pos){
        Entity entity = solver.get();
        entity.pos = pos;
        spawn(entity);
    }

    public void despawn(Entity entity){
        if (entity instanceof Grass){
            grass_grid.remove(entity.pos);
            grass.remove(entity);
        } else {
            world_grid.remove(entity.pos);
            all_entities.remove(entity);
        }
    }

    public void move(Entity entity, Position pos){
        world_grid.remove(entity.pos);
        entity.pos = pos;
        world_grid.put(pos, entity);
    }

    public Entity get(Position pos){
        return world_grid.get(pos);
    }

    public boolean is_not_out_of_bound(Position pos){
        if (pos.x < 0 || pos.x > World.GRID_WIDTH){
            return false;
        }
        if  (pos.y < 0 || pos.y > World.GRID_HEIGHT){
            return false;
        }
        return true;
    }


    public Entity get_grass(Position pos) {
        return grass_grid.get(pos);
    }
}
