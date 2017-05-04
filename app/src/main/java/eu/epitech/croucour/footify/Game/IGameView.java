package eu.epitech.croucour.footify.Game;

import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;

/**
 * Created by roucou_c on 16/12/2016.
 */
public interface IGameView {
    void getHistoric(String babyFootEntity_id);

    void addGame(String babyFootEntity_id);

    void shareGame(GameEntity gameEntity);

    void cancelRefresh();
}
