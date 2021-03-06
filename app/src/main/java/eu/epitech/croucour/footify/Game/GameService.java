package eu.epitech.croucour.footify.Game;


import java.util.HashMap;
import java.util.Map;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.Interceptor.AuthInterceptor;
import eu.epitech.croucour.footify.MyService;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import eu.epitech.croucour.footify.SignInSignUp.ISignInSignUpView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by roucou-c on 07/12/15.
 */
public class GameService extends MyService {

    private final IGameService _api;

    public interface IGameService {
        @Headers("Content-Type: application/json")
        @POST("matches")
        Call<Void> addGame(@Body Map<String, Object> params);
    }

    private final IGameAddView _view;

    public GameService(IGameAddView view, Manager manager, TokenEntity tokenEntity) {
        super(manager);
        this._view = view;
        this._api = ServiceGeneratorApi.createService(IGameService.class, "api", tokenEntity, _manager);
    }

    public void addGame(HashMap<String, Object> params) {
        Call<Void> call = _api.addGame(params);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    _view.finishAddGame();
                }
                else if (response.code() == 404){
                    _view.setError(R.string.add_game_error);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
