package Adapters;

import android.widget.Button;

import Fragments.BaseOnboardFragment;
import Fragments.Business;
import Fragments.Social;
import Fragments.Tech;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pager_Adapter extends FragmentStatePagerAdapter {
    private static int num_items = 3;
    public BaseOnboardFragment[] fragments;

    public Pager_Adapter(FragmentManager fm) {
        super(fm);
        fragments = new BaseOnboardFragment[]
                {
                    Business.newInstance(0),
                    Social.newInstance(1),
                    Tech.newInstance(2)
                };
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return num_items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[0].getClass().getName();
    }
}
