package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static project.Main.world;

public class Painter extends JPanel implements ActionListener {

    private final int DELAY = 16;
    private int TICK_SPEED = 300;
    private int window_width = 1000;
    private int window_height = 1040;

    private long old_time = System.currentTimeMillis();

    public Painter() {
        Timer timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, window_width, window_height);

        if (System.currentTimeMillis() - old_time > TICK_SPEED){
            System.out.println("There are " + world.all_entities.size() + " entities");

            for (int i = 0; i < world.all_entities.size(); i++) {
                world.all_entities.get(i).tick();
            }

            old_time = System.currentTimeMillis();
        }

        for (int i = 0; i < world.all_entities.size(); i++) {
            Entity entity = world.all_entities.get(i);
            g.setColor(Color.WHITE);
            if (entity instanceof Animal animal) {
                int color_strength = (int) ((animal.hunger / 25.0) * 255);
                g.setColor(new Color(255, color_strength, color_strength));
            }
            draw_entity(g, entity.pos);
        }


        // This forces the graphics pipeline to sync with the display
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw_entity(Graphics g, Position pos){
        double scale_v = (double) window_height /World.GRID_HEIGHT;
        double scale_h = (double) window_width /World.GRID_WIDTH;

        g.fillRect((int) (pos.x * scale_h), (int) (pos.y * scale_v), (int) scale_h, (int) scale_v);

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
        frame.setSize(window_width, window_height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}