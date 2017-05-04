package eu.epitech.croucour.footify.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Date;

import eu.epitech.croucour.footify.Api.ApiLogin;
import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.HomeActivity;
import eu.epitech.croucour.footify.R;
import mehdi.sakout.fancybuttons.FancyButton;


/**
 * Created by roucou_c on 01/09/2016.
 */
public final class SignInSignUpActivity extends AppCompatActivity implements ISignInSignUpView, View.OnClickListener {

    private ApiLogin _apiLogin;
    private SignInSignUpPresenter _signInSignUoPresenter;
    private View _coordinatorLayout;
    private String _step;
    private FancyButton _signup_submit;
    private MaterialEditText _signup_pseudo;
    private String _token_facebook;
    private LoginButton _facebook_login;
    private CallbackManager callbackManager;
    private Manager _manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _manager = Manager.getInstance(getApplicationContext());
        _signInSignUoPresenter = new SignInSignUpPresenter(this, _manager);

        autoLogin();

        changeStep("step1");
    }

    private void autoLogin() {
        TokenEntity tokenEntity = _manager._tokenManager.select();

        if (tokenEntity != null && isValidAccessToken(tokenEntity.get_expire_access_token())) {
            this.signInSignUpSuccess();
        }
    }

    public boolean isValidAccessToken(String expireAccess_token) {
        Date date = new Date();

        if (date.getTime() > Long.parseLong(expireAccess_token)) {
            return false;
        }
        return true;
    }

    @Override
    public void changeStep(String step) {
        _step = step;

        _coordinatorLayout = (CoordinatorLayout) findViewById(R.id.display_snackbar);

        switch (step) {
            case "step1":
                _apiLogin = new ApiLogin(getApplicationContext(), this);
                AppEventsLogger.activateApp(getApplication());

                setContentView(R.layout.signin_signup);

                LoginManager.getInstance().logOut();

                _facebook_login = (LoginButton) findViewById(R.id.signinsignup_facebook);
                _apiLogin.launch();
                break;
            case "step2":
                LoginManager.getInstance().logOut();
                setContentView(R.layout.signup_pseudo);


                _signup_submit = (FancyButton) findViewById(R.id.signup_submit);
                _signup_submit.setOnClickListener(this);
                _signup_pseudo = (MaterialEditText) findViewById(R.id.signup_pseudo);
                break;
        }
    }

    @Override
    public void setError(int resId) {
        if (_coordinatorLayout != null) {
            Snackbar snackbar = Snackbar
                    .make(_coordinatorLayout, resId, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Override
    public LoginButton getSignInButtonFacebook() {
        return (LoginButton) findViewById(R.id.signinsignup_facebook);
    }

    @Override
    public void onFacebookClicked(String access_token_facebook) {
        _token_facebook = access_token_facebook;
        _signInSignUoPresenter.onLoginFacebookClicked(access_token_facebook);
    }

    @Override
    public void signInSignUpSuccess() {
        this.finish();
        Intent intent = new Intent(SignInSignUpActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void setErrorPseudo(int resId) {
        _signup_pseudo.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_submit:
                _signInSignUoPresenter.signUpFacebook(_token_facebook, _signup_pseudo.getText().toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        _apiLogin.onActivityResult(requestCode, resultCode, data);
    }
}