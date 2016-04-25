package com.fantasy.rcdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fantasy.rcdemo.adapter.RecyclerViewAdapter;
import com.fantasy.rcdemo.domain.DescribeItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private String[] text = new String[]{
            "他什么都没有留下来",
            "随着海水的颠簸",
            "去往未知的地域",
            "我是一只船",
    };

    private List<DescribeItem> itemList;

    private RecyclerView list;
    private MyAdapter adapter;
    private DescribeItem itemD ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    //初始化布局
    private void initView() {
        list = (RecyclerView) findViewById(R.id.list);

        //线性布局管理器
        list.setLayoutManager(new LinearLayoutManager(this));
//        //网格布局管理器
//        list.setLayoutManager(new GridLayoutManager(this,2));
//        //瀑布流布局管理器
//        list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        list.setItemAnimator(new DefaultItemAnimator());
        list.setHasFixedSize(true);
    }

    private void initData() {
        itemList = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            itemD= new DescribeItem(text[i]);
            itemList.add(itemD);
        }
        adapter = new MyAdapter(this, itemList);
        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "点击事件被触发,位置：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void OnItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"长按事件被触发,位置："+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyAdapter extends RecyclerViewAdapter<DescribeItem> {

        public MyAdapter(Context ctx, List<DescribeItem> l) {
            super(ctx, l);
        }

        @Override
        protected void delete(int position) {
            adapter.removeData(position);
        }

        @Override
        protected void add(int position) {
            Random random=new Random();
            int i = random.nextInt(3);
            DescribeItem item=new DescribeItem(text[i]);
            adapter.addData(position,item);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "还原").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.getItem(0).setIcon(R.drawable.ic_refresh_white_48dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        adapter.clear();
        for (int i = 0; i < text.length; i++) {
            itemD= new DescribeItem(text[i]);
            itemList.add(itemD);
        }
        adapter = new MyAdapter(this, itemList);
        list.setAdapter(adapter);
        return super.onOptionsItemSelected(item);
    }

}
