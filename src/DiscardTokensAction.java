 


import java.util.HashMap;
public class DiscardTokensAction implements Action
{
    private HashMap<Resource,Integer> jetons;
    /**
     * Constructeur d'objets de classe DiscardTokensAction
     */
    public DiscardTokensAction(HashMap<Resource,Integer> jetons)
    {
        this.jetons = jetons;
    }

    public void process(Board board){
        jetons.forEach((res,v)->{
            board.getResources().updateNbResource(res,v);
        });
        
    }
    
    public String toString(){
        StringBuilder res = new StringBuilder(" a défaussé ");
        jetons.forEach((key, value) -> {
            res.append(value).append(" ").append(key).append(" ");
        });
        return res.toString();
    }
}
