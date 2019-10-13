package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import Fragments.Business;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Business_RV_Adapter extends RecyclerView.Adapter<Business_RV_Adapter.ViewHolder> {

    private final ArrayList<String> interests;
    private ArrayList<String> selected_interests;
    private final Business.OnFragmentInteractionListener mlistener;
    static final String TAG = "BusinessRV";
    private Context context;

    public Business_RV_Adapter(Context cont, ArrayList<String> arr, Business.OnFragmentInteractionListener listener) {
        context = cont;
        interests = arr;
        mlistener = listener;
        selected_interests = new ArrayList<String>();
    }

    @Override
    public Business_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_button, parent, false);
        return new Business_RV_Adapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(final Business_RV_Adapter.ViewHolder holder, int position) {
        holder.setData(interests.get(position));
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    public ArrayList<String> getSelected_interests() {
        return selected_interests;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mview;
        public final Button mButton;
        public Context context;
        public String interest_;

        public ViewHolder(View view, Context cont) {
            super(view);
            mview = view;
            mButton = (Button) view.findViewById(R.id.business_button);
            context = cont;
        }

        public void setData(final String item) {
            this.interest_ = item;
            mButton.setText(interest_);
            mButton.setTextColor(Color.BLACK);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Log.i(TAG, "selected");
                        if (null != mlistener) {
                            mlistener.onFragmentInteraction(item);
                        }
                        Boolean isSelected = selected_interests.contains(interest_);
                        if (isSelected) {
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            mButton.setTextColor(Color.BLACK);
                            selected_interests.remove(interest_);
                        } else {
                            selected_interests.add(interest_);
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            mButton.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

    }
}
