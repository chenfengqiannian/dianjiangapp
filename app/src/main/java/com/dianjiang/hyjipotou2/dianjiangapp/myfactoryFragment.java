package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dianjiang.hyjipotou2.dianjiangapp.pullrefreshlistview.XListView;

import java.util.ArrayList;


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
    // TODO: Rename and change types of parameters
    private String mParam1;


    private XListView xListView=null;
    private ArrayList<String> items = new ArrayList<String>();
    private Handler mHandler;
    private int start = 0;
    private static int refreshCnt = 0;
    private View view;

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

        if (mParam1=="已发布工程"){
            String[] arr1={"Fate","Stay","Night"};
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.testitem,R.id.testText,arr1);
            xListView.setAdapter(adapter);
        }

        if (mParam1=="未发布工程"){
            String[] arr1={"Fate","Zero"};
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.testitem,R.id.testText,arr1);
            xListView.setAdapter(adapter);
        }

        if (mParam1=="招标中工程"){
            String[] arr1={"ffff","ccccc"};
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.testitem,R.id.testText,arr1);
            xListView.setAdapter(adapter);
        }

        if (mParam1=="已完工工程"){
            String[] arr1={"rrrrr","nnnnnnnqw"};
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),R.layout.testitem,R.id.testText,arr1);
            xListView.setAdapter(adapter);
        }

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = ++refreshCnt;
                items.clear();
                geneItems();

                //数据
                String[] arr1={"ali","prprpr"};
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.testitem,R.id.testText,arr1);
                xListView.setAdapter(adapter);

                onLoad();
            }
        }, 2000);

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
}
