package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FriendEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("pseudo")
    private String _pseudo = null;
    @SerializedName("first_name")
    private String _first_name = null;
    @SerializedName("last_name")
    private String _last_name = null;
    @SerializedName("picture_url")
    private String _picture_url = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_pseudo() {
        return _pseudo;
    }

    public void set_pseudo(String _pseudo) {
        this._pseudo = _pseudo;
    }

    public String get_first_name() {
        return _first_name;
    }

    public void set_first_name(String _first_name) {
        this._first_name = _first_name;
    }

    public String get_last_name() {
        return _last_name;
    }

    public void set_last_name(String _last_name) {
        this._last_name = _last_name;
    }

    public String get_picture_url() {
        return _picture_url;
    }

    public void set_picture_url(String _picture_url) {
        this._picture_url = _picture_url;
    }
}
