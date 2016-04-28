package com.dianjiang.hyjipotou2.dianjiangapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


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

    public static final int SHENFEN_CAMERA=800;
    public static final int SHENFEN_PHOTO=801;
    public static final int SHENFEN_PHOTO_REQUEST_CUT=311;

    public static final int FIRSTIMG=1;
    public static final int SECONDIMG=2;

    public static final int NO=0;
    public static final int OK=1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;

    //身份各种标记
    private int shenfen_state=1;
    private int shenfen_xiugai_state1=NO;
    private int shenfen_xiugai_state2=NO;
    private int shenfen_hold=1;
    private int shenfen_count1=0;
    private int shenfen_count2=0;
    private File tempFile;

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
    private RelativeLayout jiebao_fukuanfangshi;
    private EditText jiebao_beizhu;
    private TextView jiebao_gongzhongtext;
    private TextView jiebao_shigong;
    private TextView jiebao_wangong;
    private TextView jiebao_fukuantext;
    private static boolean[] state={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};

    //预览发布
    private TextView yulan_gongchenghao;
    private TextView yulan_gongchengname;
    private TextView yulan_gongchengmiaoshu;
    private TextView yulan_dizhi;
    private TextView yulan_gongzhong;
    private TextView yulan_yaoqiu;
    private TextView yulan_shichang;
    private TextView yulan_price;
    private TextView yulan_beizhu;


    private List<File> files=new ArrayList<>();
    private String[] str1=new String[]{"照相上传","选择图片上传"};

    private OnFragmentInteractionListener mListener;

    private List<Map<String,Object>> datalist;


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
    public static fabuFragment newInstance(String param1) {
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

        //工程信息
        if (mParam1.equalsIgnoreCase("工程信息")){
            view=inflater.inflate(R.layout.fabu_fragment1,null);
            xinxiInit();

            return view;
        }
          //接包要求
        else if (mParam1.equalsIgnoreCase("接包要求")){
            view=inflater.inflate(R.layout.fabu_fragment2,null);
            jiebaoInit();

            return view;
        }
         //预览发布
        else {
            view=inflater.inflate(R.layout.fabu_fragment3,null);
            yulanInit();

            return view;
        }
    }

    //工程信息初始化
    public void xinxiInit(){
        xinxi_biaoti= (EditText) view.findViewById(R.id.fabu1_biaoti);
        xinxi_miaoshu= (EditText) view.findViewById(R.id.fabu1_miaoshu);
        xinxi_tupian1= (ImageView) view.findViewById(R.id.xinxi_tupian1);
        xinxi_tupian2= (ImageView) view.findViewById(R.id.xinxi_tupian2);
        xinxi_tupianOption= (ImageView) view.findViewById(R.id.img_option);
        xinxi_tupiantext=(TextView)view.findViewById(R.id.fabu1_tupian);
        xinxi_xiangxidizhi=(EditText)view.findViewById(R.id.fabu1_dizhi);

        //监听器
        xinxi_tupianOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
            }
        });
        xinxi_tupian1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state1==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = FIRSTIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });

        xinxi_tupian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shenfen_xiugai_state2==OK) {
                    shenfen_hold = shenfen_state;
                    shenfen_state = SECONDIMG;
                    simpleListOption(str1, SHENFEN_CAMERA, SHENFEN_PHOTO);
                }
            }
        });
    }

    //接包要求初始化
    public void jiebaoInit(){
        jiebao_gongzhong= (RelativeLayout) view.findViewById(R.id.fabu2_gongzhong);
        jiebao_gongzhongtext=(TextView)view.findViewById(R.id.fabu2_gongzhongtext);
        jiebao_yaoqiu= (EditText) view.findViewById(R.id.fabu2_miaoshu);
        jiebao_shigongtime= (RelativeLayout) view.findViewById(R.id.fabu2_shigongshijian);
        jiebao_wangongtime=(RelativeLayout)view.findViewById(R.id.fabu2_wangong);
        jiebao_price=(EditText)view.findViewById(R.id.fabu2_price);
        jiebao_fukuanfangshi=(RelativeLayout)view.findViewById(R.id.fabu2_fukuanfangshi);
        jiebao_beizhu=(EditText)view.findViewById(R.id.fabu2_shurubeizhu);
        jiebao_fukuantext=(TextView)view.findViewById(R.id.fabu2_fukuantext);
        jiebao_shigong=(TextView)view.findViewById(R.id.shigongtime);
        jiebao_wangong=(TextView)view.findViewById(R.id.wangongtime);

        jiebao_gongzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jiebao_gongzhongtext.setText("请选择接包工种");
                gongzhongdialog();
            }
        });
        jiebao_shigongtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartDateDialog();
            }
        });
        jiebao_wangongtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndDateDialog();
            }
        });
    }
    //预览发布初始化
    public void yulanInit(){
        yulan_gongchengname= (TextView) view.findViewById(R.id.fabu3_gongchengming);
        yulan_gongchengmiaoshu=(TextView)view.findViewById(R.id.fabu3_miaoshu);
        yulan_dizhi=(TextView)view.findViewById(R.id.fabu3_dizhi);
        yulan_gongzhong=(TextView)view.findViewById(R.id.fabu3_gongzhong);
        yulan_yaoqiu=(TextView)view.findViewById(R.id.fabu3_yaoqiu);
        yulan_shichang=(TextView)view.findViewById(R.id.fabu3_shichang);
        yulan_price=(TextView)view.findViewById(R.id.fabu3_choulao);
        yulan_beizhu=(TextView)view.findViewById(R.id.fabu3_beizhu);

        DataFragment dataFragment=DataFragment.getInstance();
        //此处获取前两个FRAGMENT的信息
        yulan_gongchengname.setText( dataFragment.fabu_datamap.get("biaoti").toString());
        yulan_gongchengmiaoshu.setText(dataFragment.fabu_datamap.get("miaoshu").toString());
        yulan_dizhi.setText(dataFragment.fabu_datamap.get("xiangxidizhi").toString());
        yulan_gongzhong.setText(dataFragment.fabu_datamap.get("gongzhong").toString());
        yulan_yaoqiu.setText(dataFragment.fabu_datamap.get("yaoqiu").toString());
        yulan_price.setText(dataFragment.fabu_datamap.get("choulao").toString());
        yulan_beizhu.setText(dataFragment.fabu_datamap.get("beizhu").toString());
        yulan_shichang.setText(dataFragment.fabu_datamap.get("shichang").toString());
    }

    public void simpleListOption(String[] str1, final int camera, final int photo){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("请选择上传方式")
                .setItems(str1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            camera(camera);
                        }
                        if (which == 1) {
                            gallery(photo);
                        }
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                shenfen_state=shenfen_hold;
            }
        });
        builder.create().show();
    }

    //跳转至照相机
    public void camera(int f) {
        // 激活相机
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            tempFile = new File(Environment.getExternalStorageDirectory(),"camera_photo");
            // 从文件中创建uri
            Uri uri = Uri.fromFile(tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, f);
    }

    //跳转至xiangce
    public void gallery(int f) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
        startActivityForResult(intent,f);
    }

    //判断存储卡是否存在
    public boolean hasSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    //剪切图片
    private void crop(Uri uri,int f) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        files.add(mytool.getFileByUri(uri,getActivity()));
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent,f);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Map<String,Object> map) {
        if (mListener != null) {
            mListener.onFragmentInteraction(map);
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
    public void onStop() {
        super.onStop();
        DataFragment dataFragment=DataFragment.getInstance();
        Log.i("LOL","fragmentStop");
        if (mParam1.equalsIgnoreCase("工程信息")){
            //此处提取回传给Activity的工程信息
            dataFragment.fabu_datamap.put("biaoti", xinxi_biaoti.getText().toString());
            dataFragment.fabu_datamap.put("miaoshu",xinxi_miaoshu.getText().toString());
            dataFragment.fabu_datamap.put("xiangxidizhi",xinxi_xiangxidizhi.getText().toString());
            dataFragment.fabu_datamap.put("tupian", files);

        }
        else if (mParam1.equalsIgnoreCase("接包要求")){
            //此处提取回传给Activity的接包信息
            dataFragment.fabu_datamap.put("gongzhong",jiebao_gongzhongtext.getText().toString());
            dataFragment.fabu_datamap.put("yaoqiu",jiebao_yaoqiu.getText().toString());
            dataFragment.fabu_datamap.put("choulao",jiebao_price.getText().toString());
            dataFragment.fabu_datamap.put("fukuan",jiebao_fukuantext.getText().toString());
            dataFragment.fabu_datamap.put("beizhu",jiebao_beizhu.getText().toString());
            dataFragment.fabu_datamap.put("shichang",jiebao_shigong.getText().toString()+"至"+jiebao_wangong.getText().toString());
            dataFragment.fabu_datamap.put("kaishitime",jiebao_shigong.getText().toString());
            dataFragment.fabu_datamap.put("jieshutime",jiebao_wangong.getText().toString());

        }
        else if (mParam1.equalsIgnoreCase("预览发布")){

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int a=getActivity().RESULT_OK;
        if (resultCode != getActivity().RESULT_OK) {
            return;
        }
        switch (requestCode) {

            ////////////////////////////////////////////
            case SHENFEN_PHOTO:
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    crop(uri,SHENFEN_PHOTO_REQUEST_CUT);
                    xinxi_tupiantext.setText("");
                }
                break;
            case SHENFEN_CAMERA:
                if (hasSdcard()) {
                    crop(Uri.fromFile(tempFile),SHENFEN_PHOTO_REQUEST_CUT);
                    xinxi_tupiantext.setText("");
                } else {
                    Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }
                break;
            case SHENFEN_PHOTO_REQUEST_CUT:
                if (data != null) {
                    switch (shenfen_state){
                        case FIRSTIMG:
                            Bitmap bitmap = data.getParcelableExtra("data");
                            xinxi_tupian1.setImageBitmap(bitmap);
                            shenfen_xiugai_state1=OK;
                            shenfen_count1++;
                            shenfen_state=SECONDIMG;
                            if (shenfen_count1>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                        case SECONDIMG:
                            Bitmap bitmap0=data.getParcelableExtra("data");
                            xinxi_tupian2.setImageBitmap(bitmap0);
                            shenfen_xiugai_state2=OK;
                            xinxi_tupianOption.setVisibility(View.INVISIBLE);
                            shenfen_count2++;
                            if (shenfen_count2>=2){
                                shenfen_state=shenfen_hold;
                            }
                            break;
                    }
                }
                try {
                    // 将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public void gongzhongdialog() {
       // boolean[] state={false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false};
        final String[] gongzhong = {"电工","木工","瓦工","焊工","架子工","钢筋工","抹灰工","砌筑工","混凝土工","油漆工","防水工","管道工","吊顶工","无气喷涂工","钻孔工","拆除工","普工/杂工","项目经理","生产经理","工长","监理","施工员","质量员","安全员","材料员","资料员","预算员","机械员","测量员","劳务员","司索指挥","塔吊司机","吊车司机","起重机司机","升降机司机","挖掘机司机","推土机司机","叉车司机","电梯司机","机械修理工","机械安装/拆除工"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle("请选择工种")
                .setMultiChoiceItems(gongzhong,state, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //if (fabuFragment.state[which])
                        //DataFragment dataFragment=DataFragment.getInstance();
                        //dataFragment.gongzhongstring = dataFragment.gongzhongstring +" "+gongzhong[which];
                        //dataFragment.gongzhongstring.replace()
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataFragment dataFragment=DataFragment.getInstance();
                        for (int i=0;i<state.length;i++){
                            if (state[i]==true){
                                dataFragment.gongzhongstring=dataFragment.gongzhongstring+" "+gongzhong[i];
                                state[i]=false;
                            }
                        }
                        jiebao_gongzhongtext.setText(dataFragment.gongzhongstring);
                    }
                });
        builder.create().show();
    }

    public void StartDateDialog(){
        Calendar c=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                jiebao_shigong.setText(Integer.toString(year)+"-"+Integer.toString(monthOfYear)+"-"+Integer.toString(dayOfMonth));
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void EndDateDialog(){
        Calendar c=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                jiebao_wangong.setText(Integer.toString(year)+"-"+Integer.toString(monthOfYear)+"-"+Integer.toString(dayOfMonth));
            }
        },c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
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
        void onFragmentInteraction(Map<String,Object> map);
    }
}
