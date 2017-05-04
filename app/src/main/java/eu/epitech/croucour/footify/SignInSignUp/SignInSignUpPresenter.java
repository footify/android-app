package eu.epitech.croucour.footify.SignInSignUp;


import java.util.HashMap;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.R;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public class SignInSignUpPresenter {

    private ISignInSignUpView _view;
    protected SignInSignUpService _signnignUpService;

    public SignInSignUpPresenter(ISignInSignUpView view, Manager manager) {
        this._view = view;
        this._signnignUpService = new SignInSignUpService(view, manager);
    }

    public void onLoginFacebookClicked(String access_token_facebook) {
        HashMap<String, String> params = new HashMap<>();

        params.put("access_token", access_token_facebook);

        this._signnignUpService.signInExternalApi(params);
    }

    public void signUpFacebook(String token_facebook, String pseudo) {
        if (pseudo.isEmpty()) {
            _view.setErrorPseudo(R.string.empty_input);
        }
        else {
            HashMap<String, String> params = new HashMap<>();

            params.put("access_token", token_facebook);
            params.put("pseudo", pseudo);

            _signnignUpService.signUpExternalApi(params);
        }
    }
}
