import java.util.HashMap;

/**
 * Action de prendre 2 tokens de Resources si il y en as assez
 */
public class PickSameTokensAction extends Exception implements Action
{
    Resources ressources;

    /**
     * enlève 2 tokens du type demandé des ressources et les rajoutes au joueur
     * s'il y en as assez
     */
    public void process(Resource key)throws IllegalArgumentException{
        if(ressources.getNbResource(key) > 3){
            ressources.updateNbResource(key, -2);
            // ajouter les 2 jetons au joueurs quand la classe sera faite
        } else{
            throw new IllegalArgumentException("Vous ne pouvez prendre 2 tokens que s'il en reste au moins 4 de cette ressource");
        }
    }
    
    public String toString(){
        String str = "Le joueur " /* ajouter nom joueur */
        + "as pris 2 tokens de " /* + key (faut trouver un moyen d'afficher le type de ressource recupéré*/;
        
        return str + ".";
    }
}
