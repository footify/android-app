package eu.epitech.croucour.footify.Home.TabLayout;

import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.epitech.croucour.footify.R;
import eu.epitech.croucour.footify.Home.IHomeView;

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

    public static class TabFragment extends Fragment {

        private String step;
        public IHomeView _iHomeView;
        public static TabFragment newInstance(String step, IHomeView iHomeView) {
            TabFragment fragment = new TabFragment();
            fragment.step = step;
            fragment._iHomeView = iHomeView;
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
                        break;
                    case "2":
                        rootView = inflater.inflate(R.layout.tab_1, container, false);
                        break;
                    case "3":
                        rootView = inflater.inflate(R.layout.tab_1, container, false);
                        break;
                }
            }
            return rootView;
        }
    }


}
