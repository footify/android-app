package eu.epitech.croucour.footify.BabyFoot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import eu.epitech.croucour.footify.BabyFoot.TabLayout.ViewPagerBabyFootAdapter;
import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Game.GameActivity;
import eu.epitech.croucour.footify.Game.GameAdapter;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.Profile.FriendAdapter;
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

        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);

        _viewPagerAdapter = new ViewPagerBabyFootAdapter(getSupportFragmentManager());
        _viewPagerAdapter.addTab(ViewPagerBabyFootAdapter.TabFragment.newInstance("1", this, this, this), "BabyFoot");
        _viewPagerAdapter.addTab(ViewPagerBabyFootAdapter.TabFragment.newInstance("2", this, this, this), "Classement");

        _viewPager.setOffscreenPageLimit(2);

        _viewPager.setAdapter(_viewPagerAdapter);

        _tabLayout.setupWithViewPager(_viewPager);

        ViewPagerBabyFootAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(0);
        fragment.setBabyFoot(_babyFootEntity);
        fragment.setPub(_pubEntity);

    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);

        _toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        _babyFootPresenter.getAbo();
//        _babyFootPresenter.getSpredCasts(1);
//        _babyFootPresenter.getSpredCasts(0);
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
        ViewPagerBabyFootAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(0);
        fragment.setGameEntities(gameEntities);
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
}
