package com.example.graduatetravell.Mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.graduatetravell.LoginActivity;
import com.example.graduatetravell.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mineListView;
    private View view;

    private List<MineListItemModal> mineListItemModals = new ArrayList<>();

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
        initData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        mineListView = view.findViewById(R.id.minelist);

        MineAdapter adapter = new MineAdapter(
                getContext() , R.layout.fragment_mine_base_item, mineListItemModals);
        mineListView.setAdapter(adapter);
        return view;
    }

    private void initData() {
        mineListItemModals.add(new MineListItemModal("UserName",R.drawable.head,0));
        mineListItemModals.add(new MineListItemModal("游记",R.drawable.mystory,1));
        mineListItemModals.add(new MineListItemModal("热门",R.drawable.myhot,1));
        mineListItemModals.add(new MineListItemModal("新闻",R.drawable.mynews,1));
    }

    @Override
    public void onStart() {
        super.onStart();
        //退出登录点击事件
        Button logout_Button = (Button) view.findViewById(R.id.logout_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(logoutIntent);
            }
        });
    }

}