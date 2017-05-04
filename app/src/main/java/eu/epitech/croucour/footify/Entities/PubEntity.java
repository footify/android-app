package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PubEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("picture_url")
    private String _picture_url = null;
    @SerializedName("name")
    private String _name = null;
    @SerializedName("street_number")
    private String _street_number = null;
    @SerializedName("street_name")
    private String _street_name = null;
    @SerializedName("zip_code")
    private String _zip_code = null;
    @SerializedName("city")
    private String _city = null;
    @SerializedName("country")
    private String _country = null;
    @SerializedName("admins")
    private List<FriendEntity> _admins = null;
    @SerializedName("open_at")
    private String _open_at = null;
    @SerializedName("close_at")
    private String _close_at = null;
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

    public String get_picture_url() {
        return _picture_url;
    }

    public void set_picture_url(String _picture_url) {
        this._picture_url = _picture_url;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_street_number() {
        return _street_number;
    }

    public void set_street_number(String _street_number) {
        this._street_number = _street_number;
    }

    public String get_street_name() {
        return _street_name;
    }

    public void set_street_name(String _street_name) {
        this._street_name = _street_name;
    }

    public String get_zip_code() {
        return _zip_code;
    }

    public void set_zip_code(String _zip_code) {
        this._zip_code = _zip_code;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_country() {
        return _country;
    }

    public void set_country(String _country) {
        this._country = _country;
    }

    public List<FriendEntity> get_admins() {
        return _admins;
    }

    public void set_admins(List<FriendEntity> _admins) {
        this._admins = _admins;
    }

    public String get_open_at() {
        return _open_at;
    }

    public void set_open_at(String _open_at) {
        this._open_at = _open_at;
    }

    public String get_close_at() {
        return _close_at;
    }

    public void set_close_at(String _close_at) {
        this._close_at = _close_at;
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
