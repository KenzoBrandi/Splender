
/**
 * Décrivez votre classe BuyCardAction ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class BuyCardAction implements Action
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private DevCard card;

    /**
     * Constructeur d'objets de classe BuyCardAction
     */
    public BuyCardAction(DevCard card)
    {
        this.card = card;
    }

    public void process(Board board){
        //remplacer la carte du plateau par une carte de meme niveau
        board.updateCard(card);
    }
    
    public String toString(){
        return "a acheté la carte "+card.toString();
    }
}
