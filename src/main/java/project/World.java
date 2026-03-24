package project;

import java.util.*;

public class World {
    private Map<Position, Entity> world_grid = new HashMap<>();
    public List<Entity> all_entities = new ArrayList<>();

    public void spawn(Entity entity){
        world_grid.put(entity.pos, entity);
        all_entities.add(entity);
    }

    public void move(Entity entity, Position pos){
        world_grid.remove(entity.pos);
        entity.pos = pos;
        world_grid.put(pos, entity);
    }

    public Entity get(Position pos){
        return world_grid.get(pos);
    }

    List<int[]> directions = new ArrayList<>();

    public World() {
        for (int i_x = -1; i_x <= 1; i_x++) {
            for (int i_y = -1; i_y <= 1; i_y++) {
                if (i_x == 0 && i_y == 0) continue;
                directions.add(new int[]{i_x, i_y});
            }
        }
    }
}
