package eu.epitech.croucour.footify.SignInSignUp;


import java.util.HashMap;

import eu.epitech.croucour.footify.DAO.Manager;

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

        this._signnignUpService.signInExternalApi(params, "facebook");
        _view.signInSignUpSuccess();
    }
}
