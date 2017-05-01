package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeamEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("players_id")
    private List<String> _players_id = null;
    @SerializedName("created_at")
    private String _created_at = null;
    @SerializedName("updated_at")
    private String _updated_at = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> get_players_id() {
        return _players_id;
    }

    public void set_players_id(List<String> _players_id) {
        this._players_id = _players_id;
    }

    public String get_created_at() {
        return _created_at;
    }

    public void set_created_at(String _created_at) {
        this._created_at = _created_at;
    }

    public String get_updated_at() {
        return _updated_at;
    }

    public void set_updated_at(String _updated_at) {
        this._updated_at = _updated_at;
    }
}
