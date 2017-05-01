package eu.epitech.croucour.footify.SignInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.login.widget.LoginButton;
import eu.epitech.croucour.footify.Api.ApiLogin;
import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Home.HomeActivity;
import eu.epitech.croucour.footify.R;


/**
 * Created by roucou_c on 01/09/2016.
 */
public final class SignInSignUpActivity extends AppCompatActivity implements ISignInSignUpView {

    private ApiLogin _apiLogin;
    private SignInSignUpPresenter _signInSignUoPresenter;
    private View _coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Manager _manager = Manager.getInstance(getApplicationContext());
        _signInSignUoPresenter = new SignInSignUpPresenter(this, _manager);


        _apiLogin = new ApiLogin(getApplicationContext(), this);
        _apiLogin.launch();

        setContentView(R.layout.signin_signup);

        _signInSignUoPresenter = new SignInSignUpPresenter(this, _manager);

        _coordinatorLayout = (CoordinatorLayout) findViewById(R.id.display_snackbar);

        this.signInSignUpSuccess();
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
        return (LoginButton) findViewById(R.id.facebook);
    }

    @Override
    public void onFacebookClicked(String access_token_facebook) {
        _signInSignUoPresenter.onLoginFacebookClicked(access_token_facebook);
    }

    @Override
    public void signInSignUpSuccess() {
        this.finish();
        Intent intent = new Intent(SignInSignUpActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}