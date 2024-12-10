
/**
 * pour gérer les différentes actions possibles des joueurs
 */

public interface Action
{
    /**
     * 
     */
    public void process(Board b);
    
    
    public String toString();
    
}
