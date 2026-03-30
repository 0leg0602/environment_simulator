package project;


/**
 * @author oleg
 * <br>
 * <br>
 * time-start: ???
 * <br>
 * time-end: ???
 * <br>
 * Java Environment Simulator.
 * Grass always spreads and grows.
 * Each grass tile has a random color.
 * The speed at which grass grows depends on the total amount of grass: the less grass there is, the faster it grows.
 * When a sheep eats a certain amount of grass it performs <code>unforeseen_anomalous_impactful_sheep_mitosis()</code> and creates a new sheep in a random free nearby cell.
 * When a sheep gets hungry, it slowly turns red; when it is fully out of food it dies.
 * There is only one wolf. When the wolf is not hungry he just stands still and is drawn on the screen as a picture.
 * When he gets hungry he switches to hunt mode and starts hunting sheep. The number of sheep he eats depends on the scale of the grid (`common_multiplier`) and the total number of sheep.
 * The wolf hunts by searching nearby cells and then jumping to the closest sheep, while recording his previous steps.
 * When the wolf is being drawn, all his previous steps are drawn as a lightning‑path for a cooler effect.
 * The `common_multiplier` corresponds to the scale of the grid.
 * <br>
 * Additionaly to the base simulator, some values are displayed in the top‑left corner:
 * <ul>
 *     <li> Grass eaten
 *     <li> Sheep starved
 *     <li> Sheep eaten
 *     <li> Total grass tiles
 *     <li> Total sheep
 *     <li> Wait time
 *     <li> Tick delay
 * </ul>
 *  The program supports the following keyboard actions:
 * <ul>
 *  <li> <code>+</code> or <code>-</code> to increase or decrease the delay.
 *  <li> Pressing `c` toggles the funny values display.
 * </ul>
 */
public class Main {
    private static final World world = new World();

    public static void main(String[] args){
        Painter painter = new Painter();
        init_entities();
        painter.create_window();
    }

    private static void init_entities() {
        for (int i = 0; i < 100 * World.getCommon_multiplier(); i++) {
            getWorld().spawn_random(Sheep::new);
        }
        for (int i = 0; i < 10 * World.getCommon_multiplier(); i++) {
            getWorld().spawn_random(Grass::new);
        }
        AlphaWolf woof = new AlphaWolf();
        woof.setPos(new Position(World.getGridWidth() /2, World.getGridHeight() /2));

    }

    public static World getWorld() {
        return world;
    }
}