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
       for(Resource res: Resource.values()){
           put(res,0);
       }
    }
    
    public Integer getNbResource(Resource key){
        if (containsKey(key)){        
            return get(key);
        }
        return 0;
    }
    
    public void setNbResource(Resource key, Integer newNbResource){
        put(key, newNbResource);        
    }
    
    public void updateNbResource(Resource key, Integer v){
        if(getNbResource(key) + v >= 0){
            Integer newNbResource = getNbResource(key) + v;
            setNbResource(key, newNbResource);
        }
    }
    
    public HashMap getAvailableResources(){
        HashMap available = new HashMap();
        this.forEach((key, value) ->{
           if (get(key) != 0){
               available.put(key,value);
           }
        });
        
        return available;
    }
    
}
