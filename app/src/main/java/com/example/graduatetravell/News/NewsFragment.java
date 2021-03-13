package com.example.graduatetravell.News;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduatetravell.Manager.DataManager;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.StoryRecyclerAdapter;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsListItemModal> newsListItemModalArrayList = new ArrayList<>();

    Handler handler;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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

        initRecyclerViewData();
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 3)
                {
                    newsListItemModalArrayList = (ArrayList<NewsListItemModal>) msg.obj;
                    adapter = new NewsAdapter(getContext(),newsListItemModalArrayList);
                    adapter.notifyDataSetChanged();
                }
                else
                {

                }
            }

        };
    }

    private void initRecyclerViewData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url("http://news-at.zhihu.com/api/3/news/latest")//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();

                        NewsResultBean resultBean = new Gson().fromJson(responseData,NewsResultBean.class);
                        //对象中拿到集合
                        List<NewsResultBean.StoryBean> storyBeanList = resultBean.getStories();
                        List<NewsResultBean.StoryBeanT> storyBeanTList = resultBean.getTop_stories();

                        newsListItemModalArrayList = new ArrayList<>();
                        for(NewsResultBean.StoryBean storyBean : storyBeanList){
                            NewsListItemModal newModal = new NewsListItemModal(storyBean.getTitle(),storyBean.getHint(),storyBean.getImages().get(0),storyBean.getUrl());
                            newsListItemModalArrayList.add(newModal);
                        }

                        for(NewsResultBean.StoryBeanT storyBeanT : storyBeanTList){
                            NewsListItemModal newModal = new NewsListItemModal(storyBeanT.getTitle(),storyBeanT.getHint(),storyBeanT.getImage(),storyBeanT.getUrl());
                            newsListItemModalArrayList.add(newModal);
                        }
                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 3;
                        message.obj = newsListItemModalArrayList;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.news_recyclerView);
        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return view;
    }
}