package eu.epitech.croucour.footify.Entities;

/**
 * Created by roucou_c on 09/09/2016.
 */

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ClientEntity implements Serializable{
    @SerializedName("id")
    private String _id = null;
    @SerializedName("name")
    private String _name = null;
    @SerializedName("key")
    private String _key = null;
    @SerializedName("secret")
    private String _secret = null;
    @SerializedName("created_at")
    private String  _created_at = null;
    @SerializedName("updated_at")
    private String _updated_at = null;

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

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_secret() {
        return _secret;
    }

    public void set_secret(String _secret) {
        this._secret = _secret;
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
