package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FriendListEntity implements Serializable{
    @SerializedName("waiting_answer")
    private FriendEntity _waiting_answer = null;
    @SerializedName("waiting_approval")
    private FriendEntity _waiting_approval = null;
    @SerializedName("accepted")
    private FriendEntity _accepted = null;

    public FriendEntity get_waiting_answer() {
        return _waiting_answer;
    }

    public void set_waiting_answer(FriendEntity _waiting_answer) {
        this._waiting_answer = _waiting_answer;
    }

    public FriendEntity get_waiting_approval() {
        return _waiting_approval;
    }

    public void set_waiting_approval(FriendEntity _waiting_approval) {
        this._waiting_approval = _waiting_approval;
    }

    public FriendEntity get_accepted() {
        return _accepted;
    }

    public void set_accepted(FriendEntity _accepted) {
        this._accepted = _accepted;
    }
}
