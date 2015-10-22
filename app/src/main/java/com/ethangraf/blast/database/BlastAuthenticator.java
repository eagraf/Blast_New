package com.ethangraf.blast.database;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ethangraf.blast.ui.PickAccountActivity;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import java.io.IOException;

/**
 * Created by Da-Jin on 10/19/2015.
 */
public class BlastAuthenticator extends AbstractAccountAuthenticator {
    private static final String TAG = "BlastAuthenticator";
    public static final String AUTHORITY = "com.ethangraf.blast";
    Context mContext;
    public BlastAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, PickAccountActivity.class);
        //Tell PickAccount it is a new account, not just an error that requires signing in again
        intent.putExtra(PickAccountActivity.ARG_IS_ADDING_NEW_ACCOUNT,true);
        //PickAccountActivity extends AccountAuthenticatorActivity, which requires response in the intent like this
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,response);

        //Give the intent as KEY_INTENT to tell accountManager to start activity
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT,intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d("BlastAuthenticator", "getAuthToken called. token type "+authTokenType);

        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        if(TextUtils.isEmpty(authToken)){
            //Try authenticating with current account
            Log.d(TAG,"IMPLEMENT RETRY AUTH TOKEN IN AUTHENTICATOR GET AUTHTOKEN");
            String scope = String.format("audience:server:client_id:%s", "732247720107-2poeg1p0d8t445hs7bbcuqbk5chnhod4.apps.googleusercontent.com");
            try {
                authToken = GoogleAuthUtil.getTokenWithNotification(mContext,account.name,scope,null,AUTHORITY,null);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }
        }

        //If it's not empty(got from am, or previous if statement got it from GoogleAuthUtil)
        if(!TextUtils.isEmpty(authToken)){
            //return it
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        //Didn't work, retry it all through PickAccount.
        final Intent intent = new Intent(mContext, PickAccountActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(PickAccountActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
