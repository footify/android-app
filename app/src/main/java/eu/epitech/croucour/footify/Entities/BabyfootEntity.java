package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BabyfootEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("bar_id")
    private String _bar_id = null;
    @SerializedName("manufacturer")
    private String _manufacturer = null;
    @SerializedName("qr_code_url")
    private String _qr_code_url = null;
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

    public String get_bar_id() {
        return _bar_id;
    }

    public void set_bar_id(String _bar_id) {
        this._bar_id = _bar_id;
    }

    public String get_manufacturer() {
        return _manufacturer;
    }

    public void set_manufacturer(String _manufacturer) {
        this._manufacturer = _manufacturer;
    }

    public String get_qr_code_url() {
        return _qr_code_url;
    }

    public void set_qr_code_url(String _qr_code_url) {
        this._qr_code_url = _qr_code_url;
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
