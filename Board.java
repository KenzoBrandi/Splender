import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.Collections;

public class Board implements Displayable {
    
    private ArrayList<DevCard> pile1;
    private ArrayList<DevCard> pile2;
    private ArrayList<DevCard> pile3;
    
    private ArrayList<DevCard> cards; 
    private Resources jetons;
    
    /**
    *Constructeur de la classe Board
    */
    public Board(){
        //Création des 3 piles de DevCard faces cachées
        pile1 = new ArrayList<DevCard>();
        pile2 = new ArrayList<DevCard>();
        pile3 = new ArrayList<DevCard>();

        //Rendre visible 4 cartes de chaques piles de DevCard
        cards = new ArrayList<DevCard>();
        for(int i=1;i<4;i++){
            for(int k=0; k<4; k++){
                cards.add(drawCard(i));
            }
        }
        
        
        //Initialisation du nombre de ressources
        
        
    }
    
    
    /**
    *Retourne le nombre de ressources disponibles sur le plateau
    *Output: String nombre de ressources disponible par type
    */
    
    public String getNbRessources(){
        String str = "";
        for(Resources resources : jetons){
            str += resources.getNbRessources();
        }
        
        return str;
    }
    
    
    /**
    *Initialisation du nombre de ressources pour un type donné
    *Input: String type de ressource
    */
    public void setNbResources(String type){
        
    }
    
    
    /**
    *Ajoute/Enlève une quantité donnée de ressources à un type donné
    *Input: String type de ressource; int quantité
    */
    public void updateNbResource(String type, int qte){
        
    
    }
    
    
    /**
    *Retourne les types de ressources pour lesquels des ressources sont disponibles
    *Output: String type de ressources
    */
    public String getAvailableResources(){
        
    }
    
    
    public DevCard getCard(int niveau,int colonne){
        ArrayList<DevCard> pile = new ArrayList();
        
        if (niveau == 1){
            pile = pile1;
        }else if(niveau == 2){
            pile = pile2;
        }else{
            pile = pile3;
        }
        
        
        return pile.get(colonne);
    }
    
    
    public void updateCard(DevCard card){
        //Tire une carte dans la pioche du même niveau que card
        drawCard(card.tier);
    }
    
    public DevCard drawCard(int niveau){
        ArrayList<DevCard> pile = new ArrayList();
        
        if (niveau == 1){
            pile = pile1;
        }else if(niveau == 2){
            pile = pile2;
        }else{
            pile = pile3;
        }
        
        
        if (pile.size() == 0){
            return null;
        }else{
            return pile.get(0);
        }
    }
    
    
    public boolean canGivesameTokens(String type){
        return jetons.getNbRessource(type) >= 4;
    }
    
    
    public boolean canGiveDiffTokens(){
        return true;
    }
    
    
    
    
    /* --- Stringers --- */

    private String[] deckToStringArray(int tier){
        /** EXAMPLE
         * ┌────────┐
         * │        │╲ 
         * │ reste: │ │
         * │   16   │ │
         * │ cartes │ │
         * │ tier 3 │ │
         * │        │ │
         * └────────┘ │
         *  ╲________╲│
         */
        ArrayList<DevCard> pile = new ArrayList();
        if (tier == 1){
            pile = pile1;
        }else if(tier == 2){
            pile = pile2;
        }else{
            pile = pile3;
        }
        
        int nbCards = pile.size();
        String[] deckStr = {"\u250C\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510  ",
                            "\u2502        \u2502\u2572 ",
                            "\u2502 reste: \u2502 \u2502",
                            "\u2502   "+String.format("%02d", nbCards)+"   \u2502 \u2502",
                            "\u2502 carte"+(nbCards>1 ? "s" : " ")+" \u2502 \u2502",
                            "\u2502 tier "+tier+" \u2502 \u2502",
                            "\u2502        \u2502 \u2502",
                            "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518 \u2502",
                            " \u2572________\u2572\u2502"};
        return deckStr;
    }

    private String[] resourcesToStringArray(){
        /** EXAMPLE
         * Resources disponibles : 4♥R 4♣E 4♠S 4♦D 4●O
         */
        String[] resStr = {"Resources disponibles : "};
        
        for(Resources res : Resource){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resStr[0] += resources.getNbResource(res)+res.toSymbol()+" ";
        }

        resStr[0] += "        ";
        return resStr;
    }

    private String[] boardToStringArray(){
        String[] res = Display.emptyStringArray(0, 0);

        //Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for(int i=stackCards.size();i>0;i--){
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        //Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for(int i=1;i<=3;i++){ //-- parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for(DevCard card : cards){ //-- parcourir les 4 cartes faces visibles pour un niveau donné (j)
                tierCardsDisplay = Display.concatStringArray(tierCardsDisplay, visibleCards[i][j]!=null ? visibleCards[i][j].toStringArray() : DevCard.noCardStringArray(), false);
            }
            cardDisplay = Display.concatStringArray(cardDisplay, Display.emptyStringArray(1, 40), true);
            cardDisplay = Display.concatStringArray(cardDisplay, tierCardsDisplay, true);
        }
        
        res = Display.concatStringArray(deckDisplay, cardDisplay, false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 52), true);
        res = Display.concatStringArray(res, resourcesToStringArray(), true);
        res = Display.concatStringArray(res, Display.emptyStringArray(35, 1, " \u250A"), false);
        res = Display.concatStringArray(res, Display.emptyStringArray(1, 54, "\u2509"), true);
        
        return res;
    }

    @Override
    public String[] toStringArray() {
        return boardToStringArray();
    }
    

}
