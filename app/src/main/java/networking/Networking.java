package networking;

/**
 * Created by asael on 08/05/16.
 */

import com.example.asaelr.tastyidea.Recipe;
import com.example.asaelr.tastyidea.RecipesSearcher;
import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class Networking {
    static Client client;
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
                AsyncTask<RecipeMetadata,Recipe,Void> ast = new AsyncTask<RecipeMetadata, Recipe, Void>() {
                    @Override
                    protected Void doInBackground(RecipeMetadata... params) {
                        for (RecipeMetadata rmd : params) {
                            try {
                                Recipe recipe = getRecipe(rmd.id);
                                publishProgress(recipe);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                    @Override
                    protected void onProgressUpdate(Recipe... recipes) {
                        Log.i("TastyIdea",recipes[0].toString());
                    }

                };
                ast.execute(result);
            }

            @Override
            public void onFailure(Throwable error) {
                Log.e("TastyIdea", "Kinvey get1 Failed", error);
            }
        });
    }

    //Don't call this function from UI thread!!!
    public static RecipeMetadata[] getAllRecipesMetadata() throws IOException {
        AsyncCustomEndpoints<GenericJson,RecipeMetadata[]> endpoints = client.customEndpoints(RecipeMetadata[].class);
        return endpoints.callEndpointBlocking("getAllRecipesMetadata",null).execute();
    }

    //Don't call this function from UI thread!!!
    public static Recipe getRecipe(String id) throws IOException {
        GenericJson input = new GenericJson();
        input.put("id",id);
        AsyncCustomEndpoints<GenericJson,RecipeData> endpoints = client.customEndpoints(RecipeData.class);
        RecipeData result = endpoints.callEndpointBlocking("getRecipe",input).execute();
        return new Recipe(result);
    }

    //Don't call this function from UI thread!!!
    public static RecipeMetadata[] searchRecipes(RecipesSearcher.SearchJSON search) throws IOException {
        AsyncCustomEndpoints<RecipesSearcher.SearchJSON,RecipeMetadata[]> endpoints = client.customEndpoints(RecipeMetadata[].class);
        return endpoints.callEndpointBlocking("searchRecipes",search).execute();
    }
}

;
