package eu.epitech.croucour.footify.Profile;

import android.widget.ImageView;

import java.util.List;

import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;

/**
 * Created by roucou_c on 16/12/2016.
 */
interface IFriendsView {
    void setFriendList(FriendListEntity friendListEntity);

    void showDialog(String id, String type);

    void setImage(String picture_url, ImageView photo);
}
