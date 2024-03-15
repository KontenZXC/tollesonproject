package swaygames.launcher.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.slider.Slider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import swaygames.launcher.settings.adapter.RecMovementAdapter;
import swaygames.online.R;
import swaygames.launcher.settings.adapter.SettingsAdapter;
import swaygames.launcher.settings.fragments.upsettingsfragment;
import swaygames.launcher.settings.fragments.volumesettingsfragment;
import com.google.android.gms.dynamic.SupportFragmentWrapper;

import java.util.ArrayList;
import java.util.List;

public class SettAct extends AppCompatActivity {

    // ALL MADE BY @BOPKAEMRU

    private RecyclerView recyclerView;
    private SettingsAdapter adapter;
    private List<String> dataList;
    Fragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sett);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.button_click);

        newFragment = new upsettingsfragment();

        dataList = new ArrayList<>();
        dataList.add("Управление");
        dataList.add("Звук");
        dataList.add("Настройки графики");

        recyclerView = findViewById(R.id.autoRecycler);
        adapter = new SettingsAdapter(dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentsettings_container, newFragment);
        fragmentTransaction.commit();

        ((AppCompatButton) findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                View Error = (View) findViewById(R.id.error);
                TextView nedostupno = (TextView) findViewById(R.id.nedostupno);
                TextView chmo = (TextView) findViewById(R.id.zaydinaserverchmo);
                AppCompatButton close = (AppCompatButton) findViewById(R.id.close);
                View zalupa = (View) findViewById(R.id.zalupa);

                zalupa.setVisibility(View.GONE);
                nedostupno.setVisibility(View.GONE);
                Error.setVisibility(View.GONE);
                chmo.setVisibility(View.GONE);
                close.setVisibility(View.GONE);
            }
        });

        adapter.setOnItemClickListener(new SettingsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                if (position == 0) {
                    tost("123");
                    newFragment = new upsettingsfragment();
                } else if (position == 1) {
                    newFragment = new volumesettingsfragment();
                } else {
                    newFragment = new volumesettingsfragment();
                }

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentsettings_container, newFragment);
                fragmentTransaction.commit();

                adapter.setPreviousSelectedPosition(adapter.getSelectedPosition());
                adapter.setSelectedPosition(position);

                adapter.notifyItemChanged(adapter.getPreviousSelectedPosition());
                adapter.notifyItemChanged(adapter.getSelectedPosition());
            }
        });

        ((AppCompatButton) findViewById(R.id.button_clean_gamee)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent SecAct = new Intent(SettAct.this, MainActivity.class);
                startActivity(SecAct);
            }
        });
    }

    private void tost(String pon)
    {
        Toast.makeText(this, pon, Toast.LENGTH_SHORT).show();
    }
}