package eu.epitech.croucour.footify.Home;

import java.util.List;

import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;

/**
 * Created by croucour on 29/04/17.
 */

public interface IHomeView {

    void setBabyFoot(BabyFootEntity babyFootEntity);

    void setPubs(PubEntity pubEntity);

    void setProfile(UserEntity userEntity);

    void getUserAndShow(String user_id);

    void startProfileActivity(FriendEntity friendEntity);

    void setGameEntities(List<GameEntity> gameEntities);

    void getPubAndShow(String id);

    void startPubActivity(PubEntity pubEntity);
}
