package com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.dianjiang.hyjipotou2.dianjiangapp.R;
import java.util.ArrayList;

/**
 * Created by hyjipotou2 on 16/4/14.
 */
public class sFragment extends Fragment implements XListView.IXListViewListener{

    private XListView xListView=null;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;



    /*public static sFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        sFragment pageFragment = new sFragment();
        pageFragment.setArguments(args);
        return pageFragment;*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gongchengtabview, container, false);

        xListView=(XListView)view.findViewById(R.id.gongcheng_list);
        geneItems();
        initViews();

        return view;
    }

    private void initViews(){


        String[] arr1={"wori","nidaye"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.testitem,R.id.testText,arr1);
        xListView.setAdapter(adapter);
        xListView.setXListViewListener(this);
        mHandler = new Handler();
    }

    private void geneItems() {
        for (int i = 0; i != 5; ++i) {
            items.add("refresh cnt " + (++start));
        }
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    @Override
    public void onRefresh() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();

                //数据
                String[] arr1={"wori","nidaye"};
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.testitem,R.id.testText,arr1);
                xListView.setAdapter(adapter);

                onLoad();
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("LOL","jiaodian");
    }
}
