package eu.epitech.croucour.footify.Game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by cleme_000 on 27/02/2016.
 */
public class GamePresenter {

    private IGameAddView _view;
    protected GameService _gameService;

    public GamePresenter(IGameAddView view, Manager manager, TokenEntity tokenEntity) {
        this._view = view;
        this._gameService = new GameService(view, manager, tokenEntity);
    }

    public void addGame() {

        BabyFootEntity babyEntity = _view.getBabyFootEntity();
        String team1_user1 = _view.getTeam1User1();
        String team1_user2 = _view.getTeam1User2();
        String team1_score = _view.getTeam1Score();

        String team2_user1 = _view.getTeam2User1();
        String team2_user2 = _view.getTeam2User2();
        String team2_score = _view.getTeam2Score();

        boolean isError = false;

        if (team1_user1.isEmpty()) {
            _view.setErrorTeam1User1(R.string.empty_input);
            isError = true;
        }
        if (team1_user2.isEmpty()) {
            _view.setErrorTeam1User2(R.string.empty_input);
            isError = true;
        }
        if (team1_score.isEmpty()) {
            _view.setErrorTeam1Score(R.string.empty_input);
            isError = true;
        }
        if (team2_user1.isEmpty()) {
            _view.setErrorTeam2User1(R.string.empty_input);
            isError = true;
        }
        if (team2_user2.isEmpty()) {
            _view.setErrorTeam2User2(R.string.empty_input);
            isError = true;
        }
        if (team2_score.isEmpty()) {
            _view.setErrorTeam2Score(R.string.empty_input);
            isError = true;
        }
        if (!isError) {
            List<String> blue_team = new ArrayList<>();
            blue_team.add(team1_user1);
            blue_team.add(team1_user2);

            List<String> red_team = new ArrayList<>();
            red_team.add(team2_user1);
            red_team.add(team2_user2);

            HashMap<String, Object> params = new HashMap<>();
            params.put("baby_id", babyEntity.get_id());
            params.put("blue_team", blue_team);
            params.put("red_team", red_team);
            params.put("blue_score", Integer.parseInt(team1_score));
            params.put("red_score", Integer.parseInt(team2_score));
            _gameService.addGame(params);
        }
    }
}
