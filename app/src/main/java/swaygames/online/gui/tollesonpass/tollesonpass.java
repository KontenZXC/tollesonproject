package swaygames.online.gui.tollesonpass;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nvidia.devtech.NvEventQueueActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import swaygames.online.R;
import swaygames.online.gui.adapters.DonateAdapter;
import swaygames.online.gui.models.Donat;
import swaygames.online.gui.models.Donatee;
import swaygames.online.gui.tollesonpass.adapters.MainRewardsAdapter;
import swaygames.online.gui.tollesonpass.holders.Rewards;

public class tollesonpass extends AppCompatActivity {

    static RecyclerView mainrewards, bonus;
    static MainRewardsAdapter rewardsAdapter;
    public Activity activity;
    public ConstraintLayout calendar, maincontainer;
    public tollesonpass(Activity aactivity) {
        activity = aactivity;
        calendar = activity.findViewById(R.id.holder_calendar);
        maincontainer = activity.findViewById(R.id.containermain);
        bonus = activity.findViewById(R.id.bonuscontainer);
        setListeners(aactivity);
    }
    public void setListeners(Activity aactivity) {
        activity = aactivity;
    }
    public void show() {
        maincontainer = activity.findViewById(R.id.containermain);
        maincontainer.setVisibility(View.VISIBLE);
        NvEventQueueActivity.getInstance().togglePlayer(1);
    }
    public void close() {
        maincontainer.setVisibility(View.GONE);
        NvEventQueueActivity.getInstance().togglePlayer(0);
    }
    private void setCalendarRecycler(List<Rewards> dataList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        mainrewards = activity.findViewById(R.id.mainrewardscontainer);
        mainrewards.setLayoutManager(layoutManager);

        rewardsAdapter = new MainRewardsAdapter(activity, dataList);
        mainrewards.setAdapter(rewardsAdapter);
    }

    public void test() {
        List<Rewards> dataList = new ArrayList<>();
        dataList.add(new Rewards(1, "123", ""));
        setCalendarRecycler(dataList);
        mainrewards.setVisibility(View.VISIBLE);
    }

}
