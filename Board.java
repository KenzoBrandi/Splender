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
    
    //Liste de toutes les cartes faces cachées, triées par niveau
    private ArrayList<Stack> stackCards;
    //Tableau de toutes les cartes faces visibles
    private DevCard[][] visibleCards ;
    //Jetons ressources
    private Resources resources;

    
    /**
     *Constructeur de la classe Board
     */
    public Board(int nbPlayers){
        //Création des 3 piles de DevCard faces cachées
        Stack<DevCard> pile1 = new Stack<DevCard>();
        Stack<DevCard> pile2 = new Stack<DevCard>();
        Stack<DevCard> pile3 = new Stack<DevCard>();
        
        //Liste de DevCards éphémère
        ArrayList<DevCard> stack = new ArrayList<DevCard>(); 
        visibleCards = new DevCard[3][4];
        
        try {
            // Lecture du fichier csv
            File file = new File("stats.csv");
            Scanner scanner = new Scanner(file);

            // On ne lit pas les 9 premières lignes du fichier (cartes NOBLE)
 
            for (int i = 0; i<9;i++){
                scanner.nextLine();
            }
            
            //Création de toutes les DevCard
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                
                //Récupération des éléments pour créer les DevCards
                Resource[] res ={ Resource.DIAMOND,Resource.SAPPHIRE,Resource.EMERALD,Resource.RUBY,Resource.ONYX};
                int niveau = Integer.parseInt(values[0]);
                Resources resources = new Resources();
                
                for (int i = 1;i<6;i++){
                    resources.setNbResource(res[i-1], Integer.valueOf(values[i]));
                }
                int points = Integer.parseInt(values[6]);
                Resource resourceType = Resource.valueOf(values[7]);
                
                //Appel au constructeur de la classe DevCard
                DevCard newCard = new DevCard(niveau,resources,points,resourceType);
                
                //Ajout de la carte à la liste éphémère
                stack.add(newCard);
            }

            scanner.close(); // Fermer le scanner
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable : " + e.getMessage());
        }
    
        //Mélanger les cartes dans la liste pour qu'elles soient rangées aléatoirement
        Collections.shuffle(stack);
        
        //Triage des cartes par niveau (les cartes restent rangées aléatoirement)
        for (DevCard card: stack){
            int niveau = card.getLevel();
            if (niveau == 1){
                pile1.push(card);
            } else if (niveau == 2){
                pile2.push(card);
            } else if (niveau == 3){
                pile3.push(card);
            }
        }
        
        //Instantiation de l'attribut stackCards (liste de toutes les cartes triées par niveau)
        stackCards = new ArrayList<Stack>();
        stackCards.add(pile1);
        stackCards.add(pile2);
        stackCards.add(pile3);
        
        
        //Rendre visible 4 cartes de chaques piles de DevCard
        visibleCards = new DevCard[3][4];
        for(int i=0;i<3;i++){
            for(int k=0; k<4; k++){
                visibleCards[i][k]= drawCard(i);
            }
        } 

        //Initialisation du nombre de ressources
        resources = new Resources();
        if (nbPlayers == 2){
            resources.forEach((key, value) ->{
                        resources.put(key,new Integer(4));
                });
        } else if (nbPlayers == 3){
            resources.forEach((key, value) ->{
                        resources.put(key,new Integer(5));
                });
        }

    }

    /**
     *Retourne le nombre de ressources disponibles sur le plateau en fonction du type de ressource entré en oaramètre
     *Input: Resource type de ressource
     *Output: Integer nombre de ressources disponible
     */

    public Integer getNbResources(Resource type){
        return resources.getNbResource(type);
    }

    /**
     *Initialisation du nombre de ressources pour un type donné
     *Input: String type de ressource int nombre de ressources
     */
    public void setNbResources(Resource type,int nb){
        resources.setNbResource(type,nb);
    }

    /**
     *Ajoute/Enlève une quantité donnée de ressources à un type donné
     *Input: String type de ressource; int quantité
     */
    public void updateNbResource(Resource type, int qte){
        resources.updateNbResource(type,qte);
    }
    
    /**
     *Retourne les types de ressources pour lesquels des ressources sont disponibles
     *Output: HashMap type de ressources
     */
    public HashMap getAvailableResources(){
        return resources.getAvailableResources();
    }

    /**
     * Retourne une carte en fonction de sa position
     * Input: int ligne(niveau); int colonne
     * Output: DevCard
     */
    public DevCard getCard(int niveau,int colonne){

        Stack<DevCard> pile = stackCards.get(niveau);

        return pile.get(colonne);
    }

    /**
     * Tire une carte de même niveau de celle entrée en paramètre
     * Input:DevCard carte à remplacer
     */
    public void updateCard(DevCard card){
        drawCard(card.getLevel());
    }
    
    
    /**
     * Retourne la 1ere carte de la pile du niveau entré en paramètre
     * Input: int niveau
     */
    public DevCard drawCard(int niveau){

        Stack<DevCard> pile = stackCards.get(niveau);

        if (pile.size() == 0){
            return null;
        }else{
            return pile.pop();
        }
    }
    
    
    /**
     * Retourne s'il est possible de prendre deux jetons de même type pour un type donné
     * Input: Resource type du jeton
     * Output: Boolean
     */
    public boolean canGivesameTokens(Resource type){
        return resources.getNbResource(type) >= 4;
    }
    
    /**
     * Retourne s'il est possible de prendre deux jetons de même type pour un type donné
     * Input: Resource type du jeton
     * Output: Boolean
     */
    public boolean canGiveDiffTokens(Resource type){
        return resources.getNbResource(type) > 0;
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

        
        Stack<DevCard> pile = stackCards.get(tier-1);

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

        for(Resource res: resources.keySet()){ //-- parcourir l'ensemble des resources (res) en utilisant l'énumération Resource
            resStr[0] += resources.getNbResource(res)+res.toSymbol()+" ";
        }

        resStr[0] += "        ";
        return resStr;
    }

    private String[] boardToStringArray(){
        String[] res = Display.emptyStringArray(0, 0);

        //Deck display
        String[] deckDisplay = Display.emptyStringArray(0, 0);
        for(int i= stackCards.size();i>0;i--){
            deckDisplay = Display.concatStringArray(deckDisplay, deckToStringArray(i), true);
        }

        //Card display
        String[] cardDisplay = Display.emptyStringArray(0, 0);
        for(int i=0;i< 3;i++){ //-- parcourir les différents niveaux de carte (i)
            String[] tierCardsDisplay = Display.emptyStringArray(8, 0);
            for(int j = 0;j<4;j++){ //-- parcourir les 4 cartes faces visibles pour un niveau donné (j)
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
