package com.example.graduatetravell;


import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.graduatetravell.Mine.MineFragment;
import com.example.graduatetravell.News.NewsFragment;
import com.example.graduatetravell.Relax.RelaxFragment;
import com.example.graduatetravell.Story.StoryFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ViewPager mainViewPager;
    private RadioGroup mainTabRadioGroup;

    private List<Fragment> mainFragments;
    private FragmentPagerAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //find view
        mainViewPager = findViewById(R.id.main_viewPage);
        mainTabRadioGroup = findViewById(R.id.mainTabs_radioGroup);
        //init fragment
        mainFragments = new ArrayList<>(4);
        mainFragments.add(StoryFragment.newInstance("游记","1"));
        mainFragments.add(RelaxFragment.newInstance("热门","2"));
        mainFragments.add(NewsFragment.newInstance("新闻","3"));
        mainFragments.add(MineFragment.newInstance("我的","4"));
        //init ViewPager
        mainAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),4) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mainFragments == null?null:mainFragments.get(position);
            }

            @Override
            public int getCount() {
                return mainFragments == null? 0 : mainFragments.size();
            }
        };
        mainViewPager.setAdapter(mainAdapter);
        //注册监听者
        mainViewPager.addOnPageChangeListener(mainPageChangeListener);
        mainTabRadioGroup.setOnCheckedChangeListener(mainOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mainViewPager.removeOnPageChangeListener(mainPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mainPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton)mainTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private  RadioGroup.OnCheckedChangeListener mainOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for(int i = 0; i < group.getChildCount();i++){
                if(group.getChildAt(i).getId() == checkedId){
                    mainViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

}