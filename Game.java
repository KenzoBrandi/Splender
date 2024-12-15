 

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
        Game game = null;
        if (args.length == 1){
            //Partie où l'utilisateur décide du nombre de joueurs
            game = new Game(Integer.parseInt(args[0]));
        } else if (args.length == 2){
            if (Integer.parseInt(args[1])== 0){
                game = new Game(Integer.parseInt(args[0]));
            } else {
                //Partie où l'utilisateur décide du nombre de joueurs et celui des joueurs humains
                game = new Game(Integer.parseInt(args[0]),Integer.parseInt(args[1]));    
            }
        }
        
        if (game != null){
            game.play();
        }
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Le thread a été interrompu !");
        }
        
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
     * Constucteur avec plusieurs humains
     */
    public Game(int nbOfPlayers,int nbOfHumans){
        if(nbOfPlayers < 2 || nbOfPlayers > 4){
            throw new IllegalArgumentException("Nombre de joueurs invalide: 2, 3 ou 4");
        }
        if (nbOfHumans > nbOfPlayers){
            throw new IllegalArgumentException("Nombre de joueurs humains > joueurs totals");
        }
        players = new ArrayList<Player>();
        board = new Board(nbOfPlayers);
        
        Scanner scanner = new Scanner(display.in);
        
        if (nbOfPlayers == 2){
                if (nbOfHumans == 1){
                    // ajouter un HumanPlayer
                    display.out.println("Veuillez entrer votre nom de joueur: ");
                    String name = scanner.nextLine();
                    HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
                    players.add(humanPlayer);
                    //et un DumbRobotPlayer
                    DumbRobotPlayer dumbRobotPlayer = new DumbRobotPlayer("Robot",1,board);
                    players.add(dumbRobotPlayer);
                } else {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    
                }
                
        }else if (nbOfPlayers == 3) {
                if (nbOfHumans == 1){
                    // ajouter un HumanPlayer
                    display.out.println("Veuillez entrer votre nom de joueur: ");
                    String name = scanner.nextLine();
                    HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
                    players.add(humanPlayer);
                    //et un DumbRobotPlayer1
                    DumbRobotPlayer dumbRobotPlayer1 = new DumbRobotPlayer("Robot1",1,board);
                    players.add(dumbRobotPlayer1);
                    //et un DumbRobotPlayer2
                    DumbRobotPlayer dumbRobotPlayer2 = new DumbRobotPlayer("Robot2",2,board);
                    players.add(dumbRobotPlayer2);
                } else if (nbOfHumans == 2) {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    //et un DumbRobotPlayer
                    DumbRobotPlayer dumbRobotPlayer = new DumbRobotPlayer("Robot",2,board);
                    players.add(dumbRobotPlayer);
                    
                } else {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    // ajouter un HumanPlayer3
                    display.out.println("Veuillez le nom du 3è joueur: ");
                    String name3 = scanner.nextLine();
                    HumanPlayer humanPlayer3 = new HumanPlayer(name3,2,board);
                    players.add(humanPlayer3);
                }
                
        } else {
                if (nbOfHumans == 1){
                    // ajouter un HumanPlayer
                    display.out.println("Veuillez entrer votre nom de joueur: ");
                    String name = scanner.nextLine();
                    HumanPlayer humanPlayer = new HumanPlayer(name,0,board);
                    players.add(humanPlayer);
                    //et un DumbRobotPlayer1
                    DumbRobotPlayer dumbRobotPlayer1 = new DumbRobotPlayer("Robot1",1,board);
                    players.add(dumbRobotPlayer1);
                    //et un DumbRobotPlayer2
                    DumbRobotPlayer dumbRobotPlayer2 = new DumbRobotPlayer("Robot2",2,board);
                    players.add(dumbRobotPlayer2);
                    //et un DumbRobotPlayer2
                    DumbRobotPlayer dumbRobotPlayer3 = new DumbRobotPlayer("Robot3",3,board);
                    players.add(dumbRobotPlayer3);
                } else if (nbOfHumans == 2) {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    //et un DumbRobotPlayer1
                    DumbRobotPlayer dumbRobotPlayer1 = new DumbRobotPlayer("Robot1",2,board);
                    players.add(dumbRobotPlayer1);
                    //et un DumbRobotPlayer2
                    DumbRobotPlayer dumbRobotPlayer2 = new DumbRobotPlayer("Robot2",3,board);
                    players.add(dumbRobotPlayer2);
                    
                } else if (nbOfHumans == 3)  {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    // ajouter un HumanPlayer3
                    display.out.println("Veuillez le nom du 3è joueur: ");
                    String name3 = scanner.nextLine();
                    HumanPlayer humanPlayer3 = new HumanPlayer(name3,2,board);
                    players.add(humanPlayer3);
                    //et un DumbRobotPlayer
                    DumbRobotPlayer dumbRobotPlayer = new DumbRobotPlayer("Robot",3,board);
                    players.add(dumbRobotPlayer);
                } else {
                    // ajouter un HumanPlayer1
                    display.out.println("Veuillez le nom du 1er joueur: ");
                    String name1 = scanner.nextLine();
                    HumanPlayer humanPlayer1 = new HumanPlayer(name1,0,board);
                    players.add(humanPlayer1);
                    // ajouter un HumanPlayer2
                    display.out.println("Veuillez le nom du 2è joueur: ");
                    String name2 = scanner.nextLine();
                    HumanPlayer humanPlayer2 = new HumanPlayer(name2,1,board);
                    players.add(humanPlayer2);
                    // ajouter un HumanPlayer3
                    display.out.println("Veuillez le nom du 3è joueur: ");
                    String name3 = scanner.nextLine();
                    HumanPlayer humanPlayer3 = new HumanPlayer(name3,2,board);
                    players.add(humanPlayer3);
                    // ajouter un HumanPlayer4
                    display.out.println("Veuillez le nom du 4è joueur: ");
                    String name4 = scanner.nextLine();
                    HumanPlayer humanPlayer4 = new HumanPlayer(name4,3,board);
                    players.add(humanPlayer4);
                }
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
        while( !isGameOver() || (tour % getNbPlayers() != 0)){
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
        boolean test = false;
        Action actionChoisie =  null;
        while (!test){
            try {
            test = true;
            actionChoisie =  player.chooseAction();
            actionChoisie.process(board);
            } catch (IllegalArgumentException e){
                display.out.println(e.getMessage());
                display.out.println();
                test = false;
            }
        }
        
        display.out.println(player.getName()+" " +actionChoisie.toString());
        display.out.println("Jetons total: "+ player.getNbTokens());
        if (player.getNbTokens() > 10){
            Action discardAction = discardToken(player);
            discardAction.process(board);
            display.out.println(player.getName()+" " +discardAction.toString());
            display.out.println("Jetons total: "+ player.getNbTokens());
        }
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
        //liste des joueurs ayant + de 15 points
        ArrayList<Player> gagnants = new ArrayList<Player>();
        Player gagnant =  players.get(0);
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getPoints() >= 15){
                gagnants.add(players.get(i));
                if (players.get(i).getPoints() >  gagnant.getPoints()){
                    //joueur avec le point max
                    gagnant = players.get(i);
                }
            }
        }
        
        if(gagnants.size() == 1){
            display.out.println("Le joueur " + gagnants.get(0) + " a gagner.");
        } else{
            //joueur gagnant exeaquo en points avec le moins de cartes
            for(Player joueur : gagnants){
                if(joueur.getPoints() == gagnant.getPoints() && joueur.getNbPurchasedCards() < gagnant.getNbPurchasedCards() ){
                    gagnant = joueur;
                }
            }
            //liste des joueurs exaequos en points et en cartes
            ArrayList<Player> exaequos = new ArrayList<Player>();
            for(Player joueur : gagnants){
                if(joueur.getPoints() == gagnant.getPoints() && joueur.getNbPurchasedCards() == gagnant.getNbPurchasedCards() ){
                    exaequos.add(joueur);
                }
            }
            
            if (exaequos.size() == 1){
                display.out.println("Lejoueur " + gagnant + " a gagner.");
            } else {
                display.out.print("Exaequos pour les joueurs ");
                for (Player joueur :exaequos){
                    display.out.print(joueur +", ");
                }
            }
            
        }
    }
}
