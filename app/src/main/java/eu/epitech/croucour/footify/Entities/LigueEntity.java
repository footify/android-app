package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LigueEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("name")
    private String _name = null;
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

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
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
