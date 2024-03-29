package com.example.graduatetravell.Relax;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.graduatetravell.R;
import com.example.graduatetravell.Story.GridSpacingItemDecoration;
import com.example.graduatetravell.Story.SqlReturnBean;
import com.example.graduatetravell.Story.StoryFragment;
import com.example.graduatetravell.Story.StoryRecyclerAdapter;
import com.example.graduatetravell.Story.StoryRecyclerItemModal;
import com.example.graduatetravell.Story.StoryResultBean;
import com.example.graduatetravell.Story.WebActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RelaxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RelaxFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //banner部分数据
    Banner banner;
    List<String> imageURL;
    List<String> imageTitle;

    //recyclerView部分数据
    private RecyclerView recyclerView;
    private RelaxRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StoryRecyclerItemModal> relaxRecyclerItemModals = new ArrayList<StoryRecyclerItemModal>();
    Handler handler;

    //上拉加载模块
    RefreshLayout refreshLayout;
    //控制加载数据的url
    private long loadStart;

    public RelaxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RelaxFragment newInstance(String param1, String param2) {
        RelaxFragment fragment = new RelaxFragment();
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
        loadStart = 0;
        initBannerData();
        initRecyclerData(loadStart);
        handler = new Handler(){
            public void handleMessage(Message msg)
            {
                if (msg.what == 2)
                {
                    relaxRecyclerItemModals.addAll((ArrayList<StoryRecyclerItemModal>) msg.obj);
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
        imageURL.add("https://pic.qyer.com/album/user/3956/18/QkBQRBsPYUg/index/680x400");
        imageURL.add("https://pic.qyer.com/album/user/3954/53/QkBQRh8EZkg/index/680x400");
        imageURL.add("https://pic.qyer.com/album/user/3922/55/QkBXQB8CY0k/index/680x400");

        imageTitle = new ArrayList<String>();
        imageTitle.add("嘿，樱花~请你等等我！三刷日本终于看到樱花满开的真面目");
        imageTitle.add("一路向南，为我们知道的厦门、为我们不知道漳州、广州、深圳");
        imageTitle.add("【一休妈】川西南行纪，没错，就是丁真的家乡");
    }

    private void initRecyclerData(long loadStart) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://api.breadtrip.com/v2/new_trip/spot/hot/list/?start="+loadStart;

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


                        RelaxResultBean relaxResultBean = new Gson().fromJson(data,RelaxResultBean.class);
                        //对象中拿到集合
                        List<RelaxResultBean.DataBean> relaxBeanList = relaxResultBean.getHot_spot_list();


                        ArrayList<StoryRecyclerItemModal> tempItemModals = new ArrayList<>();
                        for(RelaxResultBean.DataBean dataBean : relaxBeanList){
                                StoryRecyclerItemModal newModal = new StoryRecyclerItemModal(dataBean.getText(),dataBean.getIndex_cover(),dataBean.getUser().getName(),dataBean.getUser().getAvatar_l(),dataBean.getSpot_id(),null);
                            tempItemModals.add(newModal);
                        }

                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
                        Message message = new Message();
                        message.what = 2;
                        message.obj = tempItemModals;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


        //数据库数据

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String path = "http://10.0.2.2:8080/AndroidTest/mustDownload?chartname=relax&loadStart=" +loadStart;
//                    OkHttpClient client = new OkHttpClient.Builder()
//                            .connectTimeout(5000, TimeUnit.MILLISECONDS)
//                            .readTimeout(5000, TimeUnit.MILLISECONDS)
//                            .build();//创建OkHttpClient对象
//                    Request request = new Request.Builder()
//                            .url(path)//请求接口。如果需要传参拼接到接口后面。
//                            .build();//创建Request 对象
//                    Response response = null;
//                    response = client.newCall(request).execute();//得到Response 对象
//                    String responseData = response.body().string();
//                    if (responseData!=null) {
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
////                            sqlBeanToModal(sqlReturnBean,tempItemModals);
//                            StoryRecyclerItemModal newModal = new StoryRecyclerItemModal(sqlReturnBean.getTitle(),sqlReturnBean.getCover_image_default(),sqlReturnBean.getUsername(),sqlReturnBean.getUserhead(),null,sqlReturnBean.getContent());
//                            tempItemModals.add(newModal);
//                            nextStart = sqlReturnBean.getNoteID()+1;
//                        }
//
//                        //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。
//                        Message message = new Message();
//                        message.what = 2;
//                        message.obj = tempItemModals;
//                        handler.sendMessage(message);
//                    }else
//                    {
//                    }
//
//                } catch (MalformedURLException e) {
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_relax, container, false);
        View header = inflater.inflate(R.layout.banner_item, container, false);
        banner = header.findViewById(R.id.story_banner);
        banner.setBannerStyle(BannerConfig. CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader( new RelaxFragment.MyLoader());
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
                webIntent.putExtra("fragment","relaxFragment");
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
        adapter = new RelaxRecyclerAdapter(getContext(), relaxRecyclerItemModals);
        //设置headerview
        adapter.setHeaderView(banner);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        //加载模块
        refreshLayout = view.findViewById(R.id.relax_refreshlayout);
        BallPulseFooter footer = new BallPulseFooter(getContext());
        footer.setAnimatingColor(getResources().getColor(R.color.white));
        refreshLayout.setRefreshFooter(footer);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadStart +=12;
                initRecyclerData(loadStart);
                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setEnableRefresh(false);//是否启用下拉刷新功能
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
}