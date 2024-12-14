 

import java.util.HashMap;

/**
 * Action de prendre 2 tokens de Resources si il y en as assez
 */
public class PickSameTokensAction extends Exception implements Action
{
    private Resource resource;
    public PickSameTokensAction(Resource r){
        resource = r;
    }
    
    /**
     * enlève 2 tokens du type demandé des ressources et les rajoutes au joueur
     * s'il y en as assez
     */
    public void process(Board board)throws IllegalArgumentException{
        if(board.canGiveSameTokens(resource)){
            board.updateNbResource(resource,-2);
            // ajouter les 2 jetons au joueurs quand la classe sera faite
        } else{
            board.canGiveSameTokens(resource);
            throw new IllegalArgumentException("Vous ne pouvez prendre 2 tokens que s'il en reste au moins 4 de cette ressource");
        }
    }
    
    public String toString(){
        return "a pris deux jetons de ressource de type "+resource.toString();
    }
}
