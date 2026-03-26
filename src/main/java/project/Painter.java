package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static project.Main.world;

public class Painter extends JPanel implements ActionListener, KeyListener {

    private final int DELAY = 16;
    private static int tick_speed = 100;
    private int window_width = 1000;
    private int window_height = 1040;

    private long old_time = System.currentTimeMillis();
    BufferedImage a_well_fed_wold_picture;

    private static boolean text_switch = true;

    public Painter() {
        Timer timer = new Timer(DELAY, this);
        timer.start();

        try {
            a_well_fed_wold_picture = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/A_well_fed_wolf.png")));

        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error (e.g., display a placeholder or message)
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, window_width, window_height);

        Graphics2D g2d = (Graphics2D) g;
        long ancient_time = System.currentTimeMillis();

        if (System.currentTimeMillis() - old_time > tick_speed){

            for (int i = 0; i < world.all_entities.size(); i++) {
                world.all_entities.get(i).tick();
            }
            for (int i = 0; i < world.grass.size(); i++) {
                world.grass.get(i).tick();
            }

            if (world.alpha_wolf != null) {
                world.alpha_wolf.tick();
            }

            old_time = System.currentTimeMillis();
        }
        for (int i = 0; i < world.grass.size(); i++) {
            Entity grass = world.grass.get(i);

            g.setColor(grass.color);
            draw_entity(g, grass.pos, false, false);

        }

        for (int i = 0; i < world.all_entities.size(); i++) {
            Entity entity = world.all_entities.get(i);
            g.setColor(Color.WHITE);
            double ratio = 0;
            if (entity instanceof Sheep animal) {
                ratio = (double) animal.hunger/20;
            }
            g.setColor(new Color(255, (int) (255*ratio), (int) (255*ratio)));

            draw_entity(g, entity.pos, false, false);
        }

        if (world.alpha_wolf != null) {
            if (world.alpha_wolf.prev_pos != null){
                g.setColor(Color.YELLOW);
                int offset = window_width/2 - window_height/2;
                double scale_v = (double) window_height / World.GRID_HEIGHT;
                g2d.setStroke(new BasicStroke(2));

                for (int pos_i = 0; pos_i < world.alpha_wolf.prev_pos.size(); pos_i++) {
                    Position pos1 = world.alpha_wolf.prev_pos.get(pos_i);
                    Position pos2;
                    boolean to_offset = false;

                    if (pos_i == world.alpha_wolf.prev_pos.size() - 1) {
                        pos2 = world.alpha_wolf.pos;
                    } else {
                        pos2 = world.alpha_wolf.prev_pos.get(pos_i+1);
                    }

                    int x1 = (int) (offset + pos1.x * scale_v);
                    int y1 = (int) (pos1.y * scale_v);

                    int x2 = (int) ((offset + (pos2.x * scale_v)));
                    int y2 = (int) (pos2.y * scale_v);
                    g.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine(x1, y1, x2, y2);
                    g.setColor(Color.YELLOW);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawLine(x1, y1, x2, y2);

                }


                if (world.alpha_wolf.prev_pos.isEmpty()) {
                    draw_entity(g, world.alpha_wolf.pos, true, true);
                } else {
                    g.setColor(Color.BLACK);
                    draw_entity(g, world.alpha_wolf.pos, true, false);
                }
            }


        }

        long wait_time = System.currentTimeMillis() - ancient_time;

        g2d.setStroke(new BasicStroke(1));
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, 110);
        g.setColor(Color.WHITE);
        g2d.drawString((text_switch ? "Chlorophyll Casualties: " : "grass eaten: " ) + String.format("%,d", World.grass_death), 0, 15);
        g2d.drawString((text_switch ? "Pillows made: " : "sheep starved: ") + String.format("%,d", World.sheep_death), 0, 30);
        g2d.drawString((text_switch ? "pesky sheep mogged: " : "sheep eaten: ") + String.format("%,d", World.sheep_eaten), 0, 45);
        g.drawLine(0, 50, 300, 50);
        g2d.drawString((text_switch ? "Green Registry: " : "total grass tiles: ") + String.format("%,d", world.grass.size()), 0, 62);
        g2d.drawString((text_switch ? "Counting Sheep (to Infinity): " : "total sheep: ") + String.format("%,d", world.all_entities.size()), 0, 75);
        g.drawLine(0, 80, 300, 80);
        g2d.drawString((text_switch ? "OH MY PC: " : "wait time: ") + String.format("%,d", wait_time), 0, 95);
        g2d.drawString((text_switch ? "Hold your horses lil bro: " : "tick delay: ") + String.format("%,d", tick_speed), 0, 110);
//        System.out.println(text_switch);



        // This forces the graphics pipeline to sync with the display
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw_entity(Graphics g, Position pos, boolean to_offest, boolean is_a_well_fed_wolf){
        double scale_v = (double) window_height / World.GRID_HEIGHT;
//        double scale_h = scale_v;
//        double scale_h = (double) window_width / World.GRID_WIDTH;


        int x1 = (int) (pos.x * scale_v);
        int y1 = (int) (pos.y * scale_v);

        int x2 = (int) ((pos.x + 1) * scale_v);
        int y2 = (int) ((pos.y + 1) * scale_v);

        int width = x2 - x1;
        int height = y2 - y1;
        int offset = window_width/2 - window_height/2;
        x1 += offset;
        if (to_offest){
            x1 -= (int) (scale_v/2);
            y1 -= (int) (scale_v/2);
        }
        int increase = 10 * width * World.common_mult;
        if (is_a_well_fed_wolf) {
            g.drawImage(a_well_fed_wold_picture, x1 - increase/2, y1 - increase/2, width + increase, height + increase, this);
        } else {
            g.fillRect(x1, y1, width, height);
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        window_width = getWidth();
        window_height = getHeight();

        repaint();
    }

    public void create_window(){
        JFrame frame = new JFrame("environment simulator");
        Painter panel = new Painter();

        frame.add(panel);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

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

    @Override
    public void keyReleased(KeyEvent e) {

    }
}