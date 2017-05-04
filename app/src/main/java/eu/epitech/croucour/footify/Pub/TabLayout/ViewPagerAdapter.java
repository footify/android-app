package eu.epitech.croucour.footify.Pub.TabLayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Game.GameAdapter;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.Pub.BabyFootAdapter;
import eu.epitech.croucour.footify.Pub.IPubView;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.Ranking.IRankingView;
import eu.epitech.croucour.footify.Ranking.RankingAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by roucou_c on 21/09/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<TabFragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitles.get(position);
    }

    public void addTab(TabFragment tabFragment, String title) {
        fragmentList.add(tabFragment);
        fragmentTitles.add(title);
    }

    public static class TabFragment extends Fragment implements View.OnClickListener {

        private String step;
        public IPubView _iPubView;

        /**
         * Tab 1
         */
        private CoordinatorLayout _coordinatorLayoutScan;
        private FloatingActionButton _floatingActionScan;

        private TextView _pub_name;
        private PubEntity _pubEntity;
        private TextView _pub_address;
        private RecyclerView _baby_recycler_view;
        private BabyFootAdapter _baby_adapter;
        private IGameView _iGameView;
        private RecyclerView _feeds_recycler_view;
        private GameAdapter _feeds_adapter;
        private ImageView _pub_photo;
        private RecyclerView _pub_ranking_recycler_view;
        private RankingAdapter _ranking_adapter;
        private IRankingView _iRankingView;


        public static TabFragment newInstance(String step, IPubView ipubView, IGameView iGameView, IRankingView iRankingView) {
            TabFragment fragment = new TabFragment();
            fragment.step = step;
            fragment._iPubView = ipubView;
            fragment._iGameView = iGameView;
            fragment._iRankingView = iRankingView;
            return fragment;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = null;

            if (step != null) {
                switch (step) {
                    case "1":
                        rootView = inflater.inflate(R.layout.tab_1, container, false);

                        _pub_name = (TextView) rootView.findViewById(R.id.pub_name);
                        _pub_address = (TextView) rootView.findViewById(R.id.pub_address);
                        _pub_photo = (ImageView) rootView.findViewById(R.id.pub_photo);
                        if (_pubEntity != null) {
                            _pub_name.setText(_pubEntity.get_name());
                            String address = _pubEntity.get_street_number()+ ", " + _pubEntity.get_street_name() + " " + _pubEntity.get_city() + " " + _pubEntity.get_zip_code();
                            _pub_address.setText(address);
                            _iPubView.setImage(_pubEntity.get_picture_url(), _pub_photo);
                        }

                        _baby_recycler_view = (RecyclerView) rootView.findViewById(R.id.baby_recycler_view);

                        _baby_adapter = new BabyFootAdapter(getApplicationContext(), _iPubView);
                        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
                        _baby_recycler_view.setLayoutManager(mLayoutManager3);
                        _baby_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        _baby_recycler_view.setAdapter(_baby_adapter);

                        break;
                    case "2":
                        rootView = inflater.inflate(R.layout.tab_2, container, false);

                        _feeds_recycler_view = (RecyclerView) rootView.findViewById(R.id.feeds_recycler_view);

                        _feeds_adapter = new GameAdapter(getApplicationContext(), _iGameView);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        _feeds_recycler_view.setLayoutManager(mLayoutManager);
                        _feeds_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        _feeds_recycler_view.setAdapter(_feeds_adapter);

                        break;
                    case "3":
                        rootView = inflater.inflate(R.layout.ranking, container, false);

                        _pub_ranking_recycler_view = (RecyclerView) rootView.findViewById(R.id.baby_ranking_recycler_view);

                        _ranking_adapter = new RankingAdapter(getApplicationContext(), _iRankingView);
                        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext());
                        _pub_ranking_recycler_view.setLayoutManager(mLayoutManager2);
                        _pub_ranking_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        _pub_ranking_recycler_view.setAdapter(_ranking_adapter);

                        if (_pubEntity != null) {
                            _iRankingView.getRanking(_pubEntity.get_id());
                        }
                        break;
                }
            }
            return rootView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
            }

        }

        public void setPub(PubEntity pubEntity) {
            _pubEntity = pubEntity;
        }

        public void setBabyFoots(List<BabyFootEntity> babyFootEntities) {
            _baby_adapter.set_babyFootEntities(babyFootEntities);
        }

        public void setFeeds(List<GameEntity> gameEntities) {
            _feeds_adapter.setGameEntities(gameEntities);
        }

        public void setPubRanking(List<LigueRankingEntity> ligueRankingEntities) {
            _ranking_adapter.set_ligueRankingEntities(ligueRankingEntities);
        }
    }
}
