import java.util.ArrayList;
import java.util.HashMap;
public abstract class Player implements Displayable {
    private int id;
    private String name;
    private int points;
    private ArrayList<DevCard> purchasedCards;
    private Resources resources;
    
    public Player(String name, int id){
        this.name = name;
        this.id = id;
        points = 0;
        purchasedCards = new ArrayList<DevCard>();
        resources = new Resources();
    }
    
    public String getName(){
        return name;
    }
    
    public int getPoints(){
        return points;
    }
    
    public Resources getNbTokens(){
        return resources;
    }
    
    public ArrayList<DevCard> getNbPurchasedCards(){
        return purchasedCards;
    }
    
    public int getNbResource(Resource res){
        return resources.getNbResource(res);
    }
    
    public int getResFromCards(Resource res){
        int result = 0;
        for(int i = 0; i < purchasedCards.size(); i++){
            if(purchasedCards.get(i).getRessourceType() == res){
                result += 1;
            }
        }
        return result;
    }
    
        public HashMap getAvailableResources(){
        return resources.getAvailableResources();
    }
    
    public void updateNbResource(Resource key, Integer v){
        resources.updateNbResource(key, v);
    }
    
    public void updatePoints(int p){
        points += p;
    }
    
    public void addPurchasedCard(DevCard card){
        purchasedCards.add(card);
    }
    
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
    
    public abstract void chooseAction();
    public abstract void chooseDiscardingTokens();
    
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
