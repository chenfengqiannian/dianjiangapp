package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.SimpleFragmentPagerAdapter;
import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.sFragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyFragment3.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment3 extends Fragment{

    private TabLayout mTabLayout;
    private SimpleFragmentPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    public static int state=0;
    private View view;
    private myfactoryFragment fragment1;
    private myfactoryFragment fragment2;
    private myfactoryFragment fragment3;
    private myfactoryFragment fragment4;
    private ArrayList<myfactoryFragment> fragmentArrayList;
    private final String page1="已发布工程";
    private final String page2="未发布工程";
    private final String page3="招标中工程";
    private final String page4="已完工工程";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MyFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static MyFragment3 newInstance(String param1, String param2) {
        MyFragment3 fragment = new MyFragment3();
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
        view=inflater.inflate(R.layout.fragment_my_fragment3,container,false);
        init();
        return view;
    }

    private void init(){
        fragment1=myfactoryFragment.newInstance(page1);
        fragment2=myfactoryFragment.newInstance(page2);
        fragment3=myfactoryFragment.newInstance(page3);
        fragment4=myfactoryFragment.newInstance(page4);
        fragmentArrayList=new ArrayList<myfactoryFragment>();
        fragmentArrayList.add(fragment1);
        fragmentArrayList.add(fragment2);
        fragmentArrayList.add(fragment3);
        fragmentArrayList.add(fragment4);


        //设置适配器
        pagerAdapter = new SimpleFragmentPagerAdapter(getChildFragmentManager(),getActivity(),fragmentArrayList);
        mViewPager = (ViewPager)view.findViewById(R.id.wodegongcheng_pager);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout)view.findViewById(R.id.wodegongcheng_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View tview) {
        if (mListener != null) {
            mListener.yifabugongcheng(tview);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void yifabugongcheng(View tview);
    }
}
