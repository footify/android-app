package eu.epitech.croucour.footify.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by roucou_c on 27/09/2016.
 */
public class ProfileActivity extends AppCompatActivity implements IProfileView, View.OnClickListener, IFriendsView, View.OnFocusChangeListener {

    private Toolbar _toolbar;
    private Manager _manager;
    private UserEntity _userEntity;
    private ImageView _profile_edit;
//    private List<FollowEntity> _followEntities;
    private TextView _profile_nbFriends;
    private LinearLayout _profile_linearLayout_friend;
    private DisplayImageOptions _displayImageOptions;
    private ImageView _profile_photo;
    private ProfilePresenter _profilePresenter;
    private Object _curentStep;
    private TextView _profile_email_textView;
    private TextView _profile_name_textView;
    private TextView _profile_pseudo_textView;
    private MaterialEditText _profile_edit_email;
    private MaterialEditText _profile_edit_lastName;
    private MaterialEditText _profile_edit_firstName;
    private FriendEntity _friendEntity;
    private boolean _ownUser;
    private FriendListEntity _friendListEntity;
    private ImageView _profile_add_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _manager = Manager.getInstance(getApplicationContext());

        TokenEntity tokenEntity = _manager._tokenManager.select();
        _profilePresenter = new ProfilePresenter(this, this, _manager, tokenEntity);


        _friendEntity = (FriendEntity) getIntent().getSerializableExtra("friendEntity");


        if (_friendEntity != null) {
            _ownUser = false;
        }
        else {
            _ownUser = true;
        }
        changeStep("profile");

//        _profilePresenter.getProfile(_ownUser);
//        _profilePresenter.getFriendsList("profile");


        _displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }

    @Override
    public void changeStep(String step) {
        _curentStep = step;
        switch (step) {
            case "profile":
                setContentView(R.layout.profile);

                _profile_email_textView = (TextView) findViewById(R.id.profile_email_textView);
                _profile_name_textView = (TextView) findViewById(R.id.profile_name_textView);
                _profile_pseudo_textView = (TextView) findViewById(R.id.profile_pseudo_textView);

                _profile_nbFriends = (TextView) findViewById(R.id.profile_nbfriends);
                _profile_linearLayout_friend = (LinearLayout) findViewById(R.id.profile_linearLayout_friend);
                _profile_linearLayout_friend.setOnClickListener(this);

                _profile_photo = (ImageView) findViewById(R.id.profile_photo);

                _profile_edit = (ImageView) findViewById(R.id.profile_edit);
                if (_ownUser) {
                    _profile_edit.setVisibility(View.VISIBLE);
                    _profile_edit.setOnClickListener(this);
                }
                else {
                    LinearLayout profile_email_layout = (LinearLayout) findViewById(R.id.profile_email_layout);
                    profile_email_layout.setVisibility(View.GONE);

                    _profile_edit.setVisibility(View.GONE);
                    this.populateProfile(null, _friendEntity);
                }

                _profile_add_friend = (ImageView) findViewById(R.id.profile_add_friend);
                _profile_add_friend.setOnClickListener(this);

                getSupportActionBar().setTitle("Profil");
                _profile_linearLayout_friend.setVisibility(View.GONE);

                _profilePresenter.getProfile(_ownUser);
                _profilePresenter.getFriendsList("profile");

                break;
            case "editProfile":
                setContentView(R.layout.profile_edit);

                FancyButton profile_edit_submit = (FancyButton) findViewById(R.id.profile_edit_submit);

                _profile_edit_email = (MaterialEditText) findViewById(R.id.profile_edit_email);
                _profile_edit_lastName = (MaterialEditText) findViewById(R.id.profile_edit_lastname);
                _profile_edit_firstName = (MaterialEditText) findViewById(R.id.profile_edit_firstname);

                _profile_edit_email.setOnFocusChangeListener(this);

                _profile_edit_email.setText(_userEntity.get_email());
                _profile_edit_lastName.setText(_userEntity.get_last_name());
                _profile_edit_firstName.setText(_userEntity.get_first_name());

                if (profile_edit_submit != null) {
                    profile_edit_submit.setOnClickListener(this);
                }

                getSupportActionBar().setTitle(getString(R.string.profile_edit_title));
                break;
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profil");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Objects.equals(_curentStep, "profile")) {
                    this.finish();
                }
                else if (Objects.equals(_curentStep, "editProfile")) {
                    changeStep("profile");
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void populateProfile(UserEntity userEntity, FriendEntity friendEntity) {
        if (userEntity != null || friendEntity != null) {
            String lastName;
            String firstName;
            String picture_url;
            String pseudo;
            if (userEntity != null) {
                _profile_email_textView.setText(userEntity.get_email());
                lastName = userEntity.get_last_name();
                firstName = userEntity.get_first_name();
                picture_url = userEntity.get_picture_url();
                pseudo = userEntity.get_pseudo();
            } else {
                lastName = friendEntity.get_last_name();
                firstName = friendEntity.get_first_name();
                picture_url = friendEntity.get_picture_url();
                pseudo = friendEntity.get_pseudo();
            }
            String name = lastName + " " + firstName;
            _profile_name_textView.setText(name);
            _profile_pseudo_textView.setText("@" + pseudo);

            getImageProfile(picture_url, _profile_photo);
        }
        else {
            _profile_add_friend.setVisibility(View.GONE);
        }
    }

    @Override
    public String getEmail() {
        return _profile_edit_email.getText().toString();
    }

    @Override
    public String getFirstName() {
        return _profile_edit_firstName.getText().toString();
    }

    @Override
    public String getLastName() {
        return _profile_edit_lastName.getText().toString();
    }

    @Override
    public void setErrorEmail(int resId) {
        _profile_edit_email.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorFirstName(int resId) {
        _profile_edit_firstName.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public void setErrorLastName(int resId) {
        _profile_edit_lastName.setError(resId == 0 ? null : getString(resId));
    }

    @Override
    public MaterialEditText getMaterialEditTextEmail() {
        return _profile_edit_email;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_edit:
                changeStep("editProfile");
                break;
            case R.id.profile_add_friend:
                _profilePresenter.addFriend(_friendEntity.get_id());
                break;
            case R.id.profile_edit_submit:
                _profilePresenter.onSaveClicked();
                break;
            case R.id.profile_linearLayout_friend:
                Intent intent = new Intent(ProfileActivity.this, FriendActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.profile_edit_email) {
            _profilePresenter.checkEmail();
        }
    }

    @Override
    public UserEntity getUserEntity() {
        return _userEntity;
    }

    @Override
    public void setError(int resId) {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        if (coordinatorLayout != null) {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, resId, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

//    @Override
//    public void follow(boolean isFollow) {
//        if (_follow != null) {
//            if (isFollow) {
//                _follow.setImageDrawable(getDrawable(R.drawable.ic_star_white_24dp));
//            }
//            else {
//                _follow.setImageDrawable(getDrawable(R.drawable.ic_star_border_white_24dp));
//            }
//        }
//        _profilePresenter.getFollowing();
//        _profilePresenter.getProfile(false);
//    }

    @Override
    public void setUserEntity(UserEntity userEntity) {
        _userEntity = userEntity;
    }

    @Override
    public void setFriendList(FriendListEntity friendListEntity) {
        _friendListEntity = friendListEntity;

        if (_ownUser) {
            if (!(friendListEntity.get_accepted().isEmpty() && friendListEntity.get_waiting_approval().isEmpty() && friendListEntity.get_waiting_answer().isEmpty())) {
                _profile_nbFriends.setText(Integer.toString(friendListEntity.get_accepted().size()+friendListEntity.get_waiting_approval().size()+friendListEntity.get_waiting_answer().size()));
                _profile_linearLayout_friend.setVisibility(View.VISIBLE);

            }
        }
        else {
            Boolean found = false;

            for (FriendEntity friendEntity : friendListEntity.get_accepted()) {
                if (Objects.equals(_friendEntity.get_id(), friendEntity.get_id())) {
                    found = true;
                }
            }
            if (!found) {
                for (FriendEntity friendEntity : friendListEntity.get_waiting_approval()) {
                    if (Objects.equals(_friendEntity.get_id(), friendEntity.get_id())) {
                        found = true;
                    }
                }
            }
            if (!found) {
                for (FriendEntity friendEntity : friendListEntity.get_waiting_answer()) {
                    if (Objects.equals(_friendEntity.get_id(), friendEntity.get_id())) {
                        found = true;
                    }
                }
            }
            if (!found) {
                _profile_add_friend.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void showDialog(String id, String type) {}

    //    @Override
//    public void setFollowing(List<FollowEntity> followEntities) {
//        _followEntities = followEntities;
//
//        if (!_ownUser) {
//            boolean contain = false;
//
//            for (FollowEntity followEntity : followEntities) {
//                if (Objects.equals(followEntity.get_following().get_id(), _friendEntity.get_id())) {
//                    contain = true;
//                }
//            }
//            if (_profile_edit != null) {
//                _profile_edit.setVisibility(View.GONE);
//                _follow = (ImageView) findViewById(R.id.profile_follow);
//                _follow.setOnClickListener(this);
//                _follow.setVisibility(View.VISIBLE);
//                if (contain) {
//                    _follow.setImageDrawable(getDrawable(R.drawable.ic_star_white_24dp));
//                }
//                else {
//                    _follow.setImageDrawable(getDrawable(R.drawable.ic_star_border_white_24dp));
//                }
//            }
//
//        }
//    }

//    @Override
//    public void setFollowers(List<FollowerEntity> followerEntities) {
//        _profile_linearLayout_friend.setVisibility(View.VISIBLE);
//        int nb = followerEntities != null ? followerEntities.size() : 0;
//        _profile_nbFriends.setText(String.valueOf(nb));
//    }

    public void getImageProfile(String url, ImageView photo) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(url, photo, _displayImageOptions);
    }
}
