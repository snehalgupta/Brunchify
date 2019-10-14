package Adapters;

import Fragments.Business;
import Fragments.Social;
import Fragments.Tech;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Pager_Adapter extends FragmentStatePagerAdapter {
    private static int num_items = 3;

    public Pager_Adapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Business.newInstance("0");
            case 1:
                return Social.newInstance("1");
            case 2:
                return Tech.newInstance("2");
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return num_items;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Business";
            case 1:
                return "Social";
            case 2:
                return "Tech";
            default:
                return null;
        }
    }
}
