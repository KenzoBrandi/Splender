import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * Un joueur robot stupide 
 */
public class DumbRobotPlayer extends Player
{
    private Random random = new Random();
    private Board board;
    private Resources modele;
    DumbRobotPlayer(String name, int id){
        super(name, id);
    }
    
    public Action chooseAction(Action a){
        Set<Resource> keys = modele.keySet();
        Resource[] temp = new Resource[3];
        int j = 0;
        for(int i=0;i<3;i++){
            for(int k=0; k<4; k++){
                if(this.canBuyCard(board.getCard(i, k))){
                    Action act = new BuyCardAction(board.getCard(i, k));
                    act.process(board);
                    return act;
                }
            }
        }
        
        for(Resource key : keys){
            if(board.canGiveSameTokens(key)){
                Action act = new PickSameTokensAction(key);
                act.process(board);
                return act;
            }
        }

        for(Resource key : keys){
            if(board.canGiveDiffTokens(key)){
                temp[j] = key;
                if(temp[0] instanceof Resource && temp[1] instanceof Resource && temp[2] instanceof Resource){
                    Action act = new PickDiffTokensAction(temp[0], temp[1], temp[2]);
                    act.process(board);
                    return act;
                }
            }
        }
        
        Action act = new PassAction();
        act.process(board);
        return act;
    }
    
    public Resources chooseDiscardingTokens(){
        List<Resource>  keys = new ArrayList<>(super.getNbTokens().keySet());        
        Resource randKey = keys.get(random.nextInt(keys.size()));
        Action discard = new DiscardTokensAction(super.getNbTokens(), randKey);
        modele.put(randKey, 1);
        discard.process(board);
        
        return modele;
    }
}
