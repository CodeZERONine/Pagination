package org.akshanshgusain.pagination;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private Adapter adapter;
    public ArrayList<String> mList;
    private Boolean isScrolling=false;//To Check for scrolling
    private int currentItems,totalItems,scrolledOutItems;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);
        mProgressbar=findViewById(R.id.progressbar);

        manager=new LinearLayoutManager(this);
        String s[]={"123","2","3","4","5","6","7","8888","7676","ege","5646","yyu54"};
        mList=new ArrayList(Arrays.asList(s));
        adapter=new Adapter(this,mList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        ////<____________________ADDING SCROLL LISTNER ON THE RECYCLERvIEW__________________________>
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {//<----- when scrolling starts
                super.onScrollStateChanged(recyclerView, newState);
                //check for scroll state
                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                                             isScrolling=true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {//<--------------------------- When the scrolling has stopped
                super.onScrolled(recyclerView, dx, dy);
                currentItems=manager.getChildCount();
                totalItems=manager.getItemCount();
                //scrolledOutItems=manager.findFirstVisibleItemPosition();
                scrolledOutItems=((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if(isScrolling && (currentItems+scrolledOutItems==totalItems)){
                    isScrolling=false;
                    dummyDataFetc();
                }

            }
        });//End of ScrollListner
    }//End of onCreate

    private void dummyDataFetc() {
        mProgressbar.setVisibility(View.VISIBLE);
        Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                      for(int i=0;i<3;i++){
                          mList.add(Math.floor(Math.random()*1000)+"");
                          adapter.notifyDataSetChanged();
                          mProgressbar.setVisibility(View.GONE);
                      }
                }
            }, 6000);

    }
}//End of main

