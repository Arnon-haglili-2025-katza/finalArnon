package com.example.arnonfinalhta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DayViewHolder> {

    private final ArrayList<LocalDate> daysList;
    private final HashMap<String, String> matchesMap;
    private final Context context;

    public CalendarAdapter(Context context, ArrayList<LocalDate> daysList, HashMap<String, String> matchesMap) {
        this.context = context;
        this.daysList = daysList;
        this.matchesMap = matchesMap;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {

        LocalDate date = daysList.get(position);

        // 🔥 חובה! בדיקה ליום ריק (מניעת קריסה)
        if (date == null) {
            holder.tvDay.setText("");
            holder.tvDay.setBackgroundResource(R.drawable.day_background);
            holder.itemView.setOnClickListener(null);
            return;
        }

        // מציג את מספר היום
        holder.tvDay.setText(String.valueOf(date.getDayOfMonth()));

        String dateKey = date.toString(); // yyyy-MM-dd

        // רקע אם יש משחק
        if (matchesMap.containsKey(dateKey)) {
            holder.tvDay.setBackgroundResource(R.drawable.match_day_background);
        } else {
            holder.tvDay.setBackgroundResource(R.drawable.day_background);
        }

        // קליק על היום
        holder.itemView.setOnClickListener(v -> {
            if (matchesMap.containsKey(dateKey)) {
                Toast.makeText(
                        context,
                        "📅 " + matchesMap.get(dateKey),
                        Toast.LENGTH_LONG
                ).show();
            } else {
                Toast.makeText(context, "אין משחק בתאריך זה 🙂", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public static class DayViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay;

        public DayViewHolder(View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }
}
