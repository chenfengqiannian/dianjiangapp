package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView;
import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView.IXListViewListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFragment2.OnFragmentInteractionListener1} interface
 * to handle interaction events.
 * Use the {@link MyFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment2 extends Fragment implements XListView.IXListViewListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String URL="http://192.168.191.1:8000";
    public static final String USERAPI="/userapi/";
    public static final String GONGCHENGAPI="/gongchengapi/";

    private static final int LEVEL=1;
    private static final int PRICE=2;
    private static final int PINGJIA=3;

    private static final int JIANGXU=0;
    private static final int SHENGXU=1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View viewFragment;
    private XListView xListView=null;
    public dianjiangItemAdapter mAdapter;

    ///////////////////
    private RelativeLayout level;
    private RelativeLayout price;
    private RelativeLayout pingjia;
    private RelativeLayout option;
    private TextView level_text;
    private TextView price_text;
    private TextView pingjia_text;
    private ImageView level_img;
    private ImageView price_img;
    private ImageView pingjia_img;


    private int level_state=JIANGXU;
    private int price_state=SHENGXU;
    private int pingjia_state=SHENGXU;

    private Handler mhandler=new Handler();

    private int option_state=LEVEL;


    public DataFragment dataFragment=DataFragment.getInstance();
    private ArrayList<LinkedTreeMap> linkedTreeMapArrayList=new ArrayList<>();



    private OnFragmentInteractionListener1 mListener;

    public MyFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment2 newInstance(String param1, String param2) {
        MyFragment2 fragment = new MyFragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewFragment=inflater.inflate(R.layout.fragment_my_fragment2, null);
        initViews();
        setTopOptionListener();

        return viewFragment;
    }

    private void initViews(){
        xListView=(XListView) viewFragment.findViewById(R.id.dianjiang_mylist);
        level= (RelativeLayout) viewFragment.findViewById(R.id.dianjiang_level);
        level_text= (TextView) viewFragment.findViewById(R.id.leveltext);
        level_img=(ImageView)viewFragment.findViewById(R.id.levelimg);
        price= (RelativeLayout) viewFragment.findViewById(R.id.dianjiang_rixin);
        price_text=(TextView)viewFragment.findViewById(R.id.salarytext);
        price_img=(ImageView)viewFragment.findViewById(R.id.salaryimg);
        pingjia= (RelativeLayout) viewFragment.findViewById(R.id.dianjiang_pingjia);
        pingjia_text=(TextView)viewFragment.findViewById(R.id.pingjiatext);
        pingjia_img=(ImageView)viewFragment.findViewById(R.id.pingjiaimg);
        option=(RelativeLayout)viewFragment.findViewById(R.id.dianjiang_shaixuan);

        //初始化LISTVIEW adapter
        mAdapter=new dianjiangItemAdapter(this.getActivity(),dataFragment.dianjiangItemBeans);
        xListView.setAdapter(mAdapter);
        xListView.setXListViewListener(this);

        initdata();
    }

    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View tview) {
        if (mListener != null) {
            mListener.onFragmentInteraction1(tview);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener1) {
            mListener = (OnFragmentInteractionListener1) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //刷新       /////////////////////////////////////////////////
    @Override
    public void onRefresh() {

        GetHttp();

    }

    public void setTopOptionListener(){

        Resources resources=(Resources)getActivity().getBaseContext().getResources();
        final ColorStateList main_color=(ColorStateList)resources.getColorStateList(R.color.main_color);
        final ColorStateList non_color=(ColorStateList)resources.getColorStateList(R.color.black_color);

        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                option_state = LEVEL;

                if (level_state == JIANGXU) {
                    level_state = SHENGXU;
                    level_img.setImageResource(R.drawable.shengxu);
                    myComparator comparator = new myComparator(myComparator.LEVEL_SHENGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();

                } else {
                    level_state = JIANGXU;
                    level_img.setImageResource(R.drawable.jiangxu);
                    myComparator comparator = new myComparator(myComparator.LEVEL_JIANGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();
                }

                //改变颜色
                level_text.setTextColor(main_color);
                price_text.setTextColor(non_color);
                pingjia_text.setTextColor(non_color);
                price_img.setImageResource(R.drawable.paixu);
                pingjia_img.setImageResource(R.drawable.paixu);
                pingjia_state = SHENGXU;
                price_state = SHENGXU;
            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                option_state = PRICE;

                if (price_state == JIANGXU) {
                    price_state = SHENGXU;
                    price_img.setImageResource(R.drawable.shengxu);
                    myComparator comparator = new myComparator(myComparator.PRICE_SHENGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();
                } else {
                    price_state = JIANGXU;
                    price_img.setImageResource(R.drawable.jiangxu);
                    myComparator comparator = new myComparator(myComparator.PRICE_JIANGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();
                }

                //改变颜色
                level_text.setTextColor(non_color);
                price_text.setTextColor(main_color);
                pingjia_text.setTextColor(non_color);
                level_img.setImageResource(R.drawable.paixu);
                pingjia_img.setImageResource(R.drawable.paixu);
                pingjia_state = SHENGXU;
                level_state = SHENGXU;
            }
        });

        pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                option_state = PINGJIA;

                if (pingjia_state == JIANGXU) {
                    pingjia_state = SHENGXU;
                    pingjia_img.setImageResource(R.drawable.shengxu);
                    myComparator comparator = new myComparator(myComparator.PINGJIA_SHENGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();
                } else {
                    pingjia_state = JIANGXU;
                    pingjia_img.setImageResource(R.drawable.jiangxu);
                    myComparator comparator = new myComparator(myComparator.PINGJIA_JIANGXU);
                    Collections.sort(dataFragment.dianjiangItemBeans, comparator);
                    mAdapter.notifyDataSetChanged();
                }

                //改变颜色
                level_text.setTextColor(non_color);
                price_text.setTextColor(non_color);
                pingjia_text.setTextColor(main_color);
                level_img.setImageResource(R.drawable.paixu);
                price_img.setImageResource(R.drawable.paixu);
                price_state = SHENGXU;
                level_state = SHENGXU;
            }
        });

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //
                dataFragment.dianjiangItemBean = dataFragment.dianjiangItemBeans.get(position - 1);

                Intent intent = new Intent(getActivity(), dianjiangItemActivity.class);
                startActivity(intent);
            }
        });

        DataFragment fragment=DataFragment.getInstance();

        //来回发消息
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataFragment.mhandler1.sendEmptyMessage(0x2333);
            }
        });

        dataFragment.mhandler2=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0x1111){
                    //在这里执行具体筛选

                    //工种遍历
                    if (!dataFragment.gongzhong.equals("点击选择工种")){
                        for (int i=0;i<dataFragment.dianjiangItemBeans.size();i++){
                            if (dataFragment.dianjiangItemBeans.get(i).gongzhong!=dataFragment.gongzhong){
                                dataFragment.dianjiangItemBeans.remove(i);
                                i--;
                            }
                        }
                    }
                    //标签遍历
                    if (!dataFragment.biaoqian.equals("")){
                        for (int i=0;i<dataFragment.dianjiangItemBeans.size();i++){
                            if (dataFragment.dianjiangItemBeans.get(i).biaoqian!=dataFragment.biaoqian){
                                dataFragment.dianjiangItemBeans.remove(i);
                                i--;
                            }
                        }
                    }
                    //地区遍历
                    if (dataFragment.sheng_!=null && dataFragment.shi_!=null){
                        for (int i=0;i<dataFragment.dianjiangItemBeans.size();i++){
            if(dataFragment.dianjiangItemBeans.get(i).diqu==null) {
                continue;
            }               if (dataFragment.dianjiangItemBeans.get(i).diqu.split(",").length>=2)
                            {
                                if (!dataFragment.dianjiangItemBeans.get(i).diqu.split(",")[1].equals(dataFragment.shi_)){
                                    dataFragment.dianjiangItemBeans.remove(i);
                                    i--;
                                }
                        }
                           else {dataFragment.dianjiangItemBeans.remove(i);
                            i--;}

                        }
                    }

                    //赋值

                    mAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener1 {
        // TODO: Update argument type and name
        void onFragmentInteraction1(View tview);
    }

    public void GetHttp(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("job", "false")
                .build()
                .execute(new mCallBack<Object>(this) {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response");
                        String string = response.body().string();

                        Object ps = new Gson().fromJson(string, new TypeToken<Object>() {
                        }.getType());
                        return ps;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {
                        DataFragment dataFragment=DataFragment.getInstance();
                        dataFragment.gongjiang_data= (ArrayList<LinkedTreeMap<String, Object>>) response;
                        linkedTreeMapArrayList = (ArrayList<LinkedTreeMap>) response;
                        dataFragment.dianjiangItemBeans.clear();
                        dianjiangItemBean bean;

                        for (LinkedTreeMap treemap : linkedTreeMapArrayList
                                ) {
                            //URI
                            ArrayList<String> arrayList;
                            String string;
                            Uri uri=null;
                            arrayList = (ArrayList<String>) treemap.get("touxiang");
                            if (!arrayList.isEmpty()){
                                string = arrayList.get(arrayList.size() - 1);
                                uri = mytool.UriFromSenge(string);
                            }
                            String phone=(String)treemap.get("phone");
                            String name = (String) treemap.get("xingming");
                            String gongzhong = (String) treemap.get("gongzhong");
                            Double price = (Double) treemap.get("rixin");
                            Double level = (Double) treemap.get("dengji");
                            bean = new dianjiangItemBean(uri, name, gongzhong, level, price,phone);
                            bean.biaoqian=(String)treemap.get("biaoqian");
                            bean.pingjia= (Double) treemap.get("pingjiaxingji");
                            dataFragment.dianjiangItemBeans.add(0, bean);
                        }

                        //dianjiangItemBean bean=new dianjiangItemBean(linkedTreeMap.get("tupian"))
                        //dataFragment.dianjiangItemBeans.add()
                        switch (option_state){
                            case LEVEL:
                                if (level_state==JIANGXU){
                                myComparator comparator=new myComparator(myComparator.LEVEL_JIANGXU);
                                Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                            }else {
                                    myComparator comparator=new myComparator(myComparator.LEVEL_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;

                            case PRICE:
                                if (price_state==JIANGXU){
                                    myComparator comparator=new myComparator(myComparator.PRICE_JIANGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }else {
                                    myComparator comparator=new myComparator(myComparator.PRICE_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;

                            case PINGJIA:
                                if (pingjia_state==JIANGXU){
                                    myComparator comparator=new myComparator(myComparator.PINGJIA_JIANGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }else {
                                    myComparator comparator=new myComparator(myComparator.PINGJIA_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;
                        }

                        mfragment.mAdapter.notifyDataSetChanged();
                        onLoad();
                    }
                });
    }

    public void initdata(){
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("job", "false")
                .build()
                .execute(new mCallBack<Object>(this) {
                    @Override
                    public Object parseNetworkResponse(Response response) throws IOException {

                        Log.i("LOL", "response");
                        String string = response.body().string();

                        Object ps = new Gson().fromJson(string, new TypeToken<Object>() {
                        }.getType());
                        return ps;
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                        linkedTreeMapArrayList = (ArrayList<LinkedTreeMap>) response;
                        dataFragment.dianjiangItemBeans.clear();
                        dianjiangItemBean bean;

                        for (LinkedTreeMap treemap : linkedTreeMapArrayList
                                ) {
                            //URI
                            ArrayList<String> arrayList;
                            String string;
                            Uri uri=null;
                            arrayList = (ArrayList<String>) treemap.get("touxiang");
                            if (!arrayList.isEmpty()){
                                string = arrayList.get(arrayList.size() - 1);
                                uri = mytool.UriFromSenge(string);
                            }
                            String phone=(String)treemap.get("phone");
                            String name = (String) treemap.get("xingming");
                            String gongzhong = (String) treemap.get("gongzhong");
                            Double price = (Double) treemap.get("rixin");
                            Double level = (Double) treemap.get("dengji");
                            bean = new dianjiangItemBean(uri, name, gongzhong, level, price,phone);
                            bean.biaoqian=(String)treemap.get("biaoqian");
                            bean.pingjia= (Double) treemap.get("pingjiaxingji");
                            bean.diqu=(String)treemap.get("didianchar");
                            dataFragment.dianjiangItemBeans.add(0, bean);
                        }

                        //dianjiangItemBean bean=new dianjiangItemBean(linkedTreeMap.get("tupian"))
                        //dataFragment.dianjiangItemBeans.add()
                        switch (option_state){
                            case LEVEL:
                                if (level_state==JIANGXU){
                                    myComparator comparator=new myComparator(myComparator.LEVEL_JIANGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }else {
                                    myComparator comparator=new myComparator(myComparator.LEVEL_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;

                            case PRICE:
                                if (price_state==JIANGXU){
                                    myComparator comparator=new myComparator(myComparator.PRICE_JIANGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }else {
                                    myComparator comparator=new myComparator(myComparator.PRICE_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;

                            case PINGJIA:
                                if (pingjia_state==JIANGXU){
                                    myComparator comparator=new myComparator(myComparator.PINGJIA_JIANGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }else {
                                    myComparator comparator=new myComparator(myComparator.PINGJIA_SHENGXU);
                                    Collections.sort(dataFragment.dianjiangItemBeans,comparator);
                                }
                                break;
                        }

                        mfragment.mAdapter.notifyDataSetChanged();
                    }
                });
    }

    abstract class mCallBack<T> extends Callback<T>{
        MyFragment2 mfragment;
        public mCallBack(MyFragment2 mfragment){
        this.mfragment=mfragment;
    }
    }
}
