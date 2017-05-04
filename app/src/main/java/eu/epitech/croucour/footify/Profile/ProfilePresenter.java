package eu.epitech.croucour.footify.Profile;

import android.util.Patterns;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 27/09/2016.
 */
class ProfilePresenter {

    private final ProfileService _profileService;
    private final IProfileView _iProfileView;
    private final IFriendsView _iFiendsView;

    ProfilePresenter(IProfileView view, IFriendsView iFiendsView, Manager manager, TokenEntity tokenEntity) {
        this._iProfileView = view;
        this._iFiendsView = iFiendsView;
        this._profileService = new ProfileService(view, iFiendsView, manager, tokenEntity);
    }

    void getProfile(boolean populate) {
        _profileService.getProfile(populate);
    }

    void onSaveClicked() {
        String email = _iProfileView.getEmail();
        String firstName = _iProfileView.getFirstName();
        String lastName = _iProfileView.getLastName();

        UserEntity userEntity = _iProfileView.getUserEntity();

        boolean isError = false;
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                _iProfileView.setErrorEmail(R.string.empty_input);
            }
            else {
                _iProfileView.setErrorEmail(R.string.invalid_email);
            }
            isError = true;
        }
        if (firstName.isEmpty()) {
            _iProfileView.setErrorFirstName(R.string.empty_input);
            isError = true;
        }
        if (lastName.isEmpty()) {
            _iProfileView.setErrorLastName(R.string.empty_input);
            isError = true;
        }

        MaterialEditText materialEditTextEmail = _iProfileView.getMaterialEditTextEmail();

        if (materialEditTextEmail.getError() == null && !isError) {

            HashMap<String, String> params = new HashMap<>();

            if (!Objects.equals(userEntity.get_email(), email)) {
                params.put("email", email);
            }
            if (!Objects.equals(userEntity.get_first_name(), firstName)) {
                params.put("first_name", firstName);
            }
            if (!Objects.equals(userEntity.get_last_name(), lastName)) {
                params.put("last_name", lastName);
            }

            _profileService.patchProfile(params);
        }
    }

    void checkEmail() {
        String email = _iProfileView.getEmail();

        UserEntity userEntity = _iProfileView.getUserEntity();
        if (email != null && !email.isEmpty() && userEntity != null && !Objects.equals(userEntity.get_email(), email)) {
            this._profileService.checkEmail(email);
        }
    }

    void follow(String user_id, boolean isFollow) {
        _profileService.follow(user_id, isFollow);
    }

    void getFollowing() {
//        _profileService.getFollowing();
    }

    public void getFriendsList(String type_view) {
        _profileService.getFriendsList(type_view);
    }

    public void deleteAnswer(String friend_id) {
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", friend_id);

        _profileService.deleteAnswer(params);
    }

    public void deny(String friend_id) {
        _profileService.deny(friend_id);
    }

    public void accept(String friend_id) {
        _profileService.accept(friend_id);
    }

    public void addFriend(String friend_id) {
        _profileService.addFriend(friend_id);
    }
}
