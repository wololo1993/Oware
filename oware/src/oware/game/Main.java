package oware.game;

public class Main {
    private int[][] board = new int[2][6];
    private int[] boardP1 = board[0];
    private int[] boardP2 = board[1];
    public int[] stonesP = {0, 0};


    public static void main(String[] args) {

        Main main = new Main();
        System.out.println(main);
        System.out.println("make trun");
        main.makeTurn(4, 0);
        System.out.println("printing");
        System.out.println(main);
        System.out.println("print end");

    }

    public Main() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = 4;
            }
        }

    }

    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                for (int j = board[i].length - 1; j >= 0; j--) {
                    s = s + "[" + board[i][j] + "]";
                }
            } else {
                for (int j = 0; j < board[i].length; j++) {
                    s = s + "[" + board[i][j] + "]";
                }
            }
            s = s + "\n";
        }
        return s;
    }


    public void makeTurn(int i, int player) {
        //take stones
        int playerGhost = player;
        int iGhost = i;
        int stones = board[playerGhost][iGhost];
        board[playerGhost][iGhost] = 0;

        while (stones > 0) {
            if (iGhost >= board[playerGhost].length) {
                playerGhost = playerGhost ^ 1;
                iGhost = 0;
            }
            if (playerGhost == player && iGhost == i) {
                iGhost++;
            } else {
                board[playerGhost][iGhost]++;
                iGhost++;
                stones--;
            }
        }
        /*
        if(iGhost == 0){
            player = player ^ 1;
            iGhost = 6;
        } else {
            iGhost--;
        }*/

        //check catch

        System.out.println("bei : "+player+"  i:"+i);
        System.out.println("Ghost : "+playerGhost+" iG:"+iGhost);

        if(iGhost >= 6){
            System.out.println("ERROR iGhost ist "+iGhost+" bei i="+i);
        }
        if(playerGhost != player){
            System.out.println("Bin bei Gegner !");
        }
        if((board[playerGhost][iGhost] == 2 || board[playerGhost][iGhost] == 3)){
            System.out.println("Steine Sammeln bitte");
        }

        System.out.println("Beinhaltet : "+board[playerGhost][iGhost]);



        while (iGhost >= 0 && iGhost < 6 && playerGhost != player && (board[playerGhost][iGhost] == 2 || board[playerGhost][iGhost] == 3)) {
            System.out.println("Hier Will ich hin");
            stonesP[player] += board[playerGhost][iGhost];
            board[playerGhost][iGhost] = 0;
            iGhost--;
        }

        finished();


    }

    private boolean finished() {
        boolean finished = false;
        for (int i = 0; i < board.length; i++) {
            if (countPlayersBoardStones(i) == 0 && !checkNextPossible()) {
                finished = true;
            }
        }
        return finished;
    }

    private boolean checkNextPossible() {


        return false;
    }

    private int countPlayersBoardStones(int player) {
        int count = 0;
        for (int i : board[player]) {
            count += i;
        }
        return count;
    }

    public int[] getField() {
        int[] ret = new int[12];
        int c = 0;

        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                for (int j = board[i].length - 1; j >= 0; j--) {
                    ret[c] = board[i][j];
                    c++;
                }
            } else {
                for (int j = 0; j < board[i].length; j++) {
                    ret[c] = board[i][j];
                    c++;
                }
            }


        }

        return ret;

    }


}
