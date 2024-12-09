import java.util.HashMap;
import java.util.ArrayList;
public abstract class Player implements Displayable {

    /* --- Stringers --- */
    private int id;
    private String name;
    private int points;
    private ArrayList<DevCard> purchasedCards;
    private Resources resources;

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
        for(Resource res: resources.keySet()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            strPlayer[3+(Resource.values().length-1-res.ordinal())] = res.toSymbol() + " ("+resources.getNbResource(res)+") ["+getResFromCards(res)+"]";
        }

        return strPlayer;
    }

    public String getName(){
        return name;
    }

    public int getPoints(){
        return points;
    }

    public int getNbTokens(){
        return resources.size();
    }

    public int getNbPurchasedCards(){
        return purchasedCards.size();
    }

    public Integer getNbResource(Resource key){
        return resources.getNbResource(key);
    }

    public HashMap getAvailableResources(){
        return resources.getAvailableResources();
    }

    public int getResFromCards(Resource type){
        int res = 0;
        for (DevCard card: purchasedCards){
            if (card.equals(type)){
                res += 1;
            }
        }
        return res;
    }

    public void updateNbResource(Resource type, Integer v){
        resources.updateNbResource(type,v);
    }

    public void updatePoints(int v){
        points += v;
    }

    public void addPurchasedCard(DevCard card){
        purchasedCards.add(card);
    }

    public boolean canBuyCard(DevCard card){
        boolean can = true;
        card.getCost().forEach((key,value)->{
                Integer nb = getNbResource(key) + getResFromCards(key);
                if (value.compareTo(nb)== 1){
                    can = false;
                }
            });
        return can; 
    }
}
