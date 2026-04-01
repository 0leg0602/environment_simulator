package project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Represents the simulation world containing entities, grass, and an alpha wolf.
 */
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

    /**
     * Constructs a new {@code World} and initializes the direction and the list of empty positions.
     */
    public World() {
        for (int i_x = -1; i_x <= 1; i_x++) {
            for (int i_y = -1; i_y <= 1; i_y++) {
                if (i_x == 0 && i_y == 0) continue;
                directions.add(new int[]{i_x, i_y});
            }
        }
        for (int x = 0; x < getGridWidth(); x++) {
            for (int y = 0; y < getGridHeight(); y++) {
                empty_spaces.add(new Position(x, y));
            }
        }
    }

    /**
     * @return Returns the number of sheep that have died.
     */
    public static int getSheep_death() {
        return sheep_death;
    }

    /**
     * Sets the number of sheep that have died.
     *
     * @param sheep_death the new sheep death count
     */
    public static void setSheep_death(int sheep_death) {
        World.sheep_death = sheep_death;
    }

    /**
     * @return Returns the number of sheep that have been eaten.
     */
    public static int getSheep_eaten() {
        return sheep_eaten;
    }

    /**
     * Sets the number of sheep that have been eaten.
     *
     * @param sheep_eaten the new sheep eaten count
     */
    public static void setSheep_eaten(int sheep_eaten) {
        World.sheep_eaten = sheep_eaten;
    }

    /**
     * @return Returns the number of grass tiles eaten.
     */
    public static int getGrass_death() {
        return grass_death;
    }

    /**
     * Sets the number of grass tiles eaten.
     *
     * @param grass_death the new grass death count
     */
    public static void setGrass_death(int grass_death) {
        World.grass_death = grass_death;
    }

    /**
     * @return Returns the common multiplier used for grid sizing.
     */
    public static int getCommon_multiplier() {
        return common_multiplier;
    }

    /**
     * @return Returns the height of the world grid.
     */
    public static int getGridHeight() {
        return GRID_HEIGHT;
    }

    /**
     * @return Returns the width of the world grid.
     */
    public static int getGridWidth() {
        return GRID_WIDTH;
    }

    /**
     * Places an entity into the appropriate grid and list.
     *
     * @param entity the entity to spawn
     */
    private void spawn(Entity entity) {
        if (entity instanceof Grass){
            grass_grid.put(entity.getPos(), entity);
            getGrass().add(entity);
        } else {
            world_grid.put(entity.getPos(), entity);
            getAll_entities().add(entity);
        }
    }

    /**
     * Spawns a random entity chosen by {@code solver} at a random empty position.
     *
     * @param solver supplies the entity to spawn
     */
    public void spawn_random(Supplier<Entity> solver) {
        Entity entity = solver.get();
        int i = Randomizer.rand_range(0, getEmpty_spaces().size());
        entity.setPos(getEmpty_spaces().get(i));
        getEmpty_spaces().remove(i);
        spawn(entity);
    }

    /**
     * Spawns a random entity chosen by {@code solver} at the specified position {@code pos}.
     *
     * @param solver supplies the entity to spawn
     * @param pos the target position
     */
    public void spawn_pos(Supplier<Entity> solver, Position pos) {
        Entity entity = solver.get();
        entity.setPos(pos);
        spawn(entity);
    }

    /**
     * Removes an entity from the world grid and its list.
     *
     * @param entity the entity to despawn
     */
    public void despawn(Entity entity) {
        if (entity instanceof Grass){
            grass_grid.remove(entity.getPos());
            getGrass().remove(entity);
        } else {
            world_grid.remove(entity.getPos());
            getAll_entities().remove(entity);
        }
    }

    /**
     * Moves an entity to a new position {@code pos}
     *
     * @param entity the entity to move
     * @param pos the target position
     */
    public void move(Entity entity, Position pos) {
        world_grid.remove(entity.getPos());
        entity.setPos(pos);
        world_grid.put(pos, entity);
    }

    /**
     * Retrieves the entity at the given {@code pos} from the world grid.
     *
     * @param pos the position to query
     * @return the entity at {@code pos}
     */
    public Entity get(Position pos) {
        return world_grid.get(pos);
    }

    /**
     * Checks if the position {@code pos} is inside the world bounds.
     *
     * @param pos the position to check
     * @return {@code true} if inside bounds, {@code false} otherwise
     */
    public boolean is_not_out_of_bound(Position pos) {
        if (pos.getX() < 0 || pos.getX() > World.getGridWidth()){
            return false;
        }
        if  (pos.getY() < 0 || pos.getY() > World.getGridHeight()) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the grass entity at the given {@code pos} from the grass grid.
     *
     * @param pos the position of grass
     * @return the grass entity at {@code pos}
     */
    public Entity get_grass(Position pos) {
        return grass_grid.get(pos);
    }

    /**
     * @return Returns the list of all non‑grass entities in the world.
     */
    public List<Entity> getAll_entities() {
        return all_entities;
    }

    /**
     * @return Returns the list of grass entities in the world.
     */
    public List<Entity> getGrass() {
        return grass;
    }

    /**
     * @return Returns the list of empty positions in the world.
     */
    private List<Position> getEmpty_spaces() {
        return empty_spaces;
    }

    /**
     * @return Returns the list of directions.
     */
    public List<int[]> getDirections() {
        return directions;
    }

    /**
     * @return Returns the alpha wolf
     */
    public AlphaWolf getAlpha_wolf() {
        return alpha_wolf;
    }

    /**
     * Sets the alpha wolf.
     *
     * @param alpha_wolf the new alpha wolf
     */
    public void setAlpha_wolf(AlphaWolf alpha_wolf) {
        this.alpha_wolf = alpha_wolf;
    }
}
