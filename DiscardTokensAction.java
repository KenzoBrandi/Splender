
/**
 * Décrivez votre classe DiscardTokensAction ici.
 *
 * @author (votre nom)
 * @version (un numéro de version ou une date)
 */
public class DiscardTokensAction implements Action
{
    // variables d'instance - remplacez l'exemple qui suit par le vôtre
    private Resources resourcesPlayer;
    private Resource jeton;
    /**
     * Constructeur d'objets de classe DiscardTokensAction
     */
    public DiscardTokensAction(Resources resP,Resource jeton)
    {
        resourcesPlayer = resP;
        this.jeton = jeton;
    }

    public void process(Board board){
        board.getResources().updateNbResource(jeton,1);
        resourcesPlayer.updateNbResource(jeton,-1);
    }
    
    public String toString(){
        return "a défaussé des jetons.";
    }
}
