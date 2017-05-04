package eu.epitech.croucour.footify.SignInSignUp;

import com.facebook.login.widget.LoginButton;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public interface ISignInSignUpView {

    void changeStep(String step);

    void setError(int resId);

    LoginButton getSignInButtonFacebook();

    void onFacebookClicked(String access_token_facebook);

    void signInSignUpSuccess();

    void setErrorPseudo(int resId);
}
