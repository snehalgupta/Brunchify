package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import Fragments.SelectSlots;
import Models.Availability_Slot;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import teamcool.mandeep.brunchify.R;

public class Slots_RV_Adapter extends RecyclerView.Adapter<Slots_RV_Adapter.ViewHolder> {

    private final ArrayList<ArrayList<Availability_Slot>> slots;
    private ArrayList<Availability_Slot> selected_slots;
    static final String TAG = "SlotRV";
    private Context context;

    public Slots_RV_Adapter(Context cont, ArrayList<ArrayList<Availability_Slot>> arr){
        slots = arr;
        context = cont;
        selected_slots = new ArrayList<Availability_Slot>();
    }

    @Override
    public Slots_RV_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_item, parent, false);
        return new Slots_RV_Adapter.ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(final Slots_RV_Adapter.ViewHolder holder, int position) {
        holder.setData(slots.get(position));
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    public ArrayList<Availability_Slot> getSelectedChoices() {
        return selected_slots;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mview;
        public final Button btn1;
        public final Button btn2;
        public final Button btn3;
        public final Button btn4;
        public final TextView textView;
        public Context context;
        public Availability_Slot add_slot;

        public ViewHolder(View view, Context cont){
            super(view);
            mview = view;
            btn1 = (Button)view.findViewById(R.id.slot_1);
            btn2 = (Button)view.findViewById(R.id.slot_2);
            btn3 = (Button)view.findViewById(R.id.slot_3);
            btn4 = (Button)view.findViewById(R.id.slot_4);
            textView = (TextView)view.findViewById(R.id.slot_date);
            context = cont;
        }

        public void setData(final ArrayList<Availability_Slot> item) {

            //add_slot = new Availability_Slot(item.day,item.date,new ArrayList<String>());
            //selected_slots.add(add_slot);

            // Assuming all items are for the same day
            String te = item.get(0).day+" "+item.get(0).date;
            textView.setText(te);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Availability_Slot thisSlot = item.get(0);
                        Log.i(TAG,"selected");
                        //Boolean isSelected = add_slot.timings.contains(btn1.getText().toString());
                        boolean isSelected = selected_slots.contains(thisSlot);
                        if(isSelected){
                            btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            btn1.setTextColor(Color.BLACK);
                            //add_slot.timings.remove(btn1.getText().toString());
                            selected_slots.remove(thisSlot);
                        }
                        else{
                            //add_slot.timings.add(btn1.getText().toString());
                            selected_slots.add(thisSlot);
                            btn1.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            btn1.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Availability_Slot thisSlot = item.get(1);
                        Log.i(TAG,"selected");
                        //Boolean isSelected = add_slot.timings.contains(btn1.getText().toString());
                        boolean isSelected = selected_slots.contains(thisSlot);
                        if(isSelected){
                            btn2.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            btn2.setTextColor(Color.BLACK);
                            //add_slot.timings.remove(btn1.getText().toString());
                            selected_slots.remove(thisSlot);
                        }
                        else{
                            //add_slot.timings.add(btn1.getText().toString());
                            selected_slots.add(thisSlot);
                            btn2.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            btn2.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Availability_Slot thisSlot = item.get(2);
                        Log.i(TAG,"selected");
                        //Boolean isSelected = add_slot.timings.contains(btn1.getText().toString());
                        boolean isSelected = selected_slots.contains(thisSlot);
                        if(isSelected){
                            btn3.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            btn3.setTextColor(Color.BLACK);
                            //add_slot.timings.remove(btn1.getText().toString());
                            selected_slots.remove(thisSlot);
                        }
                        else{
                            //add_slot.timings.add(btn1.getText().toString());
                            selected_slots.add(thisSlot);
                            btn3.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            btn3.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
            btn4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    {
                        Availability_Slot thisSlot = item.get(3);
                        Log.i(TAG,"selected");
                        //Boolean isSelected = add_slot.timings.contains(btn1.getText().toString());
                        boolean isSelected = selected_slots.contains(thisSlot);
                        if(isSelected){
                            btn4.setBackground(ContextCompat.getDrawable(context, R.drawable.capsulewhite));
                            btn4.setTextColor(Color.BLACK);
                            //add_slot.timings.remove(btn1.getText().toString());
                            selected_slots.remove(thisSlot);
                        }
                        else{
                            //add_slot.timings.add(btn1.getText().toString());
                            selected_slots.add(thisSlot);
                            btn4.setBackground(ContextCompat.getDrawable(context, R.drawable.capsuleselected));
                            btn4.setTextColor(Color.WHITE);
                        }
                    }
                }
            });
        }

    }
}
