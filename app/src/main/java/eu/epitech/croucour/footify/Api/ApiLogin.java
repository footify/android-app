package eu.epitech.croucour.footify.Api;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import eu.epitech.croucour.footify.SignInSignUp.ISignInSignUpView;


/**
 * Created by roucou_c on 17/06/2016.
 */
public class ApiLogin {

    static final int REQUEST_CODE_FACEBOOK = 64206;

    private final Context _context;
    private final ISignInSignUpView _view;


    ApiFacebook _apiFacebook;

    public ApiLogin(Context context, ISignInSignUpView view) {
        this._context = context;
        this._view = view;

        _apiFacebook = new ApiFacebook(_context, view);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("result", String.valueOf(requestCode));
        if (requestCode == REQUEST_CODE_FACEBOOK){
            _apiFacebook.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void launch() {
        this._apiFacebook.launch();
    }
}
