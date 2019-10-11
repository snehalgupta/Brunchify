package Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import Adapters.Neighbourhood_RV_Adapter;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Select_Neighbourhood extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private RecyclerView recyclerView;
    private ArrayList<String> delhi;
    private ArrayList<String> bangalore;
    private OnFragmentInteractionListener mListener;
    String selected_city;

    public Select_Neighbourhood() {
    }

    public static Select_Neighbourhood newInstance(String param1) {
        Select_Neighbourhood fragment = new Select_Neighbourhood();
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

        View view = inflater.inflate(R.layout.fragment_select__neighbourhood, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view_neighbourhood);
        FlexboxLayoutManager layoutm = new FlexboxLayoutManager(getContext());
        layoutm.setFlexDirection(FlexDirection.ROW);
        layoutm.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutm);
        delhi = new ArrayList<String>();
        delhi.add("CP");
        delhi.add("CyberHub");
        delhi.add("SocialCops");
        bangalore = new ArrayList<String>();
        bangalore.add("Kormangala");
        bangalore.add("EGL");
        bangalore.add("Belandur");
        Spinner dropdown = (Spinner)view.findViewById(R.id.neighbourhood_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.cities_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Object item = adapterView.getItemAtPosition(i);
        selected_city = item.toString();
        ArrayList<String> arr;
        if(selected_city.equals("Delhi")){
            arr = delhi;
        }
        else{
            arr = bangalore;
        }
        Neighbourhood_RV_Adapter adapter = new Neighbourhood_RV_Adapter(getContext(),arr,mListener);
        recyclerView.setAdapter(adapter);
        //Toast.makeText(getContext(),selected_city,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //Toast.makeText(getContext(),"Select city",Toast.LENGTH_LONG).show();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String item);
    }

}
