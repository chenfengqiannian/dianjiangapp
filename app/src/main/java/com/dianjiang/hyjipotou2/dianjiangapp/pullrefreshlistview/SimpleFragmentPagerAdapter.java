package com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dianjiang.hyjipotou2.dianjiangapp.myfactoryFragment;

import java.util.ArrayList;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter{

        protected int PAGE_COUNT ;
        protected String tabTitles[] = new String[]{"已发布工程","未发布工程","招标中工程","已完工工程"};
        protected Context context;
        protected ArrayList<myfactoryFragment> fragmentlist;

        public SimpleFragmentPagerAdapter(FragmentManager fm,Context context,ArrayList<myfactoryFragment> fragmentlist) {
            super(fm);
            this.context = context;
            this.fragmentlist=fragmentlist;
            this.PAGE_COUNT=tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentlist.get(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
}
