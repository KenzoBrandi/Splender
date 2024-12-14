 

/*
 * @author    Corentin Dufourg
 * @version     1.1
 * @since       1.0
 */
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public static void main(String[] args)  throws IntNotValidException{
        //-- à modifier pour permettre plusieurs scénarios de jeu
        display.outBoard.println("Bienvenue sur Splendor !");
        display.outBoard.println("Démarrage du jeu !");
        
        Game game = new Game(2);
        game.play();
        
        display.close();
    }

    /**
     * Constructeur
     */
    public Game(int nbOfPlayers){
        if(nbOfPlayers < 2 || nbOfPlayers > 4){
            throw new IllegalArgumentException("Nombre de joueurs invalide: 2, 3 ou 4");
        }
        
        players = new ArrayList<Player>();
        board = new Board(nbOfPlayers);
        display.out.println("Veuillez entrer votre nom de joueur: ");
        Scanner scanner = new Scanner(display.in);
        String name = scanner.nextLine();
        if(nbOfPlayers == 2){
            // ajouter un HumanPlayer
            HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
            players.add(humanPlayer);
            //et un DumbRobotPlayer
            DumbRobotPlayer dumbRobotPlayer = new DumbRobotPlayer("Robot",1,board);
            players.add(dumbRobotPlayer);
        }
        else if(nbOfPlayers == 3){
            // ajouter un HumanPlayer
            HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
            players.add(humanPlayer);
            // et deux DumbRobotPlayer
            DumbRobotPlayer dumbRobotPlayer1 = new DumbRobotPlayer("Robot1",1,board);
            players.add(dumbRobotPlayer1);
            DumbRobotPlayer dumbRobotPlayer2 = new DumbRobotPlayer("Robot2",2,board);
            players.add(dumbRobotPlayer2);
        }
        else{
            // ajouter un HumanPlayer
            HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
            players.add(humanPlayer);
            // et trois DumbRobotPlayer
            DumbRobotPlayer dumbRobotPlayer1 = new DumbRobotPlayer("Robot1",1,board);
            players.add(dumbRobotPlayer1);
            DumbRobotPlayer dumbRobotPlayer2 = new DumbRobotPlayer("Robot2",2,board);
            players.add(dumbRobotPlayer2);
            DumbRobotPlayer dumbRobotPlayer3 = new DumbRobotPlayer("Robot3",3,board);
            players.add(dumbRobotPlayer3);
        }

    }

    /**
     * Getter du nombre de joueurs
     */
    public int getNbPlayers(){
        return players.size();
    }
    
    /**
     *Permet l'affichage du jeu
     */
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
    
    /**
     * Gère une partie
     */
    public void play() throws IntNotValidException{
        int tour = 0;
        while( !isGameOver()){
            if(tour % getNbPlayers() == 0){
               display(0);
                move(players.get(0)); 
                tour += 1;
            } else if(tour % getNbPlayers() == 1){
                display(1);
                move(players.get(1));
                tour += 1;

            } else if (tour % getNbPlayers() == 2){
                display(2);
                move(players.get(2));
                tour += 1;

            } else if (tour % getNbPlayers() == 3){
                display(3);
                move(players.get(3));
                tour += 1;
            }
        }
        gameOver();
    }
    
    /**
     * Choisi une action entre prendre des jetons, acheter une carte ou passer son tour
     */
    private void move(Player player) throws IntNotValidException{
        Action actionChoisie = null;
        if (player.getNbTokens() > 10){
            actionChoisie = discardToken(player);
        }
        else{
            actionChoisie = player.chooseAction();
        }
        actionChoisie.process(board);
        display.out.println(player.getName()+" " +actionChoisie.toString());
        display.out.println("Jetons total: "+ player.getNbTokens());
        display.out.println("--------------------------------------------------------");
    }
    
    /**
     * Retire les jetons selectionnés du joueurs et les remets sur le plateau
     */
    private DiscardTokensAction discardToken(Player player){
        HashMap<Resource, Integer> tokensToDiscard = player.chooseDiscardingTokens();
        DiscardTokensAction discardAction = new DiscardTokensAction(tokensToDiscard);
        return discardAction;
    }
    
    /**
     * Vérifie si la partie est terminée si un joueur possède 15 points ou plus
     */
    public boolean isGameOver(){ 
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPoints() >= 15){
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Affiche le message de victoire pour le joueur gagnant
     */
    private void gameOver(){
        ArrayList<Player> gagnants = new ArrayList<Player>();
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPoints() >= 15){
                gagnants.add(players.get(i));
            }
        }
        
        if(gagnants.size() == 1){
            System.out.println("Le joueur " + gagnants.get(0) + " a gagner.");
        } else{
            int min = gagnants.get(0).getNbPurchasedCards();
            int p = 0;
            for(int k = 1; k < gagnants.size(); k++){
                if(gagnants.get(k).getNbPurchasedCards() < min){
                    min = gagnants.get(k).getNbPurchasedCards();
                    p = k;
                }
            }
            System.out.println("Lejoueur " + gagnants.get(p) + " a gagner.");
        }
    }
}
