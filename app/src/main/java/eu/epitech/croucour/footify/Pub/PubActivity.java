package eu.epitech.croucour.footify.Pub;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;

import eu.epitech.croucour.footify.DAO.Manager;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Entities.TokenEntity;
import eu.epitech.croucour.footify.Pub.TabLayout.ViewPagerAdapter;
import eu.epitech.croucour.footify.R;

/**
 * Created by croucour on 29/04/17.
 */

public class PubActivity extends AppCompatActivity implements IPubView, ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener{


    private Manager _manager;
    private PubPresenter _pubPresenter;
    private TabLayout _tabLayout;
    private ViewPager _viewPager;
    private ViewPagerAdapter _viewPagerAdapter;
    private BabyFootEntity _babyFootEntity;
    private PubEntity _pubEntity;
    private Toolbar _toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub);

        _manager = Manager.getInstance(getApplicationContext());

        _babyFootEntity = (BabyFootEntity) getIntent().getSerializableExtra("babyEntity");
        _pubEntity = (PubEntity) getIntent().getSerializableExtra("pubEntity");


        TokenEntity tokenEntity = _manager._tokenManager.select();

        _pubPresenter = new PubPresenter(this, _manager, tokenEntity);

        _tabLayout = (TabLayout) findViewById(R.id.tabs);
        _viewPager = (ViewPager) findViewById(R.id.viewpager);

        _viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("1", this), "En cours");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("2", this), "Historique");
        _viewPagerAdapter.addTab(ViewPagerAdapter.TabFragment.newInstance("3", this), "Classement");

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
//        _pubPresenter.getAbo();
//        _pubPresenter.getSpredCasts(1);
//        _pubPresenter.getSpredCasts(0);
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



}
