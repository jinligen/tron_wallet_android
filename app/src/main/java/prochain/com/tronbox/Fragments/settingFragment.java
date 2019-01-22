package prochain.com.tronbox.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import prochain.com.tronbox.R;
import prochain.com.tronbox.setting.SettingAdapter;
import prochain.com.tronbox.setting.SettingListInfo;
import prochain.com.tronbox.Views.SettingSrollView;
import prochain.com.tronbox.main.TestCenterActivity;
import prochain.com.tronbox.setting.walletSettingActivity;


public class settingFragment extends android.support.v4.app.Fragment {


    private SettingSrollView mScrollView;

    private RelativeLayout mIv;
    private List<SettingListInfo> mList;


    public settingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static settingFragment newInstance(String param1, String param2) {
        settingFragment fragment = new settingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_setting, container, false);
        mScrollView = v.findViewById(R.id.scrollView);

        View header = View.inflate(getActivity(), R.layout.setting_listview_header, null);
        mIv = header.findViewById(R.id.layout_header);

        mScrollView.setZoomImageView(mIv);
        mScrollView.addHeaderView(header);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public void onStart() {
        super.onStart();
        View v = getView();


        mList = new ArrayList<>();

        setData();


        SettingAdapter adapter = new SettingAdapter(getActivity(), mList);
        mScrollView.setAdapter(adapter);

        mScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                switch (position) {
                    case 0:
                    {
                        startTest();
                        break;
                    }
                    case 1: {
                        startSetting();
                        break;
                    }

                    default:
                        break;

                }


            }
        });

    }


    public void setData()
    {
        SettingListInfo info1 = new SettingListInfo(R.mipmap.setting_wallet_icon,"钱包管理", "", R.mipmap.setting_enter_icon);
        mList.add(info1);

    }


    private void startTest()
    {
        Intent intent = new Intent(getActivity(), TestCenterActivity.class);
        startActivity(intent);
    }

    private void startSetting()
    {
        Intent intent = new Intent(getActivity(), walletSettingActivity.class);
        startActivity(intent);
    }
}
