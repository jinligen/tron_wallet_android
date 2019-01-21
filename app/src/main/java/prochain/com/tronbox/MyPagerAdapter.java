package prochain.com.tronbox;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by alex on 2019/1/21.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mfragmentList;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mfragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
}
