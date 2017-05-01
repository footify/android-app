package eu.epitech.croucour.footify.SignInSignUp;


import com.facebook.login.LoginManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
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
        @POST("users")
        Call<UserEntity> signUp(@Body Map<String, String> params);
    }

    private final ISignInSignUpView _view;

    public SignInSignUpService(ISignInSignUpView view, Manager manager) {
        super(manager);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(ISignUpService.class, "api", _manager);
    }

    public void signUp(final HashMap<String, String> userParams) {

        Call call = _api.signUp(userParams);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String token = response.body();
                    _manager._globalManager.addGlobal("token", token);
//                    _view.signUpSuccess();
                } else {
//                    _view.setError(R.string.signup_error);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    public String refreshTokenSync(TokenEntity tokenEntity) {
        return null;
    }

    public void signInExternalApi(HashMap<String, String> params, String facebook) {
//        Call call = _api.signInFacebook(params);
//        call.enqueue(new Callback<TokenEntity>() {
//            @Override
//            public void onResponse(Call<TokenEntity> call, Response<TokenEntity> response) {
//                if (response.isSuccessful()) {
//                    signInOnResponse(response);
//                }
//                else {
//                    ApiError apiError = new ApiError(response.errorBody(), response.code(), "signIn");
//                    if (apiError.get_httpCode() == 404) {
//                        _iSignInSignUpView.changeStep("step2");
//                    }
//                    else if (apiError.get_httpCode() == 403) {
//                        _view.setError(apiError.get_target_message());
//                        if (Objects.equals(api, "facebook")) {
//                            LoginManager.getInstance().logOut();
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TokenEntity> call, Throwable t) {
//                //                if (t instanceof UnknownHostException){
//                //                    signInWithoutConnexion();
//                //                }
//            }
//        });
    }
}
