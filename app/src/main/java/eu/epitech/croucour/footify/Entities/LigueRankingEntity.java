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
    @SerializedName("league")
    private String _ligue_id = null;
    @SerializedName("team")
    private TeamEntity _team = null;
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

    public TeamEntity get_team() {
        return _team;
    }

    public void set_team(TeamEntity _team) {
        this._team = _team;
    }

    public Integer get_point() {
        return _point;
    }

    public void set_point(Integer _point) {
        this._point = _point;
    }
}
