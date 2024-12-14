 

import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Ressources avec une table de hachage
 */
public class Resources extends HashMap<Resource, Integer>
{
    
    /**
     * Constructeur de Ressources
     */
    public Resources()
    {
       super(); 
       for ( Resource res: Resource.values()){
           put(res,0);
       }
    }
    /**
     * Renvoi le nombre de ressources du type indiqué
     */
    public Integer getNbResource(Resource key){
        if (containsKey(key)){        
            return get(key);
        }
        return 0;
    }
    
    /**
     * Setter de la classe
     */
    public void setNbResource(Resource key, Integer newNbResource){
        put(key, newNbResource);        
    }
    
    /**
     * Permet d'ajouter/de retirer le nombres v de ressources à un type donné
     */
    public void updateNbResource(Resource key, Integer v){
        if(getNbResource(key) + v >= 0){
            Integer newNbResource = getNbResource(key) + v;
            setNbResource(key, newNbResource);
        }
    }
    
    /**
     * Renvoi une autre HashMap avec les types de ressources disponibles et
     * le nombre de ressources
     */
    public HashMap<Resource, Integer> getAvailableResources(){
        HashMap available = new HashMap();
        this.forEach((key, value) ->{
           if (get(key) != 0){
               available.put(key,value);
           }
        });
        
        return available;
    }
    

}
