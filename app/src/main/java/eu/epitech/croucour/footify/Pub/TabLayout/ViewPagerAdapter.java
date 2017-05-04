package eu.epitech.croucour.footify.Pub.TabLayout;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.epitech.croucour.footify.Entities.PubEntity;
import eu.epitech.croucour.footify.Home.IHomeView;
import eu.epitech.croucour.footify.Pub.IPubView;
import eu.epitech.croucour.footify.R;

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

        private TextView _tab1_name;
        private PubEntity _pubEntity;


        public static TabFragment newInstance(String step, IPubView ipubView) {
            TabFragment fragment = new TabFragment();
            fragment.step = step;
            fragment._iPubView = ipubView;
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

                        _tab1_name = (TextView) rootView.findViewById(R.id.tab_1_name);

                        if (_pubEntity != null) {
                            _tab1_name.setText(_pubEntity.get_name());
                        }
                        break;
                    case "2":
                        rootView = inflater.inflate(R.layout.tab_2, container, false);
                        break;
                    case "3":
                        rootView = inflater.inflate(R.layout.tab_3, container, false);
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
    }
}
