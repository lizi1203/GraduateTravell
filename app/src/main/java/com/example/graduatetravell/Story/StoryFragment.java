package com.example.graduatetravell.Story;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatetravell.LoginActivity;
import com.example.graduatetravell.Manager.DataManager;
import com.example.graduatetravell.Manager.UserNameApplication;
import com.example.graduatetravell.Mine.MineListItemModal;
import com.example.graduatetravell.Mine.MineNoteRecyclerModal;
import com.example.graduatetravell.News.NewsAdapter;
import com.example.graduatetravell.News.NewsListItemModal;
import com.example.graduatetravell.News.NewsResultBean;
import com.example.graduatetravell.R;
import com.example.graduatetravell.RegisterActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.Logger;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //banner部分数据
    private Banner banner;
    private List<String> imageURL;
    private List<String> imageTitle;

    //recyclerView部分数据
    private RecyclerView recyclerView;
    private ArrayList<StoryRecyclerItemModal> storyRecyclerItemModals = new ArrayList<StoryRecyclerItemModal>();
    private StoryRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UserNameApplication app;

    Handler handler;

    //上拉加载模块
    RefreshLayout refreshLayout;
    //控制加载数据的url
    long loadStart;
    long sqlLoadStart = 1;

    public StoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryFragment newInstance(String param1, String param2) {
        StoryFragment fragment = new StoryFragment();
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
        loadStart = 1;
        initBannerData();
        getSQLRecyclerData();
        initRecyclerData(loadStart);
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 1)//拉取到网页JSON
                {
                    storyRecyclerItemModals.addAll((ArrayList< StoryRecyclerItemModal>) msg.obj);
                    adapter.notifyDataSetChanged();
                }
                else if(msg.what == 9){//拉取到数据库JSON
                    ArrayList<StoryRecyclerItemModal> headAddRecyclerItemModals = new ArrayList<StoryRecyclerItemModal>();
                    headAddRecyclerItemModals.addAll((ArrayList< StoryRecyclerItemModal>) msg.obj);
                    headAddRecyclerItemModals.addAll(storyRecyclerItemModals);
                    storyRecyclerItemModals.clear();
                    storyRecyclerItemModals.addAll(headAddRecyclerItemModals);
                    adapter.notifyDataSetChanged();
                }
                else
                {

                }
            }

        };
    }

    private void initBannerData() {
        imageURL = new ArrayList<String>();
        imageURL.add("https://pic.qyer.com/album/user/3904/2/QkBVRhoFaEo/index/680x400");
        imageURL.add("https://pic.qyer.com/album/user/3956/11/QkBQRBsGaEg/index/680x400");
        imageURL.add("https://pic.qyer.com/album/user/3955/47/QkBQRx4AYUA/index/680x400");

        imageTitle = new ArrayList<String>();
        imageTitle.add("【西域梦游记】故土新归 | 新疆姑娘带你赏遍南疆秘境！（金秋国庆深度自驾攻略)");
        imageTitle.add("烟火中的未知地带——2021，我在【苗乡侗寨】自驾过年 (贵州 黔东南 18个村寨记事)");
        imageTitle.add("巴厘岛 | 总有一个假日，要属于bali");
    }

    //上拉加载的算法
    private void initRecyclerData(long nextStart) {
        //面包旅行网络数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://api.breadtrip.com/v2/index/?next_start="+ nextStart;

                try {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(url)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        //拿到数组
                        JsonObject jsonObject = new JsonParser().parse(responseData).getAsJsonObject();
                        JsonObject data = jsonObject.get("data").getAsJsonObject();


                        StoryResultBean storyResultBean = new Gson().fromJson(data,StoryResultBean.class);
                        //对象中拿到集合
                        List<StoryResultBean.DataBean> storyBeanList = storyResultBean.getElements();
                        loadStart = storyResultBean.getNext_start();

                        ArrayList<StoryRecyclerItemModal> tempItemModals = new ArrayList<>();
                        for(StoryResultBean.DataBean dataBean : storyBeanList){
                            List<StoryResultBean.StoryBean> realData = dataBean.getData();
                            for(StoryResultBean.StoryBean storyBean: realData){
                                StoryRecyclerItemModal newModal = new StoryRecyclerItemModal(storyBean.getName(),storyBean.getCover_image_default(),storyBean.getUser().getName(),storyBean.getUser().getAvatar_l(),storyBean.getId(),null);
                                tempItemModals.add(newModal);
                            }

                        }

                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 1;
                        message.obj = tempItemModals;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void getSQLRecyclerData(/*long nextStart*/) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://10.0.2.2:8080/AndroidTest/mustDownload?chartname=note&loadStart=1" /*+nextStart*/;
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(path)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    String responseData = response.body().string();
                    if (responseData!=null) {
                        //Json的解析类对象
                        JsonParser parser = new JsonParser();
                        //将JSON的String 转成一个JsonArray对象
                        JsonArray jsonArray = parser.parse(responseData).getAsJsonArray();
                        Gson gson = new Gson();
                        ArrayList<SqlReturnBean> sqlReturnBeans = new ArrayList<>();
                        ArrayList<StoryRecyclerItemModal> tempItemModals = new ArrayList<>();
                        //加强for循环遍历JsonArray
                        for (JsonElement note : jsonArray) {
                            //使用GSON，直接转成Bean对象
                            SqlReturnBean sqlReturnBean = gson.fromJson(note, SqlReturnBean.class);
                            sqlReturnBeans.add(sqlReturnBean);
//                            sqlBeanToModal(sqlReturnBean,tempItemModals);
                            StoryRecyclerItemModal newModal = new StoryRecyclerItemModal(sqlReturnBean.getTitle(),sqlReturnBean.getCover_image_default(),sqlReturnBean.getUsername(),sqlReturnBean.getUserhead(),String.valueOf(sqlReturnBean.getNoteID()),sqlReturnBean.getContent());
                            tempItemModals.add(newModal);
                            loadStart = sqlReturnBean.getNoteID()+1;
                        }

                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 1;
                        message.obj = tempItemModals;
                        handler.sendMessage(message);
                    }else
                    {
                    }

                } catch (MalformedURLException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        View header = inflater.inflate(R.layout.banner_item, container, false);
        banner = header.findViewById(R.id.story_banner);
        banner.setBannerStyle(BannerConfig. CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader( new MyLoader());
        banner.setBannerTitles(imageTitle);
        banner.setImages( imageURL);
        banner.setBannerAnimation(Transformer. DepthPage);
        banner.setDelayTime( 3000);
        banner.isAutoPlay( true);
        banner.setIndicatorGravity(BannerConfig. CENTER);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent webIntent = new Intent(getActivity(), WebActivity.class);
                webIntent.putExtra("position",position);
                startActivity(webIntent);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.card_recyclerView);
        layoutManager = new GridLayoutManager(getContext(),2);
        int spanCount = 2; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge,false));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoryRecyclerAdapter(getContext(), storyRecyclerItemModals);
        //设置headerview
        adapter.setHeaderView(banner);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //加载模块
        refreshLayout = view.findViewById(R.id.story_refreshlayout);
        BallPulseFooter footer = new BallPulseFooter(getContext());
        footer.setAnimatingColor(getResources().getColor(R.color.white));
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()).setAccentColor(getResources().getColor(R.color.white)));
        refreshLayout.setRefreshFooter(footer);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadFromMysql(sqlLoadStart);
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                initRecyclerData(loadStart);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        refreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作

        return view;
    }

    private class MyLoader  extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide. with(context).load((String) path).into(imageView);
        }
    }

    //下拉刷新的方法
    private void loadFromMysql(long nextStart) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://10.0.2.2:8080/AndroidTest/mustGetNote?loadStart=" +nextStart;
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
                            .readTimeout(5000, TimeUnit.MILLISECONDS)
                            .build();//创建OkHttpClient对象
                    Request request = new Request.Builder()
                            .url(path)//请求接口。如果需要传参拼接到接口后面。
                            .build();//创建Request 对象
                    Response response = null;
                    response = client.newCall(request).execute();//得到Response 对象
                    String responseData = response.body().string();
                    if (responseData!=null) {
//                        //Json的解析类对象
//                        JsonParser parser = new JsonParser();
//                        //将JSON的String 转成一个JsonArray对象
//                        JsonArray jsonArray = parser.parse(responseData).getAsJsonArray();
//                        Gson gson = new Gson();
//                        ArrayList<SqlReturnBean> sqlReturnBeans = new ArrayList<>();
//                        ArrayList<StoryRecyclerItemModal> tempItemModals = new ArrayList<>();
//                        //加强for循环遍历JsonArray
//                        for (JsonElement note : jsonArray) {
//                            //使用GSON，直接转成Bean对象
//                            SqlReturnBean sqlReturnBean = gson.fromJson(note, SqlReturnBean.class);
//                            sqlReturnBeans.add(sqlReturnBean);
//                            sqlBeanToModal(sqlReturnBean,tempItemModals);
//                        }
                        ArrayList<StoryRecyclerItemModal> tempItemModals = new ArrayList<>();
                        String filePath = getContext().getFilesDir().getAbsolutePath();
                        app = (UserNameApplication) getContext().getApplicationContext(); //获取应用程序
                        File file = new File(filePath + "/" + app.getUserName()) ;
                        File file2 = new File(file.getAbsoluteFile()  + "/MineNote.txt") ;
                        ObjectInputStream objectInputStream = null;
                        try {
                            objectInputStream = new ObjectInputStream(new FileInputStream(file2));
                            ArrayList<MineNoteRecyclerModal> tempMineItemModals = new ArrayList<>();
                            tempMineItemModals = (ArrayList<MineNoteRecyclerModal>) objectInputStream.readObject();
                            for (MineNoteRecyclerModal item : tempMineItemModals) {
                                //使用GSON，直接转成Bean对象
                                StoryRecyclerItemModal tempModal = new StoryRecyclerItemModal(item.getTitle(),item.getImage(),item.getUserName(),null,null,item.getContent());
                                tempItemModals.add(tempModal);
                            }
                            objectInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                            //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 9;
                        message.obj = tempItemModals;
                        handler.sendMessage(message);
                    }else
                    {
                        Looper.prepare();
                        Toast.makeText(getContext(),"没有更新的内容！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                } catch (MalformedURLException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void sqlBeanToModal(SqlReturnBean sqlReturnBean ,ArrayList<StoryRecyclerItemModal> tempItemModals) {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        String json = sqlReturnBean.getContent();
        JsonArray jsonArray = parser.parse(json).getAsJsonArray();
        Gson gson = new Gson();
        ArrayList<EditBean> editBeans = new ArrayList<>();
        //加强for循环遍历JsonArray
        for (JsonElement item : jsonArray) {
            //使用GSON，直接转成Bean对象
            EditBean editBean = gson.fromJson(item, EditBean.class);
            editBeans.add(editBean);
        }
//        location = storyDetailBean.getCities().get(0);
        EditBean titleBean = editBeans.get(0);
        StoryRecyclerItemModal newModal = new StoryRecyclerItemModal(titleBean.getEditText(),titleBean.getImagePath(),sqlReturnBean.getUsername(),null,null,sqlReturnBean.getContent());
        tempItemModals.add(newModal);
    }

}