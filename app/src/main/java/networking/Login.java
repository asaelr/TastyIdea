package networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.example.asaelr.tastyidea.TastyDrawerLayout;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;
import com.mikepenz.materialdrawer.Drawer;

import java.io.IOException;

/**
 * Created by asael on 13/05/16.
 */
public class Login {
    //private final Drawer drawer;
    private GoogleApiClient gac;
    private FragmentActivity context;
    private GoogleSignInAccount account;

    public static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginC";

    public Login(FragmentActivity activity) {
        this.context = activity;
        //this.drawer = drawer;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gac = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .enableAutoManage(context,null)
                .build();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(gac);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            account = result.getSignInAccount();
        } else account = null;
    }

    public void login() {
        Log.i(TAG,"sign1");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gac);
        Log.i(TAG,"sign2");
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.i(TAG,"sign3");
    }

    private static final String SCOPE_STRING = "oauth2:https://www.googleapis.com/auth/plus.me";

    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
//        GoogleSignInStatusCodes.getStatusCodeString(result.getStatus().getStatusCode());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            account = result.getSignInAccount();
            Log.i(TAG,account.getDisplayName());
            Log.i(TAG,account.getEmail());
            Log.i(TAG,account.getId());
            Log.i(TAG,"isconnected: "+gac.isConnected());
            //gac.connect();
            new AsyncTask<Void,Void,Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        String token = GoogleAuthUtil.getToken(context, account.getEmail(), SCOPE_STRING);
                        Log.i(TAG,token);
                        Networking.client.user().logout().execute();
                        Networking.client.user().loginGoogle(token, new KinveyUserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                Log.i(TAG,"kinvey loginGoogle success");
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.i(TAG,"kinvey loginGoogle failed",throwable);

                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (GoogleAuthException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }.execute();

        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
            account = null;
        }
    }

    public void ping() {
        AsyncCustomEndpoints endpoints = Networking.client.customEndpoints(GenericJson.class);
        endpoints.callEndpoint("ping", null, new KinveyClientCallback<GenericJson>() {
            @Override
            public void onSuccess(GenericJson o) {
                Log.i(TAG,"ping success: "+o);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.i(TAG,"ping failed",throwable);

            }
        });
        Log.i(TAG, "internal data: "+getUserName());
    }

    public void logout() {
        account = null;
        Auth.GoogleSignInApi.signOut(gac).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Networking.client.user().logout().execute();
                        Networking.client.user().login(new KinveyUserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                Log.i(TAG,"anonymous login success");
                            }

                            @Override
                            public void onFailure(Throwable throwable) {
                                Log.i(TAG,"anonymous login failed",throwable);

                            }
                        });
                    }
                });

    }

    public String getUserName() {
        return account.getDisplayName();
    }

    public String getEmail() {
        return account.getEmail();
    }

    public boolean isConnected() {
        return account!=null;
    }
}
