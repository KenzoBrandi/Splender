/*
 * @author    Corentin Dufourg
 * @version     1.1
 * @since       1.0
 */

import java.util.List;
import java.util.ArrayList;

public class Game extends Exception{
    /* L'affichage et la lecture d'entrée avec l'interface de jeu se fera entièrement via l'attribut display de la classe Game.
     * Celui-ci est rendu visible à toutes les autres classes par souci de simplicité.
     * L'intéraction avec la classe Display est très similaire à celle que vous auriez avec la classe System :
     *    - affichage de l'état du jeu (méthodes fournies): Game.display.outBoard.println("Nombre de joueurs: 2");
     *    - affichage de messages à l'utilisateur: Game.display.out.println("Bienvenue sur Splendor ! Quel est ton nom?");
     *    - demande d'entrée utilisateur: new Scanner(Game.display.in);
     */
    private static final int ROWS_BOARD=36, ROWS_CONSOLE=8, COLS=82;
    public static final  Display display = new Display(ROWS_BOARD, ROWS_CONSOLE, COLS);

    private Board board;
    private List<Player> players;

    public static void main(String[] args) {
        //-- à modifier pour permettre plusieurs scénarios de jeu
        display.outBoard.println("Bienvenue sur Splendor !");
        Game game = new Game(2);
        game.play();
        display.close();
    }

    public Game(int nbOfPlayers) throws IllegalArgumentException{
        if(nbOfPlayers < 2 || nbOfPlayers > 4){
            throw new IllegalArgumentException("Nombre de joueurs invalide: 2, 3 ou 4");
        }
        players = new ArrayList<Player>();
        board = new Board();
        if(nbOfPlayers == 2){
            // ajouter un HumanPlayer et un DumbRobotPlayer
        }
        else if(nbOfPlayers == 3){
            // ajouter un HumanPlayer et deux DumbRobotPlayer
        }
        else{
            // ajouter un HumanPlayer et trois DumbRobotPlayer
        }
    }

    public int getNbPlayers(){
        return players.size();
    }

    private void display(int currentPlayer){
        String[] boardDisplay = board.toStringArray();
        String[] playerDisplay = Display.emptyStringArray(0, 0);
        for(int i=0;i<players.size();i++){
            String[] pArr = players.get(i).toStringArray();
            if(i==currentPlayer){
                pArr[0] = "\u27A4 " + pArr[0];
            }
            playerDisplay = Display.concatStringArray(playerDisplay, pArr, true);
            playerDisplay = Display.concatStringArray(playerDisplay, Display.emptyStringArray(1, COLS-54, "\u2509"), true);
        }
        String[] mainDisplay = Display.concatStringArray(boardDisplay, playerDisplay, false);

        display.outBoard.clean();
        display.outBoard.println(String.join("\n", mainDisplay));
    }

    public void play(){
        int tour = 0;
        while( !isGameOver()){
            if(tour % getNbPlayers() == 0){
                players.get(0); // ajouter la suite pour demander l'action du joueur
                // quand les classe seront implémentées
                tour += 1;
            } else if(tour % getNbPlayers() == 1){
                players.get(1); // pareil
                tour += 1;
            } else if (tour % getNbPlayers() == 2){
                players.get(2); // pareil
                tour += 1;
            } else if (tour % getNbPlayers() == 3){
                players.get(3); // pareil
                tour += 1;
            }
        }
    }

    private void move(Player player){ // besoin de classe Player finie
        /*
         * ACOMPLETER
         */
    }

    private void discardToken(Player player){ // pareil
        /*
         * ACOMPLETER
         */
    }

    public boolean isGameOver(){ // pareil
        /*
         * ACOMPLETER
         */
        return false; //-- AMODIFIER
    }

    private void gameOver(){ // paril
        /*
         * ACOMPLETER
         */
    }


}
