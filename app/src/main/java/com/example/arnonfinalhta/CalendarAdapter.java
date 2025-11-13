package com.example.arnonfinalhta;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    public interface OnDayClickListener {
        void onDayClick(String date, String matchInfo);
    }

    private final List<String> daysList;
    private final Map<String, String> matchesMap;
    private final OnDayClickListener listener;

    public CalendarAdapter(List<String> daysList, Map<String, String> matchesMap, OnDayClickListener listener) {
        this.daysList = daysList;
        this.matchesMap = matchesMap;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day = daysList.get(position);
        holder.tvDay.setText(day);

        if (matchesMap.containsKey(day)) {
            holder.tvDay.setBackgroundResource(R.drawable.match_day_background); // לציין שמות משאבים
        } else {
            holder.tvDay.setBackgroundResource(R.drawable.day_background);
        }

        holder.itemView.setOnClickListener(v -> {
            String info = matchesMap.getOrDefault(day, "אין משחק בתאריך זה 🙂");
            listener.onDayClick(day, info);
        });
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;
        FrameLayout container;

        DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            container = (FrameLayout) itemView;
        }
    }
}
