 

import java.util.Set;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;

/**
 * Un joueur robot stupide 
 */
public class DumbRobotPlayer extends Player
{
    
    /**
     * COnstructeur
     */
    DumbRobotPlayer(String name, int id, Board board){
        super(name, id, board);
    }
    
    /**
     * Renvoi l'action choisie avec les paramètres selon le choix
     */
    public Action chooseAction() throws IntNotValidException{
        int j = 0;
        DevCard card = null;
        //Achat d'une carte
        for(int i=0;i<=2;i++){
            for(int k=0; k<4; k++){
                card = getBoard().getVisibleCards()[i][k];
                if(canBuyCard(card)){
                    //mis à jour des ressources
                    card.getCost().forEach((key,value)->{
                        int jetonsAEnlever = -value + getResFromCards(key);
                        if(jetonsAEnlever < 0){
                            //enlever les jetons dépensés du player après l'achat
                            updateNbResource(key,jetonsAEnlever); 
                            //remettre les jetons dépensés sur le board après l'achat
                            getBoard().updateNbResource(key,-jetonsAEnlever);
                        }
                    });
                    Action act = new BuyCardAction(card);
                    addPurchasedCard(card);
                    
                    updatePoints(card.getPoints());
                    return act;
                }
            }
        }
        
        
        //Prendre 2 ressources de même type
        for(Resource key : getTokens().keySet()){
            if(super.getBoard().canGiveSameTokens(key)){
                Action act = new PickSameTokensAction(key);
                updateNbResource(key,2);
                return act;
            }
        }
        
        
        
        //Prendre 3 jetons de types différents
        ArrayList<Resource> boardResources = new ArrayList<>(getBoard().getAvailableResources().keySet());
        for (Resource res: getBoard().getAvailableResources().keySet()){
            boardResources.add(res);
        }
        
        if (boardResources.size()>=3) {
            Random random = new Random();
            int i1 = random.nextInt(boardResources.size());
            int i2 = random.nextInt(boardResources.size());
            while(boardResources.get(i1) == boardResources.get(i2)){
                i2 = random.nextInt(boardResources.size());
            }
            int i3 = random.nextInt(boardResources.size());
            while(boardResources.get(i1) == boardResources.get(i3) || boardResources.get(i2) == boardResources.get(i3) ){
                i3 = random.nextInt(boardResources.size());
            }
            Action act = new PickDiffTokensAction(boardResources.get(i1),boardResources.get(i2),boardResources.get(i3));
            updateNbResource(boardResources.get(i1),1);
            updateNbResource(boardResources.get(i2),1);
            updateNbResource(boardResources.get(i3),1);
            return act;
        }

            
        

        Action act = new PassAction();
        act.process(super.getBoard());
        return act;
    }
    
    
    
    
    /**
     * Renvoi une HashMap des jetons choisie à jeter
     */
    public HashMap<Resource,Integer> chooseDiscardingTokens(){
        HashMap<Resource,Integer> jeter = new HashMap<Resource,Integer>();
        int surplus = getNbTokens()- 10;
        List<Resource>  keys = new ArrayList<>(getAvailableResources().keySet());        
        Random random = new Random();
        while(surplus  >0){
            Resource randKey = keys.get(random.nextInt(keys.size()));
            if( getNbResource(randKey) > 0){
                jeter.put(randKey,jeter.getOrDefault(randKey,0) +1);
                updateNbResource(randKey,-1);
                surplus  --;
            }
            
        }

        return jeter;
    }
}
