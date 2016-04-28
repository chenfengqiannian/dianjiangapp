package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.SimpleFragmentPagerAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link gongjianglstFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link gongjianglstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gongjianglstFragment extends android.support.v4.app.Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AlertDialog.Builder builder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout textDialog;
    private OnFragmentInteractionListener mListener;

    private ImageView shezhi;
    private TextView xingming;
    private TextView dianhua;
    private SimpleDraweeView touxiang;
    private RelativeLayout gerenxinxi;
    private RelativeLayout wodezhanghu;
    private RelativeLayout suoyaofapiao;
    private RelativeLayout wodebanzu;
    private RelativeLayout banggongyou;
    private RelativeLayout dianjiangketang;
    private RelativeLayout lianxikefu;
    private RelativeLayout yijianfankui;
    private TextView fr1_province;
    private TextView fr1_city;
    private RelativeLayout wodeyeji;
    private TextView diqutext;
    private String dianhua1;

    private Button button1;
    private Button button2;

    private EditText text;

    private TabLayout mytab;
    private ViewPager viewPager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gongjianglstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static gongjianglstFragment newInstance(String param1, String param2) {
        gongjianglstFragment fragment = new gongjianglstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public gongjianglstFragment() {
        // Required empty public constructor
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
        View view=null;
        if (mParam1.equalsIgnoreCase("tab1"))
        view=init(0,inflater, container);
        if(mParam1.equalsIgnoreCase("tab2"))
        view=init(1,inflater, container);
        if(mParam1.equalsIgnoreCase("tab3"))
        view=init(2,inflater, container);

        //if(mParam1.equalsIgnoreCase("ketang"))
           // view=init(3,inflater, container);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(v==shezhi)
        {onButtonPressed("shezhi");}
        if(v==lianxikefu)
        {final String[] phonenum={dianhua1};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                    .setTitle("点击拨打客服电话")
                    .setItems(phonenum, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phonenum[0]));
                            startActivity(intent);
                        }
                    });
            builder.create().show();}
        if(v==yijianfankui)
        {builder=new AlertDialog.Builder(getActivity())
                .setTitle("请输入反馈信息")
                .setView(null)
                .setView(textDialog)
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //提取消息

                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textDialog=null;
                        if (textDialog==null){
                            textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                            text= (EditText) textDialog.findViewById(R.id.text123);
                        }
                    }
                });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    textDialog=null;
                    if (textDialog==null){
                        textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);
                        text= (EditText) textDialog.findViewById(R.id.text123);
                    }
                }
            });
            builder.create().show();}

        if(v==wodezhanghu)
        {
            Intent intent=new Intent();
            intent.setClass(getActivity().getApplicationContext(), wodezhanghuActivity.class);
            startActivity(intent);



        }
        if(v==dianjiangketang)
        {

onButtonPressed("dianjiangketang");


        }
        if(v==gerenxinxi)
        {onButtonPressed("gerenxinxi");}
        if(v==suoyaofapiao)
        {}

        if(v==wodebanzu)
        {onButtonPressed("wodebanzu");}
        if(v==banggongyou)
        {   onButtonPressed("banggongyou");}

        if (v==wodeyeji){
            onButtonPressed("wodeyeji");
        }
        if (v==button1){
            setDiqudialog2();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        DataFragment da=DataFragment.getInstance();
        if (!mParam1.equalsIgnoreCase("tab3")){
        fr1_province.setText(da.province);
        fr1_city.setText(da.city);}
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
        public void onFragmentInteraction(String uri);
    }

    private View init(int index,LayoutInflater inflater, ViewGroup container) {
    View view=null;
        if (index == 0) {view=inflater.inflate(R.layout.gongjiang_fragment1,container,false);
        viewPager=(ViewPager)view.findViewById(R.id.xinrenwu_pager);
        mytab=(TabLayout)view.findViewById(R.id.xinrenwu_tabs);
            fr1_province=(TextView)view.findViewById(R.id.gongjiang_sheng);
            fr1_city= (TextView) view.findViewById(R.id.gongjiang_shi);
            button1= (Button) view.findViewById(R.id.gongjiang_qiehuan);
            button1.setOnClickListener(this);
            DataFragment dataFragment=DataFragment.getInstance();
            fr1_city.setText(dataFragment.city);
            fr1_province.setText(dataFragment.province);

            ArrayList<myfactoryFragment> fragments=new ArrayList<>();
            fragments.add(myfactoryFragment.newInstance("招标工程"));
            fragments.add(myfactoryFragment.newInstance("指定工程"));
            myPagePager page=new myPagePager(getChildFragmentManager(),getActivity().getApplicationContext(),fragments,new String[]{"招标工程","指定工程"});
            viewPager.setAdapter(page);
            mytab.setupWithViewPager(viewPager);
            mytab.setTabMode(TabLayout.MODE_FIXED);

        }

        if (index == 1) {view=inflater.inflate(R.layout.gongjiang_fragment2,container,false);
            viewPager=(ViewPager)view.findViewById(R.id.lishirenwu_pager);
            mytab=(TabLayout)view.findViewById(R.id.lishirenwu_tabs);

            fr1_province=(TextView)view.findViewById(R.id.gongjiang_sheng);
            fr1_city= (TextView) view.findViewById(R.id.gongjiang_shi);

            button1= (Button) view.findViewById(R.id.gongjiang_qiehuan_);
            button1.setOnClickListener(this);
            DataFragment dataFragment=DataFragment.getInstance();
            fr1_city.setText(dataFragment.city);
            fr1_province.setText(dataFragment.province);

            ArrayList<myfactoryFragment> fragments=new ArrayList<>();
            fragments.add(myfactoryFragment.newInstance("招标成功"));
            fragments.add(myfactoryFragment.newInstance("进行中"));
            fragments.add(myfactoryFragment.newInstance("已结款"));
            fragments.add(myfactoryFragment.newInstance("未结款"));
            myPagePager page=new myPagePager(getChildFragmentManager(),getActivity().getApplicationContext(),fragments,new String[]{"招标成功","进行中","已结款","未结款"});
            viewPager.setAdapter(page);
            mytab.setupWithViewPager(viewPager);
            mytab.setTabMode(TabLayout.MODE_FIXED);

        }

        if (index == 2)

        {view=inflater.inflate(R.layout.gongjiang_fragment3,container,false);
        shezhi=(ImageView)view.findViewById(R.id.gongjiang_shezhi);
            xingming=(TextView)view.findViewById(R.id.gongjiang_nicheng);
            dianhua=(TextView)view.findViewById(R.id.gongjiang_shoujihao);
            touxiang=(SimpleDraweeView)view.findViewById(R.id.gongjiang_touxiang);
            gerenxinxi=(RelativeLayout)view.findViewById(R.id.gongjiang_xinxi);
            wodezhanghu=(RelativeLayout)view.findViewById(R.id.gongjiang_zhanghu);
            suoyaofapiao=(RelativeLayout)view.findViewById(R.id.gongjiang_yeji);
            wodebanzu=(RelativeLayout)view.findViewById(R.id.gongjiang_banzu);
            banggongyou=(RelativeLayout)view.findViewById(R.id.gongjiang_bangzhuce);
            dianjiangketang=(RelativeLayout)view.findViewById(R.id.gongjiang_ketang);
            lianxikefu=(RelativeLayout)view.findViewById(R.id.gongjiang_kefu);
            yijianfankui=(RelativeLayout)view.findViewById(R.id.gongjiang_yijian);
            textDialog=(LinearLayout)getActivity().getLayoutInflater().inflate(R.layout.textdialog, null);

            wodeyeji= (RelativeLayout) view.findViewById(R.id.gongjiang_yeji);
            getkefudianhua();
            shezhi.setOnClickListener(this);
            gerenxinxi.setOnClickListener(this);
            wodebanzu.setOnClickListener(this);
            suoyaofapiao.setOnClickListener(this);
            wodezhanghu.setOnClickListener(this);
            banggongyou.setOnClickListener(this);
            dianjiangketang.setOnClickListener(this);
            lianxikefu.setOnClickListener(this);
            yijianfankui.setOnClickListener(this);
            if (!((ArrayList<String>)DataFragment.getInstance().user_datamap.get("touxiang")).isEmpty()) {
                touxiang.setImageURI(mytool.UriFromSenge(((ArrayList<String>) DataFragment.getInstance().user_datamap.get("touxiang")).get(((ArrayList<String>) DataFragment.getInstance().user_datamap.get("touxiang")).size() - 1)));
            }

            xingming.setText(DataFragment.getInstance().user_datamap.get("xingming").toString());
            dianhua.setText(dengluActivity.phone);


        }

if(index==3)
{

    view=inflater.inflate(R.layout.fragment_blank, container, false);
}
    return view;
    }

class myPagePager extends SimpleFragmentPagerAdapter
{


    public myPagePager(FragmentManager fm, Context context, ArrayList<myfactoryFragment> fragmentlist,String[] oj) {
        super(fm, context, fragmentlist);
        this.tabTitles=oj;
        this.PAGE_COUNT=oj.length;
    }
}
    public void getkefudianhua(){
        ArrayList<String> arrayList=new ArrayList<>();
        DataFragment dataFragment=DataFragment.getInstance();
        arrayList= (ArrayList<String>) dataFragment.linkedTreeMap.get("lianxikefu");
        dianhua1=arrayList.get(0);

    }
    public void setDiqudialog2(){
        final ArrayList<Map<String,Object>> arrayList;
        final DataFragment dataFragment=DataFragment.getInstance();
        arrayList=dataFragment.getGson();


        ArrayList<ArrayList> citylist=new ArrayList<>();
        final String[] sheng1=new String[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            sheng1[i]=(String) arrayList.get(i).get("name");
            //citylist.add((ArrayList) arrayList.get(i).get("city"));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("请选择区域")
                .setItems(sheng1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataFragment.sheng_ = sheng1[which];
                        ArrayList<LinkedTreeMap<String, Object>> arrayList1;
                        arrayList1 = (ArrayList<LinkedTreeMap<String, Object>>) arrayList.get(which).get("city");
                        final String[] shi1 = new String[arrayList1.size()];
                        for (int i = 0; i < arrayList1.size(); i++) {
                            shi1[i] = (String) arrayList1.get(i).get("name");
                        }
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity())
                                .setTitle("请选择区域")
                                .setItems(shi1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dataFragment.shi_ = shi1[which];
                                        dataFragment.province=sheng1[which];
                                        dataFragment.city=shi1[which];
                                        fr1_city.setText(dataFragment.shi_);
                                        fr1_province.setText(dataFragment.sheng_);
                                        //diqutext.setText(dataFragment.sheng_ + "," + dataFragment.shi_);
                                    }
                                });
                        builder1.create().show();
                    }
                });
        builder.create().show();
    }


}