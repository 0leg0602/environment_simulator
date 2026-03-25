package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static project.Main.world;

public class Painter extends JPanel implements ActionListener {

    private final int DELAY = 16;
    private int TICK_SPEED = 10;
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
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, window_width, window_height);

        Graphics2D g2d = (Graphics2D) g;

        if (System.currentTimeMillis() - old_time > TICK_SPEED){

            for (int i = 0; i < world.all_entities.size(); i++) {
                world.all_entities.get(i).tick();
            }
            for (int i = 0; i < world.grass.size(); i++) {
                world.grass.get(i).tick();
            }

            old_time = System.currentTimeMillis();
        }
        for (int i = 0; i < world.grass.size(); i++) {
            Entity grass = world.grass.get(i);

            g.setColor(grass.color);
            draw_entity(g, grass.pos);

        }

        for (int i = 0; i < world.all_entities.size(); i++) {
            Entity entity = world.all_entities.get(i);
            g.setColor(Color.WHITE);
            double ratio = 0;
            if (entity instanceof Animal animal) {
                ratio = 1.0 - (double) animal.hunger/20;
            }

            int red = (int) (entity.color.getRed() + (255 - entity.color.getRed()) * ratio);
            int green = (int) (entity.color.getGreen() + (0 - entity.color.getGreen()) * ratio);
            int blue = (int) (entity.color.getBlue() + (0 - entity.color.getBlue()) * ratio);
            g.setColor(new Color(red, green, blue));

            draw_entity(g, entity.pos);
        }

        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 300, 65);
        g.setColor(Color.WHITE);
        g2d.drawString("Chlorophyll Casualties: " + String.format("%,d", World.grass_death), 0, 15);
        g2d.drawString("Pillows made: " + String.format("%,d", World.sheep_death), 0, 30);
        g.drawLine(0, 35, 300, 35);
        g2d.drawString("Green Registry:  " + String.format("%,d", world.grass.size()), 0, 50);
        g2d.drawString("Counting Sheep (to Infinity):  " + String.format("%,d", world.all_entities.size()), 0, 65);



        // This forces the graphics pipeline to sync with the display
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw_entity(Graphics g, Position pos){
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

        g.fillRect(x1 + offset, y1, width, height);

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