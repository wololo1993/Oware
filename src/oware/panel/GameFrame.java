package oware.panel;


/*TODO steal all what to do;
* */

import oware.game.Main;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame implements Runnable {

    public final static int HEIGHT = 400;
    public final static int WIDTH = HEIGHT * 16 / 9;
    private boolean running;                                //always true (exit on close)
    Main game = new Main();

    //Textfields
    private JTextField textField;                           //textfield gibt an Welcher Spieler
                                                            // dran is spaeter gewonnen etc Unten
    private JTextField player1Points = new JTextField("");  //spieler "0" punkte Oben
    private JTextField player2Points = new JTextField("");  //spieler "1" punkte Oben

    private JButton reload = new JButton("Reaload");
    private JButton resetGame = new JButton("Reset Field"); //selbsterklärend
    public int player = 0;                                  //aktueller spieler (später zufällig wer zuerst
    private int[] bla;                                      //bla (vorläufigername) enthält das board aus game

    private JButton[] buttons = new JButton[12];            //sammlung der Buttons bzw Spielfelds

    /**
     * Startet Game Thread \(^,^)/
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new GameFrame());

        thread.start();
    }

    /**
     * initalliesiert alles JObjekte und geht dann in endlosschleife
     * sollte später in endlosschleife drauf warten das ein Button gedrückt wird
     * dann iwie über call() GameInputHandler
     */
    @Override
    public void run() {
        //this is a frame and here it getsa itsa Eigenschafta
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);

        //noch nicht initallisiert ? jetzt seid ihrs
        textField = new JTextField("");
        JPanel bigPanel = new JPanel();
        JPanel OverPanel = new JPanel();
        JPanel UnderPanel = new JPanel();
        JPanel panel = new JPanel();

        //set Layouts
        bigPanel.setLayout(new GridLayout(3,1));
        panel.setLayout(new GridLayout(2, 6));
        UnderPanel.setLayout(new FlowLayout());
        OverPanel.setLayout(new FlowLayout());

        // jetzt adden wir sachen
        UnderPanel.add(textField,CENTER_ALIGNMENT);
        OverPanel.add(player1Points);
        OverPanel.add(player2Points);
        resetGame.addActionListener(e -> {game.resetGame(); update();});    //bevor geadded bekommt der reset button
                                                                            // hier seine Funktion
        reload.addActionListener(e -> {update();
            System.out.println("Reloaded");});

        UnderPanel.add(resetGame);
        UnderPanel.add(reload);

        //Hier sehen Sie wie Buttons Gemacht werden sieht komisch aus is aber so

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setBackground(Color.BLUE);                           //setBackground will nich ? why auch ever
            buttons[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));

            buttons[i].addActionListener(e -> {                             //Hier bekommen die Babys ihre Funktion
                for (int i1 = 0; i1 < buttons.length; i1++) {               // bye bye new Actionlistener das geht jetzt
                    if (e.getSource() == buttons[i1]) {                     // per lambda

                        if (i1 > 5) i1 = i1 - 6;                            //umwandlung von 6-12 auf 0-5 (buttens 12)
                                                                            // aber board[2][0-5!]

                        else i1 =Math.abs(i1 -5);                           //hier wird aus 5 die 0 und aus der 0 die 5
                                                                            // verkehrte welt... weil das spiel es ja
                                                                            // nunmal so will

                        game.makeTurn(i1, player);                          //wichtig ruft game auf und vollzieht den
                        break;                                              // Zug
                    }
                }
                                                    //jetzt rücken wir die commis mal näher ran
                player = player^1;                  //aus 1 mach 0 aus 0 mach 1
                update();

            });
            panel.add(buttons[i]);                  //und schließlich kommen die kleinen Buttons in das panel

        }

        bigPanel.add(OverPanel);                //alles ins bigPanel
        bigPanel.add(panel);
        bigPanel.add(UnderPanel);

        this.getContentPane().add(bigPanel);    //und schließlich das ganze in this frame

        update();                               //nochmal updaten damits auch gleich richtig aussieht

        this.setVisible(true);                  //jetzt darf mans endlich sehen wuhu

        running = true;                         //sport ist mord


        while (running) {                       //endlos loop sorgt dafür das das Game immer weiterläuft auch wenns
                                                // da drin eig garnichts macht TODO call by Action

            try {
                Thread.sleep(1000000);              //das beweist mainThread ist grad noch totaaaal unnötig
            } catch (InterruptedException e) {      // geht auch so noch alles und so is die auslastung geringer ^^
                e.printStackTrace();
            }
        }
    }

    /**
     * update bekommt das aktuelle feld aus game und speicherts in bla
     * und setzt dann alle buttons auf den neusten wert dann schauts welche es ausschalten darf
     * bzw aktivieren muss von letzter runde
     *
     * hier soll noch geschaut werden ob ein Zug erforderlich ist um den gegner zu Retten
     * wenn ja nur dieser zug möglich
     *
     *
     * \_(ツ)_/¯
     *
     */
    private void update()  {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(game.finished){

            JOptionPane.showMessageDialog(this, "Game Finished");
            JOptionPane.showMessageDialog(this, "Player0:"+game.stonesP[0]+" Player1:"+game.stonesP[1]);

            System.out.println("Game Ends");
        }

        bla = game.getField();

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("[" + bla[i] + "]");

            if(i < 6 && player == 1){
                buttons[i].setEnabled(false);
            }else if(i >= 6 && player == 1){
                buttons[i].setEnabled(true);
            }else if(i >= 6 && player == 0){
                buttons[i].setEnabled(false);
            } else if(i < 6 && player == 0){
                buttons[i].setEnabled(true);
            }
            if(buttons[i].getText().equals("[0]")){
                buttons[i].setEnabled(false);
            }
        }
        if(game.forcedPlay){
            System.out.println("forced Play !");
            int[] possible = game.getSaveTurns(player);
            for(int i = 0 ; i < buttons.length;i++){
                buttons[i].setEnabled(false);
            }
            for(int i = 0 ; i < possible.length; i++){
                if(player == 0 && possible[i]==0){
                   buttons[i].setEnabled(true);
                } else if(player == 1 && possible[i]==0){
                    buttons[i+6].setEnabled(true);
                }
            }
            game.forcedPlay = false;
        }


        textField.setText("Player"+player+" am Zug");
        player1Points.setText("Player 0 has "+game.stonesP[0]+" Points");
        player2Points.setText("Player 1 has "+game.stonesP[1]+" Points");

        System.out.println("fertig");


    }

}
