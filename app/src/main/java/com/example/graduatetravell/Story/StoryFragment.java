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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.graduatetravell.LoginActivity;
import com.example.graduatetravell.Mine.MineListItemModal;
import com.example.graduatetravell.R;
import com.example.graduatetravell.RegisterActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;
import java.util.List;

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
    List<String> imageURL;
    List<String> imageTitle;

    //recyclerView部分数据
    private RecyclerView recyclerView;
    private StoryRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StoryRecyclerItemModal> storyRecyclerItemModals = new ArrayList<StoryRecyclerItemModal>();

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

        initBannerData();
        initRecyclerData();
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

    private void initRecyclerData() {
        for (int i=0;i<10;i++){
            StoryRecyclerItemModal item=new StoryRecyclerItemModal();
            item.setItemTitle("Test "+i);
            item.setItemAuthor("name");
            item.setIconURL("https://pic.qyer.com/album/user/3904/2/QkBVRhoFaEo/index/680x400");
            item.setItemHeadURL("https://dss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1200678983,2021774119&fm=11&gp=0.jpg");
            storyRecyclerItemModals.add(item);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_story, container, false);
        Banner banner = view.findViewById(R.id.story_banner);
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
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setLayoutManager(layoutManager);
        adapter = new StoryRecyclerAdapter(getContext(), storyRecyclerItemModals);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class MyLoader  extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide. with(context).load((String) path).into(imageView);
        }
    }



}