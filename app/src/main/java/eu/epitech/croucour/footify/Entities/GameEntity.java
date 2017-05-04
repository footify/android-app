package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class GameEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("babyfoot_id")
    private String _babyfoot_id = null;
    @SerializedName("teams")
    private List<TeamEntity> _teams = null;
    @SerializedName("winner")
    private String _winner = null;
    @SerializedName("scores")
    private List<Integer> _scores = null;
    @SerializedName("created_at")
    private String _created_at = null;
    @SerializedName("updated_at")
    private String _updated_at = null;
    @SerializedName("start_date")
    private String _start_date = null;
    @SerializedName("end_date")
    private String _end_date = null;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_babyfoot_id() {
        return _babyfoot_id;
    }

    public void set_babyfoot_id(String _babyfoot_id) {
        this._babyfoot_id = _babyfoot_id;
    }

    public List<TeamEntity> get_teams() {
        return _teams;
    }

    public void set_teams(List<TeamEntity> _teams) {
        this._teams = _teams;
    }

    public String get_winner() {
        return _winner;
    }

    public void set_winner(String _winner) {
        this._winner = _winner;
    }

    public List<Integer> get_scores() {
        return _scores;
    }

    public void set_scores(List<Integer> _scores) {
        this._scores = _scores;
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

    public String get_start_date() {
        return _start_date;
    }

    public void set_start_date(String _start_date) {
        this._start_date = _start_date;
    }

    public String get_end_date() {
        return _end_date;
    }

    public void set_end_date(String _end_date) {
        this._end_date = _end_date;
    }
}
