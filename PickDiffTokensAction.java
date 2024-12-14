 

import java.util.HashMap;
import java.util.ArrayList;
/**
 * Action de prendre 3 tokens de Resources différentes
 */
public class PickDiffTokensAction extends Exception implements Action
{
    private ArrayList<Resource> resources;
    public PickDiffTokensAction(Resource r1, Resource r2, Resource r3){
        resources = new ArrayList<Resource>();
        resources.add(r1);
        resources.add(r2);
        resources.add(r3);
    }

    /**
     * 
     */
    public void process(Board board)throws IllegalArgumentException{
        for(Resource res : resources){
            if(!board.canGiveDiffTokens(res)){
                throw new IllegalArgumentException("Vous ne pouvez pas prendre ces ressources");
            }else{
                board.updateNbResource(res,-1);
            }
        }
    }
    
    public String toString(){
        String str = "a pris 3 jetons dont les types sont: ";
        for(Resource res: resources){
            str += res.toString();
        }
        return str;
    }
}
