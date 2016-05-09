package oware.panel;


import oware.game.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameFrame extends JFrame implements Runnable {

    public final static int HEIGHT = 400;
    public final static int WIDTH = HEIGHT * 16 / 9;
    private boolean running;
    Main game = new Main();
    private JTextField textField;
    private JTextField player1Points = new JTextField("");
    private JTextField player2Points = new JTextField("");
    public int player = 0;
    private int[] bla;




    public JButton[] buttons = new JButton[12];

    public static void main(String[] args) {
        Thread thread = new Thread(new GameFrame());

        thread.start();
    }

    @Override
    public void run() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        textField = new JTextField("");

        JPanel bigPanel = new JPanel();
        JPanel OverPanel = new JPanel();
        JPanel UnderPanel = new JPanel();

        OverPanel.setLayout(new FlowLayout());
        OverPanel.add(player1Points);
        OverPanel.add(player2Points);


        UnderPanel.setLayout(new FlowLayout());

        UnderPanel.add(textField,CENTER_ALIGNMENT);

        bigPanel.setLayout(new GridLayout(3,1));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 6));


        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            //if (i < 6) buttons[i].setEnabled(false);
            //buttons[i].setLayout(new GridLayout(2, 1));

            buttons[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < buttons.length; i++) {
                        if (e.getSource() == buttons[i]) {
                            System.out.println("Bei i: "+i+" Wird zu "+(i-6));

                            if (i > 5) i = i - 6;
                            else i=Math.abs(i-5);

                            game.makeTurn(i, player);
                            break;
                        }
                    }
                    player = player^1;
                    update();
                }
            });
            panel.add(buttons[i]);

            //else buttons[i].addActionListener();
        }

        bigPanel.add(OverPanel);
        bigPanel.add(panel);
        bigPanel.add(UnderPanel);

        this.getContentPane().add(bigPanel);

        update();

        this.setVisible(true);

        running = true;



        while (running) {

        }
    }

    private void update(){
        bla = game.getField();
        for (int i = 0; i < buttons.length; i++) {
            if(i < 6 && player == 1){
                buttons[i].setEnabled(false);
            }if(i >= 6 && player == 1){
                buttons[i].setEnabled(true);
            }
            if(i >= 6 && player == 0){
                buttons[i].setEnabled(false);
            }if(i < 6 && player == 0){
                buttons[i].setEnabled(true);
            }
            buttons[i].setBackground(Color.BLUE);
            buttons[i].setText("[" + bla[i] + "]");
            buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        }
        textField.setText("Player"+player+" am Zug");
        player1Points.setText("Player 0 has "+game.stonesP[0]+" Points");
        player2Points.setText("Player 1 has "+game.stonesP[1]+" Points");

    }

    private void drawGrid() {

    }


}
