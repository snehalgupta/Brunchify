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
public class BaseChoiceAdapter<Choice> extends RecyclerView.Adapter<BaseChoiceAdapter.BaseChoiceViewHolder> {
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

    public class BaseChoiceViewHolder<ViewItem extends Choice> extends RecyclerView.ViewHolder {
        public final View mview;
        public final Button mButton;
        public Context context;
        public ViewItem choice;

        public BaseChoiceViewHolder(View view, Context cont) {
            super(view);
            mview = view;
            mButton = (Button) view.findViewById(R.id.business_button);
            context = cont;
        }

        protected String getText(ViewItem choice){
            return choice.toString();
        }

        public void setView(final ViewItem item) {
            this.choice = item;
            mButton.setText(getText(item));
            mButton.setTextColor(Color.BLACK);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Log.i(TAG, "selected");
                        Boolean isSelected = selectedChoices.contains(item);
                        if (isSelected) {
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            mButton.setTextColor(Color.BLACK);
                            selectedChoices.remove(item);
                        } else {
                            selectedChoices.add(item);
                            mButton.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            mButton.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

    }
}
