package swaygames.online.gui.tollesonpass.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import swaygames.launcher.activity.MainActivity;
import swaygames.online.R;
import swaygames.online.gui.adapters.DonatAdapter;
import swaygames.online.gui.adapters.DonateAdapter;
import swaygames.online.gui.models.Donat;
import swaygames.launcher.settings.fragments.upsettingsfragment;
import swaygames.online.gui.tollesonpass.holders.Rewards;

import java.util.List;

public class MainRewardsAdapter extends RecyclerView.Adapter<MainRewardsAdapter.ViewHolder> {

    Context context;
    List<Rewards> dataList;
    OnItemClickListener listener;

    public MainRewardsAdapter(Context context, List<Rewards> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(context).inflate(R.layout.tolleson_pass_main_reward_item, parent, false);
        return new MainRewardsAdapter.ViewHolder(item_view);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.button_click));
                switch (dataList.get(position).getId()) {
                    case 1: Toast.makeText(context, "Все робит", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public AppCompatButton day_button;

        public ViewHolder(@NonNull View itemView){

            super(itemView);
            imageView = itemView.findViewById(R.id.imageView19);
            day_button = itemView.findViewById(R.id.day_button);
        }
    }

}
