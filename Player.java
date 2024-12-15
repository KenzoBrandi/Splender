 

import java.util.ArrayList;
import java.util.HashMap;
public abstract class Player implements Displayable {
    private int id;
    private String name;
    private int points;
    private ArrayList<DevCard> purchasedCards;
    private Resources resources;
    private Board board;
    
    /**
     * Constructeur
     */
    public Player(String name, int id,Board board){
        this.name = name;
        this.id = id;
        this.board = board;
        points = 0;
        purchasedCards = new ArrayList<DevCard>();
        resources = new Resources();
    }
    
    /**
     * Getter pour board
     */
    public Board getBoard(){
        return board;
    }
    
    /**
     * Getter pour Name
     */
    public String getName(){
        return name;
    }
    
    /**
     * Getter pour Points
     */
    public int getPoints(){
        return points;
    }
    
    /**
     * Getter pour les tokens
     */
    public Resources getTokens(){
        return resources;
    }
    
    public int getNbTokens(){
        int res = 0;
        for (Integer value: resources.values()){
            res += value;
        }
        return res;
    }
    
    /**
     * Renvoi le nombre de cartes achetées
     */
    public int getNbPurchasedCards(){
        return purchasedCards.size();
    }
    
    /**
     * Renvoi le nombre de ressources en jetons pour un type donnée
     */
    public int getNbResource(Resource res){
        return resources.getNbResource(res);
    }
    
    /**
     * Renvoi les ressources obtenues avec les cartes
     */
    public int getResFromCards(Resource res){
        int result = 0;
        for(int i = 0; i < purchasedCards.size(); i++){
            if(purchasedCards.get(i).getRessourceType() == res){
                result += 1;
            }
        }
        return result;
    }
    
    /**
     * Renvoi un HashMap des ressources disponibles
     */
        public HashMap<Resource, Integer> getAvailableResources(){
        return resources.getAvailableResources();
    }
    
    /**
     * Met à jour la ressource du type donné avec la valeur v
     */
    public void updateNbResource(Resource key, Integer v){
        resources.updateNbResource(key,v);
    }
    
    /**
     * Ajoute les points au joueur
     */
    public void updatePoints(int p){
        points += p;
    }
    
    /**
     * Ajoute une carte achetée à la liste de cartes du joueur
     */
    public void addPurchasedCard(DevCard card){
        purchasedCards.add(card);
    }
    
    /**
     * Vérifie si le joueur à assez de ressources pour acheter une carte donnée
     */
    public boolean canBuyCard(DevCard card){ 
        boolean result = false;
        Resources cout = card.getCost();
        
        for(Resource key : cout.keySet()){
            if(getNbResource(key) + getResFromCards(key) >= cout.get(key)){
                result = true;
            } else{
                return false;
            }
        }
        
        return result;
    }
    
    /**
     * Permet de choisir une action de l'interface Action
     */
    public abstract Action chooseAction() throws IntNotValidException;
    
    /**
     * Permet de choisir quels jetons remettre sur le plateau
     */
    public abstract HashMap<Resource,Integer> chooseDiscardingTokens();
    
    public String toString(){
        String str = name;
        return str;
    }
    
    /* --- Stringers --- */
   
     
    public String[] toStringArray(){
        /** EXAMPLE. The number of resource tokens is shown in brackets (), and the number of cards purchased from that resource in square brackets [].
         * Player 1: Camille
         * ⓪pts
         * 
         * ♥R (0) [0]
         * ●O (0) [0]
         * ♣E (0) [0]
         * ♠S (0) [0]
         * ♦D (0) [0]
         */
        String pointStr = " ";
        String[] strPlayer = new String[8];
         
        if(points>0){
            pointStr = new String(new int[] {getPoints()+9311}, 0, 1);
        }else{
            pointStr = "\u24EA";
        }

        
        strPlayer[0] = "Player "+(id+1)+": "+name;
        strPlayer[1] = pointStr + "pts";
        strPlayer[2] = "";
        for(Resource res : resources.keySet()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            strPlayer[3+(Resource.values().length-1-res.ordinal())] = res.toSymbol() + " ("+resources.getNbResource(res)+") ["+getResFromCards(res)+"]";
        }

        return strPlayer;
    }
}
