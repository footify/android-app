package eu.epitech.croucour.footify.Pub;

import java.util.List;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Home.IHomeView;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by croucour on 29/04/17.
 */

public class PubService {

    private final IPubView _view;
    private final IPubService _api;
    private IPubService _apiBasic;

    interface IPubService {
        @Headers("Content-Type: application/json")
        @GET("pubs/{pub_id}/babyfoots")
        Call<List<BabyFootEntity>> getBabyFoots(@Path("pub_id") String bar_id);

        @Headers("Content-Type: application/json")
        @GET("pubs/{pub_id}/feeds")
        Call<List<GameEntity>> getFeeds(@Path("pub_id") String bar_id);

        @Headers("Content-Type: application/json")
        @GET("pubs/{pub_id}/rankings")
        Call<List<LigueRankingEntity>> getPubRanking(String pub_id);
    }

    PubService(IPubView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;

        this._api = ServiceGeneratorApi.createService(IPubService.class, "api", tokenEntity, manager);
        this._apiBasic = ServiceGeneratorApi.createService(IPubService.class, "api", manager);
    }

    public void getBabyFoots(String pub_id) {
        Call<List<BabyFootEntity>> call = _apiBasic.getBabyFoots(pub_id);
        call.enqueue(new Callback<List<BabyFootEntity>>() {
            @Override
            public void onResponse(Call<List<BabyFootEntity>> call, Response<List<BabyFootEntity>> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    List<BabyFootEntity> babyFootEntities = response.body();
                    _view.setBabyFoots(babyFootEntities);
                }

            }

            @Override
            public void onFailure(Call<List<BabyFootEntity>> call, Throwable t) {
            }
        });
    }

    public void getHistoric(String pub_id) {
        Call<List<GameEntity>> call = _apiBasic.getFeeds(pub_id);
        call.enqueue(new Callback<List<GameEntity>>() {
            @Override
            public void onResponse(Call<List<GameEntity>> call, Response<List<GameEntity>> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    List<GameEntity> gameEntities = response.body();
                    _view.setFeeds(gameEntities);
                }

            }

            @Override
            public void onFailure(Call<List<GameEntity>> call, Throwable t) {
            }
        });
    }
    public void getPubRanking(String pub_id) {
        Call<List<LigueRankingEntity>> call = _apiBasic.getPubRanking(pub_id);
        call.enqueue(new Callback<List<LigueRankingEntity>>() {
            @Override
            public void onResponse(Call<List<LigueRankingEntity>> call, Response<List<LigueRankingEntity>> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    List<LigueRankingEntity> ligueRankingEntities = response.body();
                    _view.setPubRanking(ligueRankingEntities);
                }
            }

            @Override
            public void onFailure(Call<List<LigueRankingEntity>> call, Throwable t) {
            }
        });
    }
}
