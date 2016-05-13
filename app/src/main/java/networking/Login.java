package networking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.api.client.json.GenericJson;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;
import com.kinvey.java.core.KinveyClientCallback;

import java.io.IOException;

/**
 * Created by asael on 13/05/16.
 */
public class Login {
    private static String userName;
    private static GoogleApiClient gac;
    private static Context context;

    public static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginC";

    public static void init(Context context) {
        Login.context = context;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gac = new GoogleApiClient.Builder(context)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .enableAutoManage((FragmentActivity)context,null)
                .build();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(gac);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            userName = result.getSignInAccount().getDisplayName();
        }
    }


    public static void login(Activity activity) {
        Log.i(TAG,"sign1");
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(gac);
        Log.i(TAG,"sign2");
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.i(TAG,"sign3");
    }


    private static final String SCOPE_STRING = "oauth2:https://www.googleapis.com/auth/plus.me";

    public static void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            final GoogleSignInAccount acct = result.getSignInAccount();
            Log.i(TAG,acct.getDisplayName());
            Log.i(TAG,acct.getEmail());
            Log.i(TAG,acct.getId());
            Log.i(TAG,"isconnected: "+gac.isConnected());
            gac.connect();
            new AsyncTask<Void,Void,Void>() {

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        String token = GoogleAuthUtil.getToken(context, acct.getEmail(), SCOPE_STRING);
                        Log.i(TAG,token);
                        Networking.client.user().logout().execute();
                        Networking.client.user().loginGoogle(token, new KinveyUserCallback() {
                            @Override
                            public void onSuccess(User user) {
                                Log.i(TAG,"kinvey loginGoogle success");
                                userName = acct.getDisplayName();
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
        }
    }

    public static void ping() {
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

    public static void logout() {
        Auth.GoogleSignInApi.signOut(gac).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Networking.client.user().logout().execute();
                        userName = null;
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

    public static String getUserName() {
        return userName;
    }
}
