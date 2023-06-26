package com.example.listennewsforenglish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetlistActivity extends AppCompatActivity {
    private ArrayList<NewsBean> mDatas = new ArrayList<>();
    private int[] ImageSource = {R.mipmap.new0,R.mipmap.new1,R.mipmap.new2,R.mipmap.new3,R.mipmap.new4,
            R.mipmap.new5};
    private RecyclerView  mRecyclerView;
    private final String TAG = "DailyVOA";
    private NewsApplication newsApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        initData();
        newsApplication = (NewsApplication)getApplication();
        newsApplication.newsBeanList = mDatas;
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_list);
        //设置布局管理器
        //采用垂直式布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        NewsAdapter newsAdapter = new NewsAdapter(mDatas);
        newsAdapter.setMyItemOnclickListner(new NewsAdapter.ViewHolder.MyItemOnclickListner() {
            @Override
            public void onItemClick(View view, int postition) {
                Log.d(TAG,postition + "被点击了");
                newsApplication.newsItemPos = postition;
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(newsAdapter);
    }

    //目前数据是写死，后续有需要可以添加网络初始化
    public void initData() {
        for (int i = 0; i < 20; ++i) {
            NewsBean newsBean = ReadFile(i);
            mDatas.add(newsBean);
        }
    }

    private NewsBean ReadFile(int i) {
        boolean isFirstLine = true;
        String title = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open(i % 6 +".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                // 处理每一行数据
                if (isFirstLine) {
                    title = line;
                    isFirstLine = false;
                    continue;
                }
                stringBuilder.append(line);
                stringBuilder.append("\n\n");
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        NewsBean newsBean = new NewsBean(i,title,ImageSource[i%6],stringBuilder.toString());
        return newsBean;
    }
}