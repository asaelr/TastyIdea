package networking;

/**
 * Created by asael on 08/05/16.
 */

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

import android.content.Context;
import android.util.Log;

public class Networking {
    private static Client client;
    public static void init(Context context) {
        client = new Client.Builder("kid_-J4_SDpzz-", "ffecf232dfac4c1eb47a96a671045f12"
                , context).build();
    }

    public static void ping() {
        client.ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e("TastyIdea", "Kinvey Ping Failed", t);
            }

            public void onSuccess(Boolean b) {
                Log.d("TastyIdea", "Kinvey Ping Success");
            }
        });
    }

    public static void login() {
        if (client.user().isUserLoggedIn()) {
            Log.e("TastyIdea", "Already logged in");
            return;
        }
        client.user().login(new KinveyUserCallback() {
            @Override
            public void onFailure(Throwable error) {
                Log.e("TastyIdea", "Login Failure", error);
            }

            @Override
            public void onSuccess(User result) {
                Log.i("TastyIdea", "Logged in a new implicit user with id: " + result.getId());
            }
        });
    }

    public static void get1() {
        AsyncCustomEndpoints endpoints = client.customEndpoints(RecipeMetadata[].class);
        endpoints.callEndpoint("get1", new GenericJson(), new KinveyClientCallback<RecipeMetadata[]>() {
            @Override
            public void onSuccess(RecipeMetadata[] result) {
                Log.e("TastyIdea", "Kinvey get1 Success : " + result);
                for (RecipeMetadata rec : result) {
                    Log.i("TastyIdea","id: "+rec.id+", name: "+rec.name);
                }
                Log.i("TastyIdea","detailed recipes:");
                for (RecipeMetadata rec : result) {
                    get2(rec.id);
                }
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TastyIdea", "Kinvey get1 Failed", error);
            }
        });
    }

    public static void get2(String id) {
        GenericJson input = new GenericJson();
        input.put("id",id);
        AsyncCustomEndpoints endpoints = client.customEndpoints(RecipeData.class);
        endpoints.callEndpoint("get2", input, new KinveyClientCallback<RecipeData>() {
            @Override
            public void onSuccess(RecipeData result) {
                Log.i("TastyIdea", "Kinvey get2 Success : " + result.name);
                Log.i("TastyIdea", "vegan: "+result.vegan);
                Log.i("TastyIdea", "ingredients:");
                for (String ing : result.ingredients) {
                    Log.i("TastyIdea",ing);
                }
                Log.i("TastyIdea", "method:");
                for (String step : result.method) {
                    Log.i("TastyIdea",step);
                }

            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TastyIdea", "Kinvey get2 Failed", error);
            }
        });
    }
}

class RecipeMetadata extends GenericJson {
    @Key("_id")
    public String id;
    @Key
    public String name;

}

class RecipeData extends GenericJson {
    @Key
    Boolean vegan;
    @Key
    String name;
    @Key
    String[] ingredients;
    @Key
    String[] method;
}