package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import Fragments.Select_Objectives;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Objectives_RV_Adapter extends RecyclerView.Adapter<Objectives_RV_Adapter.ViewHolder> {

    private final ArrayList<String> objectives;
    private ArrayList<String> selected_objectives;
    private final Select_Objectives.OnFragmentInteractionListener mlistener;
    static final String TAG = "ObjectiveRV";
    private Context context;

    public Objectives_RV_Adapter(Context cont, ArrayList<String> arr, Select_Objectives.OnFragmentInteractionListener listener){
        objectives = arr;
        mlistener = listener;
        context = cont;
        selected_objectives = new ArrayList<String>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.objectives_button, parent, false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.setData(objectives.get(position));
    }

    @Override
    public int getItemCount() {
        return objectives.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mview;
        public final Button mButton;
        public Context context;
        public String objective;

        public ViewHolder(View view, Context cont){
            super(view);
            mview = view;
            mButton = (Button)view.findViewById(R.id.obj_button);
            context = cont;
        }

        public void setData(final String item) {
            this.objective = item;
            mButton.setText(objective);
            mButton.setTextColor(Color.BLACK);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Log.i(TAG,"selected");
                        if (null != mlistener) {
                            mlistener.onFragmentInteraction(item);
                        }
                        Boolean isSelected = selected_objectives.contains(objective);
                        if(isSelected){
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            mButton.setTextColor(Color.BLACK);
                            selected_objectives.remove(objective);
                        }
                        else{
                            selected_objectives.add(objective);
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            mButton.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

    }
}