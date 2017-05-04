package eu.epitech.croucour.footify.BabyFoot;

import java.util.ArrayList;
import java.util.List;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.TeamEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
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

public class BabyFootService {

    private final IBabyFootView _view;
    private final IBabyFootService _api;

    interface IBabyFootService {
        @Headers("Content-Type: application/json")
        @GET("/{babyFoot_id}")
        Call<List<GameEntity>> getHistoric(@Path("babyFoot_id") String babyFoot_id);

        @Headers("Content-Type: application/json")
        @GET("/{babyFoot_id}")
        Call<List<LigueRankingEntity>> getRanking(@Path("babyFoot_id") String babyFootEntity_id);
    }

    BabyFootService(IBabyFootView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;

        this._api = ServiceGeneratorApi.createService(IBabyFootService.class, "api", tokenEntity, manager);
    }

    public void getHistoric(String babyFootEntity_id) {
        Call<List<GameEntity>> call = _api.getHistoric(babyFootEntity_id);
        call.enqueue(new Callback<List<GameEntity>>() {
            @Override
            public void onResponse(Call<List<GameEntity>> call, Response<List<GameEntity>> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    List<GameEntity> gameEntities = response.body();
                    _view.setHistoric(gameEntities);
                }
            }

            @Override
            public void onFailure(Call<List<GameEntity>> call, Throwable t) {
            }
        });

        List<GameEntity> gameEntities = new ArrayList<>();
        GameEntity gameEntity = new GameEntity();
        gameEntity.set_id("id");
        gameEntity.set_babyfoot_id(babyFootEntity_id);

        List<TeamEntity> teamEntities = new ArrayList<>();
        TeamEntity teamEntityBleu = new TeamEntity();

        teamEntityBleu.set_id("1");

        List<FriendEntity> friendEntities = new ArrayList<>();

        FriendEntity friendEntity = new FriendEntity();
        friendEntity.set_pseudo("user1");
        friendEntities.add(friendEntity);
        FriendEntity friendEntity2 = new FriendEntity();
        friendEntity2.set_pseudo("user2");
        friendEntities.add(friendEntity2);

        teamEntityBleu.set_players(friendEntities);

        TeamEntity teamEntityRed = new TeamEntity();

        teamEntityRed.set_id("2");

        List<FriendEntity> friendEntities2 = new ArrayList<>();

        FriendEntity friendEntity3 = new FriendEntity();
        friendEntity3.set_pseudo("user3");
        friendEntities2.add(friendEntity3);
        FriendEntity friendEntity4 = new FriendEntity();
        friendEntity4.set_pseudo("user4");
        friendEntities2.add(friendEntity4);

        teamEntityRed.set_players(friendEntities2);

        teamEntities.add(teamEntityBleu);
        teamEntities.add(teamEntityRed);
        gameEntity.set_teams(teamEntities);

        List<Integer> scrores = new ArrayList<>();
        scrores.add(1);
        scrores.add(2);
        gameEntity.set_scores(scrores);

        gameEntity.set_winner("2");

        gameEntities.add(gameEntity);
        gameEntities.add(gameEntity);
        gameEntities.add(gameEntity);
        gameEntities.add(gameEntity);
        gameEntities.add(gameEntity);
        gameEntities.add(gameEntity);
        _view.setHistoric(gameEntities);

    }

    public void getRanking(String babyFootEntity_id) {
        Call<List<LigueRankingEntity>> call = _api.getRanking(babyFootEntity_id);
        call.enqueue(new Callback<List<LigueRankingEntity>>() {
            @Override
            public void onResponse(Call<List<LigueRankingEntity>> call, Response<List<LigueRankingEntity>> response) {

                if (!response.isSuccessful()) {

                }
                else {
                    List<LigueRankingEntity> ligueRankingEntities = response.body();
                    _view.setRanking(ligueRankingEntities);
                }
            }

            @Override
            public void onFailure(Call<List<LigueRankingEntity>> call, Throwable t) {
            }
        });
    }
}
