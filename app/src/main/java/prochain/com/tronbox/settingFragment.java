package prochain.com.tronbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import prochain.com.tronbox.Views.SettingSrollView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link settingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link settingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingFragment extends android.support.v4.app.Fragment {


    private SettingSrollView mScrollView;

    private RelativeLayout mIv;
    private List<String> mList;


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
    }


    public void setData()
    {

    }
}
