package oware.panel;

import javax.swing.*;

public class GameFrame extends JFrame implements Runnable  {

    public final static int HEIGHT = 400;
    public final static int WIDTH = HEIGHT*16/9;

    @Override
    public void run() {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.setVisible(true);



    }


}
