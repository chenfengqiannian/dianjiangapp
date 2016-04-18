package com.dianjiang.hyjipotou2.dianjiangapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link fabuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fabuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fabuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;

    //工程信息
    private EditText xinxi_biaoti;
    private EditText xinxi_miaoshu;
    private TextView xinxi_tupiantext;
    private ImageView xinxi_tupian1;
    private ImageView xinxi_tupian2;
    private ImageView xinxi_tupianOption;
    private RelativeLayout xinxi_shigongdiqu;
    private EditText xinxi_xiangxidizhi;

    //接包要求
    private RelativeLayout jiebao_gongzhong;
    private EditText jiebao_yaoqiu;
    private RelativeLayout jiebao_shigongtime;
    private RelativeLayout jiebao_wangongtime;
    private TextView jiebao_price;
    private TextView jiebao_choulao;
    private RelativeLayout fabu2_fukuanfangshi;

    private OnFragmentInteractionListener mListener;


    public fabuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fabuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static fabuFragment newInstance(String param1, String param2,String param3) {
        fabuFragment fragment = new fabuFragment();
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
        if (mParam1=="工程信息"){
            view=inflater.inflate(R.layout.fabu_fragment1,null);
            xinxiInit();

            return view;
        }
        if (mParam1=="接包要求"){
            view=inflater.inflate(R.layout.fabu_fragment2,null);
            return view;
        }
            view=inflater.inflate(R.layout.fabu_fragment3,null);
            return view;
    }

    //工程信息初始化
    public void xinxiInit(){
        xinxi_biaoti= (EditText) view.findViewById(R.id.fabu1_biaoti);
        xinxi_miaoshu= (EditText) view.findViewById(R.id.fabu1_miaoshu);
        xinxi_tupian1= (ImageView) view.findViewById(R.id.xinxi_tupian1);
        xinxi_tupian2= (ImageView) view.findViewById(R.id.xinxi_tupian2);
        xinxi_tupianOption= (ImageView) view.findViewById(R.id.img_option);
        xinxi_tupiantext=(TextView)view.findViewById(R.id.fabu1_tupian);
        xinxi_shigongdiqu=(RelativeLayout)view.findViewById(R.id.fabu1_diqu);
        xinxi_xiangxidizhi=(EditText)view.findViewById(R.id.fabu1_dizhi);
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
