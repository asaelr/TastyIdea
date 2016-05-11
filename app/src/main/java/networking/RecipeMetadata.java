package networking;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

/**
 * Created by asael on 11/05/16.
 */
public class RecipeMetadata extends GenericJson {
    @Key("_id")
    public String id;
    @Key
    public String name;
    @Key
    public String category;
    @Key
    public int prepTimeMinutes;
    @Key
    public int rate;
    @Key
    public int difficulty;

}
