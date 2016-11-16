package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView;
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
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myfactoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myfactoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myfactoryFragment extends Fragment implements XListView.IXListViewListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //public static final String URL="http://192.168.191.1:8000";
    //public static final String USERAPI="/userapi/";
    //public static final String GONGCHENGAPI="/gongchengapi/";
    public static int SAVE;
    // TODO: Rename and change types of parameters
    private String mParam1;

    public processItemAdapter mAdapter;

    private XListView xListView=null;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private View view;
    private List<processItemBean> itemBeans;
    private processItemBean itemBean;
    private LinkedTreeMap linkedTreeMap;
    private ArrayList<LinkedTreeMap> linkedTreeMapArrayList=new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public myfactoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment myfactoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static myfactoryFragment newInstance(String param1) {
        myfactoryFragment fragment = new myfactoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.gongchengtabview, container, false);

        geneItems();
        initViews();

        return view;
    }

    private void initViews(){
        xListView=(XListView)view.findViewById(R.id.gongcheng_list);

        setdata();


        xListView.setXListViewListener(this);



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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        if (mParam1.equalsIgnoreCase("招标工程") || mParam1.equalsIgnoreCase("指定工程")){
            OkHttpUtils
                    .get()
                    .url(MainActivity.URL + MainActivity.PROCESSAPI)
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
                            dataFragment.process_datamap=(ArrayList<LinkedTreeMap<String,Object>>) response;
                            setdata();
                            onLoad();
                        }
                    });
        }else {
        OkHttpUtils
                .get()
                .url(MainActivity.URL + MainActivity.USERAPI)
                .addParams("phone", dengluActivity.phone)
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
                        linkedTreeMap = (LinkedTreeMap) response;
                        DataFragment dataFragment=DataFragment.getInstance();
                        dataFragment.user_datamap=linkedTreeMap;
                        setdata();
                        onLoad();
                    }
                });}

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    abstract class mCallBack<T> extends Callback<T>{
       myfactoryFragment mfragment;
        public mCallBack(myfactoryFragment mfragment){
            this.mfragment=mfragment;
        }
    }

    public void setdata(){
        final int zhuangtai[]=new int[2];
        DataFragment data=DataFragment.getInstance();
        ArrayList<LinkedTreeMap<String,Object>> gongcheng_set=(ArrayList<LinkedTreeMap<String,Object>>)data.user_datamap.get("gongcheng_set");
        itemBeans=new ArrayList<processItemBean>();
        if(mParam1.equalsIgnoreCase("招标工程") || mParam1.equalsIgnoreCase("指定工程") || mParam1.equalsIgnoreCase("招标成功") || mParam1.equalsIgnoreCase("进行中") || mParam1.equalsIgnoreCase("已结款") || mParam1.equalsIgnoreCase("未结款"))
        {
            if (mParam1.equalsIgnoreCase("招标工程")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap) {

                    if ((int) ((double) map.get("zhuangtai")) >= 2 && (int) ((double) map.get("zhuangtai")) <= 3) {
                        if (((String) map.get("suozaidi")).split(",").length >= 2)
                            //if (((String) map.get("suozaidi")).split(",")[1].equals(data.city)) {
                            if (true) {
                                ArrayList<String> tupianlist = (ArrayList<String>) map.get("tupian");

                                Uri uri;
                                String string;
                                if (tupianlist.isEmpty()) {
                                    uri = null;
                                } else {
                                    string = tupianlist.get(tupianlist.size() - 1);
                                    uri = mytool.UriFromSenge(string);
                                }

                                itemBeans.add(0, new processItemBean(uri, (String) map.get("biaoti"), map.get("id").toString(), (String) map.get("miaoshu"), (int) (double) map.get("zhuangtai"), (String) map.get("autotime"), (String) map.get("gongchengjindu")));

                            }
                    }
                }

            }
            else if (mParam1.equalsIgnoreCase("指定工程")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap){
                    for (String phone:(ArrayList<String>)map.get("yaoqing")){
                    if (phone.equals(dengluActivity.phone)){
                        ArrayList<String> tupianlist=(ArrayList<String>)map.get("tupian");

                        Uri uri;
                        String string;
                        if (tupianlist.isEmpty()){
                            uri=null;
                        }else {
                            string=tupianlist.get(tupianlist.size()-1);
                            uri=mytool.UriFromSenge(string);
                        }

                        itemBeans.add(0,new processItemBean(uri,(String)map.get("biaoti"),map.get("id").toString(),(String)map.get("miaoshu"),(int)(double)map.get("zhuangtai"),(String)map.get("autotime"),(String)map.get("gongchengjindu")));

                    }
                    }
                }
            }else if (mParam1.equalsIgnoreCase("招标成功")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap){
                    if ((int) ((double) map.get("zhuangtai")) >= 3 && (int) ((double) map.get("zhuangtai")) <= 3) {
                        for (String phone : (ArrayList<String>) map.get("zhiding")) {
                            if (phone.equals(dengluActivity.phone)) {
                                ArrayList<String> tupianlist = (ArrayList<String>) map.get("tupian");

                                Uri uri;
                                String string;
                                if (tupianlist.isEmpty()) {
                                    uri = null;
                                } else {
                                    string = tupianlist.get(tupianlist.size() - 1);
                                    uri = mytool.UriFromSenge(string);
                                }

                                itemBeans.add(0, new processItemBean(uri, (String) map.get("biaoti"), map.get("id").toString(), (String) map.get("miaoshu"), (int) (double) map.get("zhuangtai"), (String) map.get("autotime"), (String) map.get("gongchengjindu")));

                            }
                        }
                    }
                }
            }else if (mParam1.equalsIgnoreCase("进行中")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap){
                    if ((int) ((double) map.get("zhuangtai")) >= 4 && (int) ((double) map.get("zhuangtai")) <= 4) {
                        for (String phone : (ArrayList<String>) map.get("zhiding")) {
                            if (phone.equals(dengluActivity.phone)) {
                                ArrayList<String> tupianlist = (ArrayList<String>) map.get("tupian");

                                Uri uri;
                                String string;
                                if (tupianlist.isEmpty()) {
                                    uri = null;
                                } else {
                                    string = tupianlist.get(tupianlist.size() - 1);
                                    uri = mytool.UriFromSenge(string);
                                }

                                itemBeans.add(0, new processItemBean(uri, (String) map.get("biaoti"), map.get("id").toString(), (String) map.get("miaoshu"), (int) (double) map.get("zhuangtai"), (String) map.get("autotime"), (String) map.get("gongchengjindu")));

                            }
                        }
                    }
                }
            }else if (mParam1.equalsIgnoreCase("已结款")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap){
                    if ((int) ((double) map.get("zhuangtai")) >= 6 && (int) ((double) map.get("zhuangtai")) <= 6) {
                        for (String phone : (ArrayList<String>) map.get("zhiding")) {
                            if (phone.equals(dengluActivity.phone)) {
                                ArrayList<String> tupianlist = (ArrayList<String>) map.get("tupian");

                                Uri uri;
                                String string;
                                if (tupianlist.isEmpty()) {
                                    uri = null;
                                } else {
                                    string = tupianlist.get(tupianlist.size() - 1);
                                    uri = mytool.UriFromSenge(string);
                                }

                                itemBeans.add(0, new processItemBean(uri, (String) map.get("biaoti"), map.get("id").toString(), (String) map.get("miaoshu"), (int) (double) map.get("zhuangtai"), (String) map.get("autotime"), (String) map.get("gongchengjindu")));

                            }
                        }
                    }
                }
            }else if (mParam1.equalsIgnoreCase("未结款")){
                for (LinkedTreeMap<String,Object> map:data.process_datamap){
                    if ((int) ((double) map.get("zhuangtai")) >= 5 && (int) ((double) map.get("zhuangtai")) <= 5) {
                        for (String phone : (ArrayList<String>) map.get("zhiding")) {
                            if (phone.equals(dengluActivity.phone)) {
                                ArrayList<String> tupianlist = (ArrayList<String>) map.get("tupian");

                                Uri uri;
                                String string;
                                if (tupianlist.isEmpty()) {
                                    uri = null;
                                } else {
                                    string = tupianlist.get(tupianlist.size() - 1);
                                    uri = mytool.UriFromSenge(string);
                                }

                                itemBeans.add(0, new processItemBean(uri, (String) map.get("biaoti"), map.get("id").toString(), (String) map.get("miaoshu"), (int) (double) map.get("zhuangtai"), (String) map.get("autotime"), (String) map.get("gongchengjindu")));

                            }
                        }
                    }
                }
            }

        }  else {
        if (mParam1.equalsIgnoreCase("已发布工程")) {
        zhuangtai[0] = 0;
        zhuangtai[1] = 6;
    }

    if (mParam1.equalsIgnoreCase("未发布工程")) {
        zhuangtai[0]=-3;
        zhuangtai[1]=-3;
    }

    if (mParam1.equalsIgnoreCase("招标中工程")) {
        zhuangtai[0] = 2;
        zhuangtai[1] = 2;
    }

    if (mParam1.equalsIgnoreCase("已完工工程")) {
        zhuangtai[0] = 6;
        zhuangtai[1] = 6;
    }
    /*if (mParam1.equalsIgnoreCase("招标成功"))

    {
        zhuangtai[0] = 3;
        zhuangtai[1] = 3;
    }
    if (mParam1.equalsIgnoreCase("进行中"))

    {
        zhuangtai[0] = 4;
        zhuangtai[1] = 4;
    }

    if (mParam1.equalsIgnoreCase("已结款"))

    {
        zhuangtai[0] = 6;
        zhuangtai[1] = 6;
    }

    if (mParam1.equalsIgnoreCase("未结款"))

    {
        zhuangtai[0] = 5;
        zhuangtai[1] = 5;
    }
    */

   for(LinkedTreeMap<String,Object> object:gongcheng_set)
    {
        if((int)(double)object.get("zhuangtai")>=zhuangtai[0]&&(int)(double)object.get("zhuangtai")<=zhuangtai[1])
        {
                //ArrayList<Map<String,Object>> gongchenglist;
                // DataFragment dataFragment=DataFragment.getInstance();
                // ArrayList<String> gongchengid=new ArrayList<>();
                //gongchenglist= (ArrayList<Map<String, Object>>) dataFragment.user_datamap.get("gongcheng_set");
                ArrayList<String> tupianlist = (ArrayList<String>) object.get("tupian");

                Uri uri;
                String string = new String();
                if (tupianlist.isEmpty()) {
                    uri = null;
                } else {
                    string = tupianlist.get(tupianlist.size() - 1);
                    uri = mytool.UriFromSenge(string);
                }

                itemBeans.add(0, new processItemBean(uri, (String) object.get("biaoti"), object.get("id").toString(), (String) object.get("miaoshu"), (int) (double) object.get("zhuangtai"), (String) object.get("autotime"), (String) object.get("gongchengjindu")));
            }

    }

    //itemBeans.add(0,new processItemBean(uri,"我的工程"+i,"1234566"+i,"这是一个工程哟"+i,1,"2014-1-12 23:42:3"+i,"这是一个工程进度哟"+i));

}





        mAdapter=new processItemAdapter(getActivity(),itemBeans);
        xListView.setAdapter(mAdapter);
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataFragment dataFragment = DataFragment.getInstance();
                dataFragment.mprocessItemBean = itemBeans.get(position - 1);
                if (mParam1.equals("未发布工程")) {
                    Intent intent = new Intent(getActivity(), fabuActivity.class);
                    DataFragment.save = true;
                    intent.putExtra("id", dataFragment.mprocessItemBean.processnumber);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("招标工程")) {
                    DataFragment.gongchengbiaoji = 1;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("指定工程")) {
                    DataFragment.gongchengbiaoji = 2;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("进行中")) {
                    DataFragment.gongchengbiaoji = 3;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("已发布工程")) {
                    DataFragment.gongchengbiaoji = 4;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("招标中工程")) {
                    DataFragment.gongchengbiaoji = 5;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("已完工工程")) {
                    DataFragment.gongchengbiaoji = 6;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else if (mParam1.equalsIgnoreCase("未结款")) {
                    DataFragment.gongchengbiaoji=7;
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), gongchengxiangxiActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
