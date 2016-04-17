package com.dianjiang.hyjipotou2.dianjiangapp;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * Created by hyjipotou2 on 16/4/16.
 */
public class SearchActivity extends Activity {

    private SearchView sv;
    private ListView lv;
    private final String[] mStrings={"aaaaa","bbbbb","ccccc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);

        lv=(ListView)findViewById(R.id.search_list);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings));

        //设置LISTVIEW启动过滤
        lv.setTextFilterEnabled(true);

        sv=(SearchView)findViewById(R.id.search_view);

        //设置该图标是否自动缩小 显示搜索按钮  设置提示文本
        sv.setIconifiedByDefault(false);
        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("请输入搜索内容");
        //设置搜索监听
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //在此执行实际查询

                Toast.makeText(SearchActivity.this,"您选择的是:"+query,Toast.LENGTH_SHORT).show();
                return false;
            }

            //用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {

                if (TextUtils.isEmpty(newText)){
                    lv.clearTextFilter();
                }
                else {
                    lv.setFilterText(newText);
                }
                return true;
            }
        });

    }
}
