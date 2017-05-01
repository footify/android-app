package eu.epitech.croucour.footify.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.algolia.search.saas.Query;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.DecoderQrCode.DecoderQrCodeActivity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Entities.UserEntity;
import eu.epitech.croucour.footify.Home.TabLayout.ViewPagerAdapter;
import eu.epitech.croucour.footify.R;

/**
 * Created by croucour on 29/04/17.
 */

public class HomeActivity extends AppCompatActivity implements IHomeView, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener, NavigationView.OnNavigationItemSelectedListener, MaterialSearchBar.OnSearchActionListener {

    private Manager _manager;
    private UserEntity _userEntity;
    private HomePresenter _homePresenter;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private ViewPagerAdapter _viewPagerAdapter;
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

        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);

        _viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("1", this), "En cours");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("2", this), "Historique");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("3", this), "Classement");

        _viewPager.setOffscreenPageLimit(3);

        _viewPager.setAdapter(_viewPagerAdapter);

        _tabLayout.setupWithViewPager(_viewPager);

        initSearch();



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

        _client = new Client("KGZYQKI2SD", "a8583e100dbd3bb6e5a64d76462d1f5b");

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
//        _homePresenter.getAbo();
//        _homePresenter.getSpredCasts(1);
//        _homePresenter.getSpredCasts(0);
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

            _navigation.getMenu().clear();
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
        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        _viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

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

    @Override
    public void startScan() {
        Intent intent = new Intent(HomeActivity.this, DecoderQrCodeActivity.class);
        startActivity(intent);
    }
}
