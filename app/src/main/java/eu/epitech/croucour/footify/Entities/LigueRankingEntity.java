package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LigueRankingEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("ligue_id")
    private String _ligue_id = null;
    @SerializedName("team_id")
    private String _team_id = null;
    @SerializedName("point")
    private Integer _point = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_ligue_id() {
        return _ligue_id;
    }

    public void set_ligue_id(String _ligue_id) {
        this._ligue_id = _ligue_id;
    }

    public String get_team_id() {
        return _team_id;
    }

    public void set_team_id(String _team_id) {
        this._team_id = _team_id;
    }

    public Integer get_point() {
        return _point;
    }

    public void set_point(Integer _point) {
        this._point = _point;
    }
}
