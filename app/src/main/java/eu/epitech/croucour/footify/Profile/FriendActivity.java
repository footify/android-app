package eu.epitech.croucour.footify.Profile;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.FriendListEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.R;

/**
 * Created by roucou_c on 16/12/2016.
 */

public class FriendActivity extends AppCompatActivity implements IFriendsView {

    private Manager _manager;
    private ProfilePresenter _profilePresenter;
    private SwipeRefreshLayout _profile_friend_swipeRefreshLayout;
    private RecyclerView _profile_followers_recycler_view;
    private FriendAdapter _profile_friends_adapter;
    private Toolbar _toolbar;
    private DisplayImageOptions _displayImageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _manager = Manager.getInstance(getApplicationContext());

        TokenEntity tokenEntity = _manager._tokenManager.select();
        _profilePresenter = new ProfilePresenter(null, this, _manager, tokenEntity);

        setContentView(R.layout.profile_friends);

        _profile_friend_swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        _profile_friend_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                _profilePresenter.getFriendsList("friendList");
            }
        });
        _profile_followers_recycler_view = (RecyclerView) findViewById(R.id.profile_friend_recycler_view);

        _profile_friends_adapter = new FriendAdapter(getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        _profile_followers_recycler_view.setLayoutManager(mLayoutManager3);
        _profile_followers_recycler_view.setItemAnimator(new DefaultItemAnimator());
        _profile_followers_recycler_view.setAdapter(_profile_friends_adapter);

        _displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        _profilePresenter.getFriendsList("friendList");
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Vos Amis");
    }

    @Override
    public void setFriendList(FriendListEntity friendListEntity) {

        _profile_friends_adapter.set_friendListEntity(friendListEntity);
        _profile_friends_adapter.notifyDataSetChanged();

        _profile_friend_swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDialog(final String friend_id, final String type) {

        String title = null;
        String content = null;
        String positive = null;
        String negative = null;
        switch (type) {
            case "waiting answer":
                title = "Votre de demande en ami est en attente";
                content = "Voulez vous supprimer votre demande d'ajout en ami ?";
                positive = "Supprimer";
                negative = "Annuler";
                break;
            case "waiting approval":
                title = "Un ami attend votre réponse";
                content = "Voulez vous accepter la demande en ami ?";
                positive = "Accpeter";
                negative = "Refuser";
                break;
        }

        if (title != null) {
            new MaterialDialog.Builder(this)
                    .title(title)
                    .content(content)
                    .positiveText(positive)
                    .negativeText(negative)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (Objects.equals(type, "waiting answer")) {
//                                _profilePresenter.deleteAnswer(friend_id);
                            }
                            else if (Objects.equals(type, "waiting approval")){
                                _profilePresenter.accept(friend_id);
                            }
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (Objects.equals(type, "waiting answer")) {
                            }
                            else if (Objects.equals(type, "waiting approval")){
                                _profilePresenter.deny(friend_id);
                            }
                        }
                    })
                    .show();
        }
    }

    @Override
    public void setImage(String picture_url, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(picture_url, imageView, _displayImageOptions);
    }
}
