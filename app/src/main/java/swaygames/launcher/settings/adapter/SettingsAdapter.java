package swaygames.launcher.settings.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import swaygames.online.R;
import swaygames.online.gui.adapters.DonatAdapter;
import swaygames.online.gui.models.Donat;
import swaygames.launcher.settings.fragments.upsettingsfragment;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private final List<String> dataList;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;
    private int previousSelectedPosition = RecyclerView.NO_POSITION; // Переменная для хранения предыдущей выбранной позиции

    public SettingsAdapter(List<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        onStart(0);

        View itemView = inflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String item = dataList.get(position);
        holder.textView.setText(item);

        if (position == selectedPosition) {
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.textView.setTextColor(Color.parseColor("#4c5773"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSelectedPosition = selectedPosition;
                selectedPosition = position;

                notifyItemChanged(previousSelectedPosition);
                notifyItemChanged(selectedPosition);

                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void onStart(int position) {
        if(selectedPosition < 0 || selectedPosition > 2)
        {
            position = 0;
            selectedPosition = 0;
        }
    }

    public void updateItem(int position) {
        notifyItemChanged(position);
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setPreviousSelectedPosition(int position) {
        this.previousSelectedPosition = position;
    }

    public int getPreviousSelectedPosition() {
        return previousSelectedPosition;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.f);
            textView.setPadding(80, 25,0,0);
        }
    }

}
