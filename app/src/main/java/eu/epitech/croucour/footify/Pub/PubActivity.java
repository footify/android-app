package eu.epitech.croucour.footify.Pub;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

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
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.Pub.TabLayout.ViewPagerAdapter;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.Ranking.IRankingView;

/**
 * Created by croucour on 29/04/17.
 */

public class PubActivity extends AppCompatActivity implements IPubView, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener, IGameView, IRankingView {


    private Manager _manager;
    private PubPresenter _pubPresenter;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private ViewPagerAdapter _viewPagerAdapter;
    private BabyFootEntity _babyFootEntity;
    private PubEntity _pubEntity;
    private Toolbar _toolbar;
    private DisplayImageOptions _displayImageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub);

        _manager = Manager.getInstance(getApplicationContext());

        _pubEntity = (PubEntity) getIntent().getSerializableExtra("pubEntity");

        TokenEntity tokenEntity = _manager._tokenManager.select();

        _pubPresenter = new PubPresenter(this, _manager, tokenEntity);

        _displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);

        _viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("1", this, this, this), "Bar");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("2", this, this, this), "Historique");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("3", this, this, this), "Classement");

        _viewPager.setOffscreenPageLimit(3);

        _viewPager.setAdapter(_viewPagerAdapter);

        _tabLayout.setupWithViewPager(_viewPager);

        ViewPagerAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(0);
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
        _pubPresenter.getBabyFoots(_pubEntity.get_id());
        _pubPresenter.getHistoric(_pubEntity.get_id());
        // get classement
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void finish() {
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
    public void setBabyFoots(List<BabyFootEntity> babyFootEntities) {
        ViewPagerAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(0);
        fragment.setBabyFoots(babyFootEntities);
    }

    @Override
    public void setFeeds(List<GameEntity> gameEntities) {
        ViewPagerAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(1);
        fragment.setFeeds(gameEntities);
    }

    @Override
    public void getHistoric(String babyFootEntity_id) {}

    @Override
    public void addGame(String babyFootEntity_id) {}

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
    public void getRanking(String pub_id) {
        _pubPresenter.getPubRanking(pub_id);
    }

    @Override
    public void setPubRanking(List<LigueRankingEntity> ligueRankingEntities) {
        ViewPagerAdapter.TabFragment  fragment = _viewPagerAdapter.getItem(2);
        fragment.setPubRanking(ligueRankingEntities);
    }
}
