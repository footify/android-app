package eu.epitech.croucour.footify.Pub;

import android.widget.ImageView;

import java.util.List;

import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;

/**
 * Created by croucour on 29/04/17.
 */

public interface IPubView {
    void setImage(String picture_url, ImageView photo);

    void setBabyFoots(List<BabyFootEntity> babyFootEntities);

    void setFeeds(List<GameEntity> gameEntities);

    void setPubRanking(List<LigueRankingEntity> ligueRankingEntities);
}
