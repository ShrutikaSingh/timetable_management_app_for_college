package com.example.adwait.schedulemanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DayAdaptor extends RecyclerView.Adapter<DayAdaptor.DayHolder> {


    private Context context;
    private List<CardData> days;

    public DayAdaptor(Context context, List<CardData> days) {
        this.context = context;
        this.days = days;
    }

    @Override
    public DayHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.days,null);
        DayHolder holder = new DayHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {

        CardData sample = days.get(position);

        holder.slot.setText(sample.getSlot());
        holder.session.setText(sample.getSession());
        holder.fac.setText(sample.getFaculty());
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class DayHolder extends RecyclerView.ViewHolder{
        TextView slot,session,fac;

        public DayHolder(View itemView) {
            super(itemView);

            slot = (TextView)itemView.findViewById(R.id.timeslot);
            session = (TextView)itemView.findViewById(R.id.Session);
            fac = (TextView)itemView.findViewById(R.id.faculty);

        }
    }
}
