package eu.epitech.croucour.footify.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.BabyFoot.BabyFootActivity;
import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.DecoderQrCode.DecoderQrCodeActivity;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.FriendEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.TeamEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.Game.GameAdapter;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.Profile.ProfileActivity;
import eu.epitech.croucour.footify.Pub.PubActivity;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.ServiceGeneratorApi;
import eu.epitech.croucour.footify.SignInSignUp.SignInSignUpActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by croucour on 29/04/17.
 */

public class HomeActivity extends AppCompatActivity implements IHomeView, NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener, View.OnClickListener, IGameView {

    private Manager _manager;
    private UserEntity _userEntity;
    private HomePresenter _homePresenter;
    private Toolbar _toolbar;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _drawerToggle;
    private NavigationView _navigation;
    private RecyclerView _search_recycler_view;
    private CoordinatorLayout _search_coordinatorLayout;
    private SearchAdapter _search_adapter;
    private MaterialSearchBar _searchBar;
    private Client _client;
    private CompletionHandler _completionHandler;
    private BabyFootEntity _babyFootEntity;
    private PubEntity _pubEntity;
    private DisplayImageOptions _displayImageOptions;
    private RecyclerView _game_historic_recycler_view;
    private GameAdapter _game_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        _manager = Manager.getInstance(getApplicationContext());

        TokenEntity tokenEntity = _manager._tokenManager.select();

        if (tokenEntity == null) {
            _userEntity = null;
        }
        _homePresenter = new HomePresenter(this, _manager, tokenEntity);


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatActionButton_scan);

        floatingActionButton.setOnClickListener(this);

        initSearch();

        _game_historic_recycler_view = (RecyclerView) findViewById(R.id.game_historic_recycler_view);

        _game_adapter = new GameAdapter(getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        _game_historic_recycler_view.setLayoutManager(mLayoutManager3);
        _game_historic_recycler_view.setItemAnimator(new DefaultItemAnimator());
        _game_historic_recycler_view.setAdapter(_game_adapter);

        _homePresenter.getFriendHistoric();

        _displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private void initSearch() {
        _search_recycler_view = (RecyclerView) findViewById(R.id.search_recyclerView);
        _search_coordinatorLayout = (CoordinatorLayout) findViewById(R.id.search_coordinatorLayout);
        _search_adapter = new SearchAdapter(this, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        _search_recycler_view.setLayoutManager(mLayoutManager);
        _search_recycler_view.setAdapter(_search_adapter);

        _searchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        _searchBar.setOnSearchActionListener(this);

        _client = new Client("OE27RXGRJS", "c2809c38f9e5ddfc6d71e38df80adce9");

        final Index index = _client.initIndex("global");

        EditText searchEdit = (EditText) _searchBar.findViewById(com.mancj.materialsearchbar.R.id.mt_editText);

        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0){
                    Query query = new Query(String.valueOf(charSequence));
                    index.searchAsync(query, _completionHandler);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    _search_coordinatorLayout.setVisibility(View.GONE);
                }
            }
        });

        _completionHandler = new CompletionHandler() {
            @Override
            public void requestCompleted(JSONObject content, AlgoliaException error) {
                try {
                    Log.d("json", String.valueOf(content.getJSONArray("hits")));

                    JSONArray jsonArray = content.getJSONArray("hits");
                    ArrayList<String> listdata = new ArrayList<String>();
                    if (jsonArray != null) {
                        for (int i=0;i<jsonArray.length();i++){
                            listdata.add(jsonArray.getString(i));
                        }
                    }
                    if (listdata.size() != 0) {
                        _search_coordinatorLayout.setVisibility(View.VISIBLE);
                    }
                    _search_adapter.set_searchList(listdata);
                    _search_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        _homePresenter.getProfile();

        String babyFootId = _manager._globalManager.select("babyFootSelected");
        if (babyFootId != null) {
            _homePresenter.getBabyFoot(babyFootId);
        }
    }

    @Override
    protected void onStop() {
        if (_drawerLayout != null) {
            _drawerLayout.closeDrawers();
        }
        super.onStop();
    }

    protected void onCreateDrawer() {
        _toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (_toolbar != null) {
            setSupportActionBar(_toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            _drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

            _drawerToggle = new ActionBarDrawerToggle(this, _drawerLayout, R.string.app_name, R.string.app_name) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }
            };
            _drawerToggle.syncState();

            _drawerLayout.addDrawerListener(_drawerToggle);

            _navigation = (NavigationView) findViewById(R.id.navigation_view);

            _navigation.inflateHeaderView(R.layout.navigation_header);

            _navigation.inflateMenu(R.menu.navigation);

            _navigation.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        this.onCreateDrawer();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_logout:
                _manager._tokenManager.delete();
                this.finish();
                Intent intent1 = new Intent(this, SignInSignUpActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (_drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {

    }

    @Override
    public void onButtonClicked(int i) {
        switch (i) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                _drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
    }

    public void setHeaderNavigation(UserEntity userEntity) {
        TextView name = (TextView) _navigation.getHeaderView(0).findViewById(R.id.user_profile_name);
        TextView pseudo = (TextView) _navigation.getHeaderView(0).findViewById(R.id.user_profile_pseudo);
        final ImageView profile = (ImageView) _navigation.getHeaderView(0).findViewById(R.id.photo_profile);

        if (userEntity != null){
            if (name != null && pseudo != null) {
                name.setText(userEntity.get_last_name() + " " + userEntity.get_first_name());
                pseudo.setText("@" + userEntity.get_pseudo());
            }

            String url_profile = userEntity.get_picture_url();
            this.getImageProfile(url_profile, profile);
        }
    }

    @Override
    public void setProfile(UserEntity userEntity) {
        _userEntity = userEntity;
        this.setHeaderNavigation(userEntity);
        _navigation.getMenu().clear();
        _navigation.inflateMenu(R.menu.navigation);
        _navigation.setNavigationItemSelectedListener(this);
    }

    public void getImageProfile(String url, ImageView photo) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(url, photo, _displayImageOptions);
    }


    @Override
    public void setBabyFoot(BabyFootEntity babyFootEntity) {
        _babyFootEntity = babyFootEntity;
        _homePresenter.getPub(babyFootEntity.get_bar_id());
    }

    @Override
    public void setPubs(PubEntity pubEntity) {
        _pubEntity = pubEntity;
        Intent intent = new Intent(this, BabyFootActivity.class);
        intent.putExtra("babyFootEntity", _babyFootEntity);
        intent.putExtra("pubEntity", _pubEntity);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatActionButton_scan:
                Intent intent = new Intent(this, DecoderQrCodeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getUserAndShow(String user_id) {
        _homePresenter.getUserAndShow(user_id);
    }

    @Override
    public void getPubAndShow(String pub_id) {
        _homePresenter.getPubAndShow(pub_id);
    }

    @Override
    public void startProfileActivity(FriendEntity friendEntity) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("friendEntity", friendEntity);
        this.startActivity(intent);
    }

    @Override
    public void startPubActivity(PubEntity pubEntity) {
        Intent intent = new Intent(this, PubActivity.class);
        intent.putExtra("pubEntity", pubEntity);
        this.startActivity(intent);
    }

    @Override
    public void getHistoric(String babyFootEntity_id) {}

    @Override
    public void addGame(String babyFootEntity_id) {}

    @Override
    public void setGameEntities(List<GameEntity> gameEntities) {
        if (!gameEntities.isEmpty()) {
            TextView empty_view = (TextView) findViewById(R.id.empty_view);
            empty_view.setVisibility(View.GONE);
        }
        _game_adapter.setGameEntities(gameEntities);
    }

    @Override
    public void shareGame(GameEntity gameEntity) {

        TeamEntity teamEntity = gameEntity.get_teams().get(0);
        TeamEntity teamEntity2 = gameEntity.get_teams().get(1);

        String winner;
        String loser;

        if (Objects.equals(gameEntity.get_winner(), teamEntity.get_id())) {
            winner = teamEntity.get_players().get(0).get_pseudo()+" & "+teamEntity.get_players().get(1).get_pseudo();
            loser = teamEntity2.get_players().get(0).get_pseudo()+" & "+teamEntity2.get_players().get(1).get_pseudo();
        }
        else {
            loser = teamEntity.get_players().get(0).get_pseudo()+" & "+teamEntity.get_players().get(1).get_pseudo();
            winner = teamEntity2.get_players().get(0).get_pseudo()+" & "+teamEntity2.get_players().get(1).get_pseudo();
        }

        String title = winner+" ont gagné contre "+loser;
        String description = "La fin du match s'est fini avec le score de " + gameEntity.get_scores().get(0) + "-"+gameEntity.get_scores().get(1);


        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentTitle(title)
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setContentDescription(description)
                .build();
        ShareDialog.show(this, content);
    }
}
