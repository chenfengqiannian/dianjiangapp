package com.dianjiang.hyjipotou2.dianjiangapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * Created by hyjipotou2 on 16/3/13.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT=4;
    MyFragment1 myFragment1=null;
    MyFragment2 myFragment2=null;
    MyFragment3 myFragment3=null;
    MyFragment4 myFragment4=null;

    public MyFragmentPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
        myFragment1=new MyFragment1();
        myFragment2=new MyFragment2();
        myFragment3=new MyFragment3();
        myFragment4=new MyFragment4();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragment3;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = myFragment4;
                break;
        }
        return fragment;
    }


    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
