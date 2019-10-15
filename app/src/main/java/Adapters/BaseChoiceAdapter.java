package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import teamcool.mandeep.brunchify.R;

//public abstract class BaseChoiceAdapter<Choice, VH extends BaseChoiceAdapter.BaseChoiceViewHolder> extends RecyclerView.Adapter<VH> {
public class BaseChoiceAdapter<Choice> extends RecyclerView.Adapter<BaseChoiceAdapter.BaseChoiceViewHolder> implements onSelectionListener{
    protected final ArrayList<Choice> allChoices;
    protected ArrayList<Choice> selectedChoices;
    static String TAG = BaseChoiceAdapter.class.getSimpleName();
    protected Context context;
    protected int layoutId;

    public BaseChoiceAdapter(Context cont, ArrayList<Choice> arr, int layoutId) {
        context = cont;
        allChoices = arr;
        selectedChoices = new ArrayList<Choice>();
        this.layoutId = layoutId;
    }

    @Override
    public BaseChoiceAdapter.BaseChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId, parent, false);
            return new BaseChoiceViewHolder<Choice>(view, this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseChoiceAdapter.BaseChoiceViewHolder holder, int position) {
        holder.setView(allChoices.get(position));
    }


    @Override
    public int getItemCount() {
        return allChoices.size();
    }

    public ArrayList<Choice> getSelectedChoices() {
        return selectedChoices;
    }

    public void setSelectedChoices(ArrayList<Choice> selectedChoices) {
        this.selectedChoices = selectedChoices;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(int pos){
        if (selectedChoices.contains(allChoices.get(pos))){
            selectedChoices.remove(allChoices.get(pos));
        }
        else{
            selectedChoices.add(allChoices.get(pos));
        }
    }

    public class BaseChoiceViewHolder<ViewItem> extends RecyclerView.ViewHolder {
        public final View mview;
        public final Button mButton;
        public Context context;
        public ViewItem choice;

        public BaseChoiceViewHolder(View view, Context cont) {
            super(view);
            mview = view;
            mButton = (Button) view.findViewById(R.id.oval_select_button);
            context = cont;
        }

        protected String getText(ViewItem choice){
            return choice.toString();
        }

        private void renderSelection(){
            boolean isSelected = selectedChoices.contains(this.choice);
            if (isSelected) {
                mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                mButton.setTextColor(Color.WHITE);
            } else {
                mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                mButton.setTextColor(Color.BLACK);
            }
        }

        public void setView(final ViewItem item) {
            Log.i(TAG, "selected");
            this.choice = item;
            mButton.setText(getText(item));
            renderSelection();
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        BaseChoiceAdapter.this.onClick(getAdapterPosition());
                        renderSelection();
                    }
                }
            });
        }

    }
}

interface onSelectionListener{
    void onClick(int pos);
}
