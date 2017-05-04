package eu.epitech.croucour.footify.BabyFoot;

import android.widget.ImageView;

import java.util.List;

import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;

/**
 * Created by croucour on 29/04/17.
 */

public interface IBabyFootView {
    void setImage(String picture_url, ImageView imageView);

    void setHistoric(List<GameEntity> gameEntities);

    void setRanking(List<LigueRankingEntity> ligueRankingEntities);

    void setBabyFoot(BabyFootEntity babyFootEntity);

    void setPub(PubEntity pubEntity);

    void startPubActivity();
}
