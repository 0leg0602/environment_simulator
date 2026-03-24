package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    private Map<Position, Entity> world_grid = new HashMap<>();
    public List<Entity> all_entities = new ArrayList<>();
    List<int[]> directions = new ArrayList<>();

    public static final int GRID_HEIGHT = 100;
    public static final int GRID_WIDTH = 100;


    public World() {
        for (int i_x = -1; i_x <= 1; i_x++) {
            for (int i_y = -1; i_y <= 1; i_y++) {
                if (i_x == 0 && i_y == 0) continue;
                directions.add(new int[]{i_x, i_y});
            }
        }
    }

    public void spawn(Entity entity){
        world_grid.put(entity.pos, entity);
        all_entities.add(entity);
    }

    public void despawn(Entity entity){
        world_grid.remove(entity.pos);
        all_entities.remove(entity);
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



}
