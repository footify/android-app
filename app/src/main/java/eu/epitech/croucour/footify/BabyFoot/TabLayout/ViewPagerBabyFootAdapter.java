package eu.epitech.croucour.footify.BabyFoot.TabLayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
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

import eu.epitech.croucour.footify.BabyFoot.IBabyFootView;
import eu.epitech.croucour.footify.Entities.BabyFootEntity;
import eu.epitech.croucour.footify.Entities.GameEntity;
import eu.epitech.croucour.footify.Entities.LigueRankingEntity;
import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Game.GameAdapter;
import eu.epitech.croucour.footify.Game.IGameView;
import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.Ranking.IRankingView;
import eu.epitech.croucour.footify.Ranking.RankingAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by roucou_c on 21/09/2016.
 */
public class ViewPagerBabyFootAdapter extends FragmentPagerAdapter {

    private List<TabFragment> fragmentList = new ArrayList<>();
    private List<String> fragmentTitles = new ArrayList<>();

    public ViewPagerBabyFootAdapter(FragmentManager fm) {
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
        public IBabyFootView _iBabyFootView;

        /**
         * Tab 1
         */
        private CoordinatorLayout _coordinatorLayoutScan;
        private FloatingActionButton _floatingActionScan;

        private PubEntity _pubEntity;
        private BabyFootEntity _babyFootEntity;
        private TextView _babyfoot_name;
        private TextView _babyfoot_manufacter;
        private ImageView _babyfoot_picture;
        private TextView _babyfoot_pub_name;
        private IGameView _iGameView;
        private RecyclerView _baby_historic_recycler_view;
        private GameAdapter _game_adapter;
        private FloatingActionButton _game_floatingAction;
        private RecyclerView _baby_ranking_recycler_view;
        private IRankingView _iRankingView;
        private RankingAdapter _ranking_adapter;
        private SwipeRefreshLayout _historic_swipeRefreshLayout;


        public static TabFragment newInstance(String step, IBabyFootView iBabyFootView, IGameView iGameView, IRankingView iRankingView) {
            TabFragment fragment = new TabFragment();
            fragment.step = step;
            fragment._iBabyFootView = iBabyFootView;
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
                        rootView = inflater.inflate(R.layout.babyfoot, container, false);

                        _babyfoot_name = (TextView) rootView.findViewById(R.id.babyfoot_name);
                        _babyfoot_manufacter = (TextView) rootView.findViewById(R.id.babyfoot_manufacturer);
                        _babyfoot_picture = (ImageView) rootView.findViewById(R.id.babyfoot_photo);
                        _babyfoot_pub_name = (TextView) rootView.findViewById(R.id.babyfoot_pub_name);

                        if (_pubEntity != null) {
                            _babyfoot_pub_name.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    _iBabyFootView.startPubActivity();
                                }
                            });
                            _babyfoot_pub_name.setText(_pubEntity.get_name());
                        }
                        if (_babyFootEntity != null) {
                            _babyfoot_name.setText(_babyFootEntity.get_name());
                            _babyfoot_manufacter.setText(_babyFootEntity.get_manufacturer());
                            _iBabyFootView.setImage(_babyFootEntity.get_picture_url(), _babyfoot_picture);
                        }

                        _baby_historic_recycler_view = (RecyclerView) rootView.findViewById(R.id.baby_historic_recycler_view);

                        _game_adapter = new GameAdapter(getApplicationContext(), _iGameView);
                        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(getApplicationContext());
                        _baby_historic_recycler_view.setLayoutManager(mLayoutManager3);
                        _baby_historic_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        _baby_historic_recycler_view.setAdapter(_game_adapter);

//                        _historic_swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);
//                        _historic_swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                            @Override
//                            public void onRefresh() {
//                                _iGameView.getHistoric(_pubEntity.get_id());
//                            }
//                        });

                        _game_floatingAction = (FloatingActionButton) rootView.findViewById(R.id.game_floatActionButton);

                        _game_floatingAction.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                _iGameView.addGame(_babyFootEntity.get_id());
                            }
                        });
                        _iGameView.getHistoric(_babyFootEntity.get_id());

                        break;
                    case "2":
                        rootView = inflater.inflate(R.layout.ranking, container, false);

                        _baby_ranking_recycler_view = (RecyclerView) rootView.findViewById(R.id.baby_ranking_recycler_view);

                        _ranking_adapter = new RankingAdapter(getApplicationContext(), _iRankingView);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        _baby_ranking_recycler_view.setLayoutManager(mLayoutManager);
                        _baby_ranking_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        _baby_ranking_recycler_view.setAdapter(_ranking_adapter);

                        if (_babyFootEntity != null) {
                            _iRankingView.getRanking(_babyFootEntity.get_id());
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

        public void setBabyFoot(BabyFootEntity babyFootEntity) {
            _babyFootEntity = babyFootEntity;
        }

        public void setGameEntities(List<GameEntity> gameEntities) {
            _game_adapter.setGameEntities(gameEntities);
        }

        public void setLigueRankingEntities(List<LigueRankingEntity> ligueRankingEntities) {
            _ranking_adapter.set_ligueRankingEntities(ligueRankingEntities);
        }
    }
}
