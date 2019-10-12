package Fragments;

import android.content.Context;
import android.os.Bundle;

import Adapters.Pager_Adapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import teamcool.mandeep.brunchify.R;

public class SelectInterests extends Fragment implements Business.OnFragmentInteractionListener, Social.OnFragmentInteractionListener, Tech.OnFragmentInteractionListener{

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private FragmentStatePagerAdapter adapter_vp;

    private OnFragmentInteractionListener mListener;

    public SelectInterests() {

    }

    public static SelectInterests newInstance(String param1) {
        SelectInterests fragment = new SelectInterests();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select__interests, container, false);
        ViewPager vpager = (ViewPager)view.findViewById(R.id.vpPager);
        adapter_vp = new Pager_Adapter(getFragmentManager());
        vpager.setAdapter(adapter_vp);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(String item) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String item);
    }

}
