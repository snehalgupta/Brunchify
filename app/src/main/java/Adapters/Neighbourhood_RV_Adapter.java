package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import Fragments.Select_Neighbourhood;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Neighbourhood_RV_Adapter extends RecyclerView.Adapter<Neighbourhood_RV_Adapter.ViewHolder> {

    private final ArrayList<String> places;
    private ArrayList<String> selected_places;
    private final Select_Neighbourhood.OnFragmentInteractionListener mlistener;
    static final String TAG = "NeighbourhoodRV";
    private Context context;

    public Neighbourhood_RV_Adapter(Context cont, ArrayList<String> arr, Select_Neighbourhood.OnFragmentInteractionListener listener){
        places = arr;
        mlistener = listener;
        context = cont;
        selected_places = new ArrayList<String>();
    }

    @Override
    public Neighbourhood_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neighbourhood_button, parent, false);
        return new Neighbourhood_RV_Adapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final Neighbourhood_RV_Adapter.ViewHolder holder, int position) {
        holder.setData(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mview;
        public final Button mButton;
        public Context context;
        public String place;

        public ViewHolder(View view, Context cont){
            super(view);
            mview = view;
            mButton = (Button)view.findViewById(R.id.neigh_button);
            context = cont;
        }

        public void setData(final String item) {
            this.place = item;
            mButton.setText(place);
            mButton.setTextColor(Color.BLACK);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Log.i(TAG,"selected");
                        if (null != mlistener) {
                            mlistener.onFragmentInteraction(item);
                        }
                        Boolean isSelected = selected_places.contains(place);
                        if(isSelected){
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            mButton.setTextColor(Color.BLACK);
                            selected_places.remove(place);
                        }
                        else{
                            selected_places.add(place);
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            mButton.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

    }

}
