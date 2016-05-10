package oware.game;


public class Main {
    //private int[][] board = {{4,4,4,4,4,4},{4,4,4,4,4,4}};      // normales spielBrett
    private int[][] board = {{1,0,2,5,0,3},{0,0,0,0,0,1}};    // board zum debuggen spezielle züge etc

    public int[] stonesP = {0, 0};                              // die Gewonnen steine im spiel
    public boolean finished = false;
    public boolean forcedPlay = false;


    /**
     * Debugging lebt wuhu
     * @param args
     */
    public static void main(String[] args) {

        Main main = new Main();
        System.out.println(main);

        System.out.println("Next Turn Possible :"+main.saveTurnPossible(main.board,1));

        System.out.println(main);

    }

    /**
     * \_(ツ)_/¯
     */
    public Main() {
    }

    /**
     * gibt das spielfeld schön aus
     * @return
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < board.length; i++) {
            if (i == 0) {
                for (int j = board[i].length - 1; j >= 0; j--) {    //moonwalk
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

    /**
     * Das hier macht den Zug :)
     * @param i int stelle im arry 0 <= i <= 5
     * @param player int Spieler 1||0
     */
    public void makeTurn(int i, int player) {
        //take stones
        int playerGhost = player;
        int iGhost = i;
        int stones = board[playerGhost][iGhost];
        board[playerGhost][iGhost] = 0;

        while (stones > 0) {
            iGhost++; //wichtig zuerst hochzählen \_(ツ)_/¯

            if (iGhost >= board[playerGhost].length) { //Spiel Seitenwechsel
                playerGhost = playerGhost ^ 1;
                iGhost = 0;
            }
            if (playerGhost == player && iGhost == i) { //Stelle wo man Steine entnommen hat
                iGhost++;
            } else {
                board[playerGhost][iGhost] += 1 ;
                stones--;
            }

        }
        System.out.println("Prüfe ob stehlbar");
        //Solange auf Gegnerseite && steine = 3 || 2 nimm steine und gehe eins zurück dann nochmal prüfen etc
        while (iGhost >= 0 && iGhost < 6 && playerGhost != player && (board[playerGhost][iGhost] == 2 || board[playerGhost][iGhost] == 3)) {
            System.out.println("Stehle STeine");
            stonesP[player] += board[playerGhost][iGhost];
            board[playerGhost][iGhost] = 0;
            iGhost--;
        }

        finished();


    }

    private boolean finished() {
        boolean finished = false;
        System.out.println("Prüfe ob finished");
        System.out.println("");
        for (int i = 0; i < board.length; i++) {
            if (countPlayersBoardStones(i) == 0 && !saveTurnPossible(board,i^1)) {
                finished = true;
                this.finished = true;
                System.out.println("Should be Ended");
            }
        }
        return finished;
    }

    /**
     * nächster zug möglich ?
     * @return
     */
    private boolean checkNextPossible(int player) {
        boolean possible = false;
        return possible;
    }

    /**
     * iteriert das feld des spielers und gibt Steine auf seiner Seite zurück
     * @param player int
     * @return int Steine auf player Seite
     */
    private int countPlayersBoardStones(int player) {
        int count = 0;
        for (int i : board[player]) {
            count += i;
        }
        return count;
    }

    /**
     * Gibt ein neues "board" int[]  zurück damit keine verlinkungen entstehen
     * @return int[]
     */
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

    /**
     * setzt das ganze feld auf 4
     */
    public void resetField(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = 4;
            }
        }
    }

    /**
     * setzt alles auf Anfang
     */
    public void resetGame(){
        resetField();
        stonesP[0] = 0;
        stonesP[1] = 0;
        finished = false;
    }

    public boolean saveTurnPossible(int[][] board, int player){
        boolean ret = false;
        int [] turns = getSaveTurns(player);
        for(int i = 0; i < turns.length; i++){
            if (turns[i] == 0){
                ret = true;
                forcedPlay = true;
            }
        }
        return ret;
    }

    /**
     * 0 if turn at i is possible
     * -1 if not possible
     * @param player
     * @return
     */
    public int[] getSaveTurns(int player){
        int[] ret = {-1,-1,-1,-1,-1,-1};
        int zuZiehen;
        for(int i = 0; i < board[player].length; i++){
            zuZiehen = Math.abs(i-6);
            if(zuZiehen <= board[player][i]){
                ret[i] = 0;
            }
        }

        for(int i : ret ){
            System.out.println(i);
        }

        return ret;
    }

    public void collectAll(){
        for(int i = 0; i < board.length ; i++){
            for(int j = 0; j < board[i].length; j++){
                stonesP[i] += board[i][j];
                board[i][j] = 0;
            }
        }
    }

    public void clearBoard(){
        for(int i = 0; i < board.length ; i++){
            for(int j = 0; j < board[i].length; j++){
                board[i][j] = 0;
            }
        }
    }


}
