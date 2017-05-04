package eu.epitech.croucour.footify.SignInSignUp;


import java.util.HashMap;
import java.util.Map;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.Interceptor.AuthInterceptor;
import eu.epitech.croucour.footify.MyService;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by roucou-c on 07/12/15.
 */
public class SignInSignUpService extends MyService {

    private final ISignUpService _api;

    public interface ISignUpService {
        @Headers("Content-Type: application/json")
        @POST("auth/facebook-connect")
        Call<TokenEntity> signInFacebook(@Body Map<String, String> params);

        @Headers("Content-Type: application/json")
        @POST("auth/facebook-register")
        Call<UserEntity> signUpFacebook(@Body Map<String, String> params);
    }

    private final ISignInSignUpView _view;

    public SignInSignUpService(ISignInSignUpView view, Manager manager) {
        super(manager);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ISignUpService.class, "login", _manager);
    }

    public String refreshTokenSync(TokenEntity tokenEntity) {
        return null;
    }

    public void signInExternalApi(HashMap<String, String> params) {
        Call call = _api.signInFacebook(params);
        call.enqueue(new Callback<TokenEntity>() {
            @Override
            public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
                if (response.isSuccessful()) {
                    TokenEntity tokenEntity = response.body();

                    if (tokenEntity != null) {
                        AuthInterceptor.updateTokenExpire(tokenEntity);
                        _manager._tokenManager.add(tokenEntity);
                        _view.signInSignUpSuccess();
                    }
                }
                else {
                    if (response.code() == 404) {
                        _view.changeStep("step2");
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenEntity> call, Throwable t) {
            }
        });
    }

    public void signUpExternalApi(final HashMap<String, String> params) {
        Call call = _api.signUpFacebook(params);
        call.enqueue(new Callback<UserEntity>() {
            @Override
            public void onResponse(Call<UserEntity> call, Response<UserEntity> response) {

                if (response.isSuccessful()) {
                    UserEntity userEntity = response.body();

                    if (userEntity != null) {
                        signInExternalApi(params);
                    }
                }
                else {
                    if (response.code() == 409) {
                        _view.setError(R.string.email_already_use);
                    }
                    else if (response.code() == 403) {
                        _view.setErrorPseudo(R.string.pseudo_already_use);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserEntity> call, Throwable t) {
            }

        });
    }
}
