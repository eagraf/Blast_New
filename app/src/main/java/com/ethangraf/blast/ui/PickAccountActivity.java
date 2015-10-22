package com.ethangraf.blast.ui;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ethangraf.blast.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import java.io.IOException;

/**
 * Created by Da-Jin on 10/22/2015.
 */
public class PickAccountActivity extends AccountAuthenticatorActivity{

    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    public static final String KEY_ERROR_MESSAGE = "ERR_MSG";

    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int RC_GOOGLE_LOGIN = 1001;
    private static final String TAG = "PickAccountActivity";

    private String mEmail;

    public static final String ACCOUNT_TYPE = "com.ethangraf";
    private AccountManager mAccountManager;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_account);
        mAccountManager = AccountManager.get(getBaseContext());

        findViewById(R.id.sign_in_google).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });
    }

    public void signInGoogle(){
        //When google button is clicked
        //Use the system account picker to choose a google account
        String[] accountTypes = new String[]{"com.google"};
        Intent intent = AccountPicker.newChooseAccountIntent(null,null,accountTypes,false,null,null,null,null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==REQUEST_CODE_PICK_ACCOUNT){
            //Coming back from the account picker
            if(resultCode == RESULT_OK){
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            }else if(resultCode == RESULT_CANCELED){
                Toast.makeText(this, R.string.pick_account, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getToken(){
        if(mEmail==null){//check again that they picked an account
            signInGoogle();
        }else{
            new AsyncTask<String,Void,Intent>(){

                @Override
                protected Intent doInBackground(String... params) {
                    String token=null;
                    String scope = String.format("audience:server:client_id:%s", "732247720107-2poeg1p0d8t445hs7bbcuqbk5chnhod4.apps.googleusercontent.com");

                    Bundle data = new Bundle();
                    try {
                        token = GoogleAuthUtil.getToken(PickAccountActivity.this, mEmail, scope);
                        data.putString(AccountManager.KEY_ACCOUNT_NAME, mEmail);
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
                        data.putString(AccountManager.KEY_AUTHTOKEN,token);
                    } catch (IOException transientEx) {//when there's an error, just add to intent
                    /* Network or server error */
                        Log.e(TAG, "Error authenticating with Google: " + transientEx);
                        data.putString("Network error: " + transientEx.getMessage(),KEY_ERROR_MESSAGE);
                    } catch (UserRecoverableAuthException e) {
                        Log.w(TAG, "Recoverable Google OAuth error: " + e.toString());
                        Intent recover = e.getIntent();
                        startActivityForResult(recover, RC_GOOGLE_LOGIN);
                    } catch (GoogleAuthException authEx) {
                    /* The call is not ever expected to succeed assuming you have already verified that
                     * Google Play services is installed. */
                        Log.e(TAG, "Error authenticating with Google: " + authEx.getMessage(), authEx);
                        data.putString("Error authenticating with Google: " + authEx.getMessage(),KEY_ERROR_MESSAGE);
                    }
                    final Intent res = new Intent();
                    res.putExtras(data);
                    return res;
                }
                @Override
                protected void onPostExecute(Intent intent) {
                    if (intent.hasExtra(KEY_ERROR_MESSAGE)) {//if there was an error in doInBackground, show it
                        new AlertDialog.Builder(PickAccountActivity.this)
                                .setTitle("Error")
                                .setMessage(intent.getStringExtra(KEY_ERROR_MESSAGE))
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else{//no errors, finish login
                        finishLogin(intent);
                    }
                }
            }.execute();
        }
    }
    private void finishLogin(Intent intent){

        String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
        Account account = new Account(mEmail,ACCOUNT_TYPE);
        if(getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT,false)){

            mAccountManager.addAccountExplicitly(account,null,null);
        }
        mAccountManager.setAuthToken(account, null, authtoken);

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK,intent);
        finish();
    }
}
