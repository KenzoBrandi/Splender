 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;
public class HumanPlayer extends Player
{
    
    public HumanPlayer(String name, int id,Board board)
    {
        super(name,id,board);
    }

    public Action chooseAction() throws IntNotValidException{
        //choix possibles
        Game.display.out.println("Veuillez choisir le numéro correspondant à l\'action choisie: ");
        Game.display.out.println("1)Prendre 2 jetons du même type");
        Game.display.out.println("2)Prendre 3 jetons différents");
        Game.display.out.println("3)Acheter une carte");
        Game.display.out.println("4)Passer mon tour");
        //entrées utilisateur
        Scanner scanner = new Scanner(Game.display.in);
        int action = 0;
        
        //gestion des erreurs d'entrée de l'utilisateur
        boolean test = false;
        while (! test){
            try {
                test = true;
                action = scanner.nextInt();
                if (action <1 ||action >4){
                    throw new IntNotValidException();
                }
            }   catch (InputMismatchException e1){
                    Game.display.out.println("Veuillez entrer un entier!");
                    scanner.nextLine();
                    test = false;
            }   catch (IntNotValidException e2){
                    Game.display.out.println(e2.getMessage());
                    scanner.nextLine();
                    test = false;
            }    
        }
        
        scanner.nextLine();
        //liste des resources disponibles sur le board
        ArrayList<Resource> boardResources = new ArrayList<Resource>();
        for (Resource res: getBoard().getAvailableResources().keySet()){
            boardResources.add(res);
        }
        //la variable contenant le type d'action choisie
        Action actionChoisie = null;
        //gestion des erreurs d'entrée de l'utilisateur
        test = false;
        while (! test){
            try {
                test = true;
                switch(action){
                    case 1:
                        Game.display.out.print("Veuillez entrer le type de jeton à acquérir deux fois");
                        Game.display.out.println("(DIAMOND, SAPPHIRE,EMERALD,ONYX,RUBY):");
                        String jeton = scanner.nextLine().toUpperCase();
                        Resource resource = Resource.valueOf(jeton);
                        actionChoisie = new PickSameTokensAction(resource);
                        updateNbResource(resource,2);

                        break;
                    case 2:
                        String r1 = "";
                        String r2 = "";
                        String r3 = "";
                        Game.display.out.println("(DIAMOND, SAPPHIRE,EMERALD,ONYX,RUBY)");
                        Game.display.out.println("Veuillez entrer le 1er jeton à acquérir ");
                        r1 = scanner.nextLine().toUpperCase();
                        Game.display.out.println("Veuillez entrer le 2e jeton à acquérir ");
                        r2 = scanner.nextLine().toUpperCase();
                        while (r1.equals(r2)){
                            Game.display.out.println("Veuillez entrer un jeton différent du 1er et disponible! ");
                            r2 = scanner.nextLine().toUpperCase();
                        }
                        Game.display.out.println("Veuillez entrer le 3e jeton à acquérir ");
                        r3 = scanner.nextLine().toUpperCase();
                        while (r1.equals(r3) || r2.equals(3)){
                            Game.display.out.println("Veuillez entrer un jeton différent des deux 1ers et disponible! ");
                            r3 = scanner.nextLine().toUpperCase();
                        }
                        actionChoisie = new PickDiffTokensAction(Resource.valueOf(r1),Resource.valueOf(r2),Resource.valueOf(r3));
                        updateNbResource(Resource.valueOf(r1),1);
                        updateNbResource(Resource.valueOf(r2),1);
                        updateNbResource(Resource.valueOf(r3),1);
                        break;
                        
                    case 3:
                        Game.display.out.println("Veuillez entrer le niveau de la carte à acheter: ");
                        int i = scanner.nextInt();
                        Game.display.out.println("Veuillez entrer la colonne: ");
                        int j = scanner.nextInt();
                        DevCard card = getBoard().getVisibleCards()[3-i][j-1];
                        if (canBuyCard(card)){
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
                            addPurchasedCard(card);
                            updatePoints(card.getPoints());
                            actionChoisie = new BuyCardAction(card); 
                        } else {
                            Game.display.out.println("Ressources insuffisantes pour acheter cette carte, vous passez votre tour");
                            actionChoisie = new PassAction();
                        }
                        
                        break;
                        
                    case 4:
                        actionChoisie = new PassAction();
                        break;
             
                }
            }   catch (IllegalArgumentException e1){
                Game.display.out.println(e1.getMessage());
                test = false;
                
            }   catch (InputMismatchException e2){
                Game.display.out.println(e2.getMessage());
                test = false;
            } catch (IndexOutOfBoundsException e3){
                Game.display.out.println(e3.getMessage());
                test = false;
            }
            
        }
        
        return actionChoisie;
    };
    
    public HashMap<Resource,Integer> chooseDiscardingTokens(){
        HashMap<Resource,Integer> jeter = new HashMap<Resource,Integer>();
        int surplus = getNbTokens()- 10;
        Scanner scanner = new Scanner(Game.display.in);
        String jeton = "";
        int reste = 0;
        while (surplus > 0){
            try {
                Game.display.out.print("Veuillez entrer un jeton à enlever parmi: ");
                getAvailableResources().forEach((res,val)->{
                    Game.display.out.print(val+ " "+res + "; ");
                }); 
                jeton= scanner.nextLine().toUpperCase();
                //reste de jetons d'un type après retrait
                reste = getAvailableResources().getOrDefault(Resource.valueOf(jeton),0);
                //Pour éviter le cas où on retire plus de jetons d'un type donné que ce qu'on a 
                if(reste == 0){
                    Game.display.out.println("Veuillez entrer un jeton que vous avez en stock! ");
                } else {
                    jeter.put(Resource.valueOf(jeton),jeter.getOrDefault(Resource.valueOf(jeton),0)+1);
                    updateNbResource(Resource.valueOf(jeton),-1);
                    surplus --;
                }
            } catch (IllegalArgumentException e){
                Game.display.out.println(e.getMessage());
            }
            
        }
        
        return jeter;
    };
}
