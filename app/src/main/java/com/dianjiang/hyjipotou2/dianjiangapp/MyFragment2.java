package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View viewFragment;
    private XListView xListView=null;
    private Handler mHandler;
    public dianjiangItemAdapter mAdapter;
    private Uri uri;

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
    public DataFragment dataFragment=DataFragment.getInstance();

    private LinkedTreeMap linkedTreeMap;



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
        uri=Uri.fromFile(new File("/storage/sdcard1/tieba/kinght.jpg"));
        for (int i=0;i<10;i++){
            dataFragment.dianjiangItemBeans.add(0, new dianjiangItemBean(uri, "装哭的骑士" + i, "歌姬" + i, "MR" + i, "1000" + i));
        }
        mAdapter=new dianjiangItemAdapter(this.getActivity(),dataFragment.dianjiangItemBeans);
        xListView.setAdapter(mAdapter);


        xListView.setXListViewListener(this);
        mHandler=new Handler();
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
       /* mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int count;  //获取数据个数
                count=10;


                //数据
                uri=Uri.fromFile(new File("/storage/sdcard1/tieba/miaoniang.jpg"));
                for (int i=0;i<count;i++){
                    itemBeanList.add(0,new dianjiangItemBean(uri,"罗艾娜"+i,"歌姬"+i,"MR"+i,"1000"+i));
                }
                mAdapter.notifyDataSetChanged();

                onLoad();
            }
        }, 2000);*/
        OkHttpUtils
                .get()
                .url(URL + USERAPI)
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

                        linkedTreeMap= (LinkedTreeMap) response;

                       //dianjiangItemBean bean=new dianjiangItemBean(linkedTreeMap.get("tupian"))
                       //dataFragment.dianjiangItemBeans.add()

                        mfragment.mAdapter.notifyDataSetChanged();
                    }
                });

    }

    public void setTopOptionListener(){

        Resources resources=(Resources)getActivity().getBaseContext().getResources();
        final ColorStateList main_color=(ColorStateList)resources.getColorStateList(R.color.main_color);
        final ColorStateList non_color=(ColorStateList)resources.getColorStateList(R.color.black_color);

        level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //改变颜色
                level_text.setTextColor(main_color);
                price_text.setTextColor(non_color);
                pingjia_text.setTextColor(non_color);
                level_img.setImageResource(R.drawable.jiangxu);
                price_img.setImageResource(R.drawable.paixu);
                pingjia_img.setImageResource(R.drawable.paixu);
            }
        });

        price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //改变颜色
                level_text.setTextColor(non_color);
                price_text.setTextColor(main_color);
                pingjia_text.setTextColor(non_color);
                level_img.setImageResource(R.drawable.paixu);
                price_img.setImageResource(R.drawable.jiangxu);
                pingjia_img.setImageResource(R.drawable.paixu);
            }
        });

        pingjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //改变颜色
                level_text.setTextColor(non_color);
                price_text.setTextColor(non_color);
                pingjia_text.setTextColor(main_color);
                level_img.setImageResource(R.drawable.paixu);
                price_img.setImageResource(R.drawable.paixu);
                pingjia_img.setImageResource(R.drawable.jiangxu);
            }
        });
    }

    public List<dianjiangItemBean> descSort(List<dianjiangItemBean> list) {

        list=new ArrayList<>();

        int in, out;
        int temp = 0;

        int price;
        int decprice;

        for (out = 0; out < list.size(); out++) {

            for (in = list.size() - 1; in > out; in--) {

                price=Integer.parseInt(list.get(in).price,10);
                decprice=Integer.parseInt(list.get(in-1).price,10);

                if (price > decprice) {
                    temp = price;
                    price = decprice;
                    decprice = temp;
                }
            }
        }
        return list;
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

abstract class mCallBack<T> extends Callback<T>{
    MyFragment2 mfragment;
    public mCallBack(MyFragment2 mfragment){
        this.mfragment=mfragment;
    }
}
}
