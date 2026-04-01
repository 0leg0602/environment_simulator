package project;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static project.Main.getWorld;

public class Painter extends JPanel implements ActionListener, KeyListener {

    private static int tick_speed = 100;
    private int window_width = 1000;
    private int window_height = 1040;
    static int frame_delay = 16;


    private long old_time = System.currentTimeMillis();
    private BufferedImage a_well_fed_wold_picture;

    private static boolean text_switch = false;

    /**
     * Creates a new {@code Painter} panel.
     * Starts a {@link javax.swing.Timer} that calls {@link #actionPerformed(java.awt.event.ActionEvent)} every {@value frame_delay}ms.
     */
    public Painter() {
        Timer timer = new Timer(frame_delay, this);
        timer.start();

        try {
            a_well_fed_wold_picture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/A_well_fed_wolf.png")));

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., display a placeholder or message)
        }
    }

    /**
     * Paints the simulation world onto this panel.
     * Handles entity ticking, drawing of grass, animals, and the UI statistics panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, window_width, window_height);

        Graphics2D g2d = (Graphics2D) g;
        long ancient_time = System.currentTimeMillis();

        if (System.currentTimeMillis() - old_time > tick_speed){

            for (int i = 0; i < getWorld().getAll_entities().size(); i++) {
                getWorld().getAll_entities().get(i).tick();
            }
            for (int i = 0; i < getWorld().getGrass().size(); i++) {
                getWorld().getGrass().get(i).tick();
            }

            if (getWorld().getAlpha_wolf() != null) {
                getWorld().getAlpha_wolf().tick();
            }

            old_time = System.currentTimeMillis();
        }
        for (int i = 0; i < getWorld().getGrass().size(); i++) {
            Entity grass = getWorld().getGrass().get(i);

            g.setColor(grass.getColor());
            draw_entity(g, grass.getPos(), false, false);

        }

        for (int i = 0; i < getWorld().getAll_entities().size(); i++) {
            Entity entity = getWorld().getAll_entities().get(i);
            g.setColor(Color.WHITE);
            double ratio = 0;
            if (entity instanceof Sheep animal) {
                ratio = (double) animal.getHunger() /20;
            }
            g.setColor(new Color(255, (int) (255*ratio), (int) (255*ratio)));

            draw_entity(g, entity.getPos(), false, false);
        }

        if (getWorld().getAlpha_wolf() != null) {
            if (getWorld().getAlpha_wolf().getPrev_pos() != null){
                g.setColor(Color.YELLOW);
                int offset = window_width/2 - window_height/2;
                double scale_v = (double) window_height / World.getGridHeight();
                g2d.setStroke(new BasicStroke(2));

                for (int pos_i = 0; pos_i < getWorld().getAlpha_wolf().getPrev_pos().size(); pos_i++) {
                    Position pos1 = getWorld().getAlpha_wolf().getPrev_pos().get(pos_i);
                    Position pos2;

                    if (pos_i == getWorld().getAlpha_wolf().getPrev_pos().size() - 1) {
                        pos2 = getWorld().getAlpha_wolf().getPos();
                    } else {
                        pos2 = getWorld().getAlpha_wolf().getPrev_pos().get(pos_i+1);
                    }

                    int x1 = (int) (offset + pos1.getX() * scale_v);
                    int y1 = (int) (pos1.getY() * scale_v);

                    int x2 = (int) ((offset + (pos2.getX() * scale_v)));
                    int y2 = (int) (pos2.getY() * scale_v);
                    g.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(x1, y1, x2, y2);
                    g.setColor(Color.YELLOW);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x1, y1, x2, y2);

                }


                if (getWorld().getAlpha_wolf().getPrev_pos().isEmpty()) {
                    draw_entity(g, getWorld().getAlpha_wolf().getPos(), true, true);
                } else {
                    g.setColor(Color.BLACK);
                    draw_entity(g, getWorld().getAlpha_wolf().getPos(), true, false);
                }
            }


        }

        long wait_time = System.currentTimeMillis() - ancient_time;

        g2d.setStroke(new BasicStroke(1));
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, 110);
        g.setColor(Color.WHITE);
        g2d.drawString((text_switch ? "Chlorophyll Casualties: " : "grass eaten: " ) + String.format("%,d", World.getGrass_death()), 0, 15);
        g2d.drawString((text_switch ? "Pillows made: " : "sheep starved: ") + String.format("%,d", World.getSheep_death()), 0, 30);
        g2d.drawString((text_switch ? "pesky sheep mogged: " : "sheep eaten: ") + String.format("%,d", World.getSheep_eaten()), 0, 45);
        g.drawLine(0, 50, 300, 50);
        g2d.drawString((text_switch ? "Green Registry: " : "total grass tiles: ") + String.format("%,d", getWorld().getGrass().size()), 0, 62);
        g2d.drawString((text_switch ? "Counting Sheep (to Infinity): " : "total sheep: ") + String.format("%,d", getWorld().getAll_entities().size()), 0, 75);
        g.drawLine(0, 80, 300, 80);
        g2d.drawString((text_switch ? "OH MY PC: " : "wait time: ") + String.format("%,d", wait_time), 0, 95);
        g2d.drawString((text_switch ? "Hold your horses lil bro: " : "tick delay: ") + String.format("%,d", tick_speed), 0, 110);

        // This forces the graphics pipeline to sync with the display
        Toolkit.getDefaultToolkit().sync();
    }


    /**
     * @param pos                 the entity's {@link Position} to be rendered.
     * @param to_offest           if {@code true} the entity is drawn offset to the left‑top corner (used for the wolf’s rendering).
     * @param is_a_well_fed_wolf  if {@code true} then “well‑fed wolf” image is drawn otherwise a plain rectangle is drawn.
     */
    private void draw_entity(Graphics g, Position pos, boolean to_offest, boolean is_a_well_fed_wolf){
        double scale_v = (double) window_height / World.getGridHeight();
        // Scale only vertically not horizontally, in order to make my simulation display a square

        int x1 = (int) (pos.getX() * scale_v);
        int y1 = (int) (pos.getY() * scale_v);

        int x2 = (int) ((pos.getX() + 1) * scale_v);
        int y2 = (int) ((pos.getY() + 1) * scale_v);

        int width = x2 - x1;
        int height = y2 - y1;
        int offset = window_width/2 - window_height/2;
        x1 += offset;
        if (to_offest){
            x1 -= (int) (scale_v/2);
            y1 -= (int) (scale_v/2);
        }
        int increase = 10 * width * World.getCommon_multiplier();
//          Draw it in the center of the window
        if (is_a_well_fed_wolf) {
            g.drawImage(a_well_fed_wold_picture, x1 - increase/2, y1 - increase/2, width + increase, height + increase, this);
        } else {
            g.fillRect(x1, y1, width, height);
        }

    }


    /**
     * Called by the {@link javax.swing.Timer}; updates the panel size and repaints.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        window_width = getWidth();
        window_height = getHeight();

        repaint();
    }

    /**
     * Creates the simulation window, adds this panel, and registers the key listener.
     */
    public void create_window(){
        JFrame frame = new JFrame("environment simulator");
        Painter panel = new Painter();

        frame.add(panel);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
    }

    /**
     * Invoked when a key is typed (not used).
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }


    /**
     * Handles key presses: toggles statistics text, and adjusts the tick speed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
//        System.out.println(e);
        if (e.getKeyCode() == KeyEvent.VK_S) {
            text_switch = !text_switch;
        }
        if (e.getKeyCode() == KeyEvent.VK_MINUS) {
            tick_speed -= 10;
        }

        if (e.getKeyCode() == KeyEvent.VK_EQUALS) {
            tick_speed += 10;
        }
    }


    /**
     * Invoked when a key is released (not used).
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
}