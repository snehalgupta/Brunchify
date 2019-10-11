package Adapters;

import Fragments.Interests;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Pager_Adapter extends FragmentPagerAdapter {
    private static int num_items = 3;

    public Pager_Adapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return Interests.newInstance("0");
            case 1:
                return Interests.newInstance("1");
            case 2:
                return Interests.newInstance("2");
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return num_items;
    }
}
