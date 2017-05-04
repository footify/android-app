package eu.epitech.croucour.footify.BabyFoot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;
import java.util.Objects;

import eu.epitech.croucour.footify.BabyFoot.TabLayout.ViewPagerBabyFootAdapter;
import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.TeamEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Game.GameActivity;
import eu.epitech.croucour.footify.Game.GameAdapter;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.Pub.PubActivity;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.Ranking.IRankingView;

/**
 * Created by croucour on 29/04/17.
 */

public class BabyFootActivity extends AppCompatActivity implements IBabyFootView, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener, IGameView, IRankingView {


    private Manager _manager;
    private BabyFootPresenter _babyFootPresenter;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private ViewPagerBabyFootAdapter _viewPagerAdapter;
    private BabyFootEntity _babyFootEntity;
    private PubEntity _pubEntity;
    private Toolbar _toolbar;
    private DisplayImageOptions _displayImageOptions;
    private TextView _babyfoot_name;
    private TextView _babyfoot_manufacter;
    private ImageView _babyfoot_picture;
    private TextView _babyfoot_pub_name;
    private RecyclerView _baby_historic_recycler_view;
    private GameAdapter _game_adapter;
    private FloatingActionButton _game_floatingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.babyfoot);

        _manager = Manager.getInstance(getApplicationContext());

        _babyFootEntity = (BabyFootEntity) getIntent().getSerializableExtra("babyFootEntity");
        _pubEntity = (PubEntity) getIntent().getSerializableExtra("pubEntity");


        TokenEntity tokenEntity = _manager._tokenManager.select();

        _displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        _babyFootPresenter = new BabyFootPresenter(this, _manager, tokenEntity);

//        _tabLayout = (TabLayout) findViewById(R.id.tabs);
//        _viewPager = (ViewPager) findViewById(R.id.viewpager);
//
//        _viewPagerAdapter = new ViewPagerBabyFootAdapter(getSupportFragmentManager());
//        _viewPagerAdapter.addTab(ViewPagerBabyFootAdapter.TabFragment.newInstance("1", this, this, this), "BabyFoot");
//        _viewPagerAdapter.addTab(ViewPagerBabyFootAdapter.TabFragment.newInstance("2", this, this, this), "Classement");
//
//        _viewPager.setOffscreenPageLimit(2);
//
//        _viewPager.setAdapter(_viewPagerAdapter);
//
//        _tabLayout.setupWithViewPager(_viewPager);

//        ViewPagerBabyFootAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(0);
//        fragment.setBabyFoot(_babyFootEntity);
//        fragment.setPub(_pubEntity);

        _babyfoot_name = (TextView) findViewById(R.id.babyfoot_name);
        _babyfoot_manufacter = (TextView) findViewById(R.id.babyfoot_manufacturer);
        _babyfoot_picture = (ImageView) findViewById(R.id.babyfoot_photo);
        _babyfoot_pub_name = (TextView) findViewById(R.id.babyfoot_pub_name);

        populatePudAndBaby();
        _baby_historic_recycler_view = (RecyclerView) findViewById(R.id.baby_historic_recycler_view);

        _game_adapter = new GameAdapter(getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
        _baby_historic_recycler_view.setLayoutManager(mLayoutManager3);
        _baby_historic_recycler_view.setItemAnimator(new DefaultItemAnimator());
        _baby_historic_recycler_view.setAdapter(_game_adapter);

        _game_floatingAction = (FloatingActionButton) findViewById(R.id.game_floatActionButton);

        _game_floatingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame(_babyFootEntity.get_id());
            }
        });
        getHistoric(_babyFootEntity.get_id());

    }

    private void populatePudAndBaby() {
        if (_pubEntity != null) {
            _babyfoot_pub_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPubActivity();
                }
            });
            _babyfoot_pub_name.setText(_pubEntity.get_name());
        }
        if (_babyFootEntity != null) {
            _babyfoot_name.setText(_babyFootEntity.get_name());
            _babyfoot_manufacter.setText(_babyFootEntity.get_manufacturer());
            setImage(_babyFootEntity.get_picture_url(), _babyfoot_picture);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BabyFoot");

    }

    @Override
    protected void onResume() {
        super.onResume();

        _babyFootPresenter.getBabyFoot(_babyFootEntity.get_id());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
        _manager._globalManager.deleteGlobal("babyFootSelected");
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    @Override
    public void setImage(String picture_url, ImageView imageView) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(picture_url, imageView, _displayImageOptions);
    }

    @Override
    public void setHistoric(List<GameEntity> gameEntities) {
        _game_adapter.setGameEntities(gameEntities);
    }

    @Override
    public void getHistoric(String babyFootEntity_id) {
        _babyFootPresenter.getHistoric(babyFootEntity_id);
    }

    @Override
    public void addGame(String babyFootEntity_id) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("babyFootEntity", _babyFootEntity);
        startActivity(intent);
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

    @Override
    public void getRanking(String babyFootEntity_id) {
        _babyFootPresenter.getRanking(babyFootEntity_id);
    }

    @Override
    public void setRanking(List<LigueRankingEntity> ligueRankingEntities) {
        ViewPagerBabyFootAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(1);
        fragment.setLigueRankingEntities(ligueRankingEntities);
    }

    @Override
    public void setBabyFoot(BabyFootEntity babyFootEntity) {
        _babyFootPresenter.getPub(babyFootEntity.get_bar_id());
        _babyFootEntity = babyFootEntity;
    }

    @Override
    public void setPub(PubEntity pubEntity) {
        _pubEntity = pubEntity;
        populatePudAndBaby();
    }

    @Override
    public void startPubActivity() {
        Intent intent = new Intent(this, PubActivity.class);
        intent.putExtra("pubEntity", _pubEntity);
        startActivity(intent);
    }
}
