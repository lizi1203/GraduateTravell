package com.example.graduatetravell.Mine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        mineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Intent intentToMineNote = new Intent(getContext(),MineNoteActivity.class);
                        startActivity(intentToMineNote);
                        break;
                    case 2:
                        Intent intentToStoryHistory = new Intent(getContext(),StoryHistoryActivity.class);
                        startActivity(intentToStoryHistory);
                        break;
                    case 3:
                        Intent intentToRelaxHistory = new Intent(getContext(),RelaxHistoryActivity.class);
                        startActivity(intentToRelaxHistory);
                        break;
                    case 4:
                        Intent intentToNewsHistory = new Intent(getContext(),NewsHistoryActivity.class);
                        startActivity(intentToNewsHistory);
                        break;
                }
            }
        });


        MineAdapter adapter = new MineAdapter(
                getContext() , R.layout.fragment_mine_base_item, mineListItemModals);
        mineListView.setAdapter(adapter);
        return view;
    }

    private void initData() {
        mineListItemModals.add(new MineListItemModal(mParam2,R.drawable.head,0));
        mineListItemModals.add(new MineListItemModal("我发布的帖子",R.drawable.list,1));
        mineListItemModals.add(new MineListItemModal("热门浏览历史",R.drawable.star,1));
        mineListItemModals.add(new MineListItemModal("休闲浏览历史",R.drawable.hot,1));
        mineListItemModals.add(new MineListItemModal("新闻浏览历史",R.drawable.news,1));
    }

    @Override
    public void onStart() {
        super.onStart();
        //退出登录点击事件
        Button logout_Button = (Button) view.findViewById(R.id.logout_button);
        logout_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提示：");
        builder.setMessage("您确定要退出登录吗？");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(logoutIntent);
                Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        //设置反面按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setIcon(R.drawable.question);
        //设置对话框颜色
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();//获取dialog布局的参数
        dialog.getWindow().setBackgroundDrawableResource(R.color.grey);

        dialog.show();
        Button btnPositive = (Button)dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.setTextColor(Color.parseColor("#FF000000"));
        Button btnNegative = (Button)dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        btnNegative.setTextColor(Color.parseColor("#FF000000"));
    }

}