package swaygames.launcher.settings.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import swaygames.launcher.settings.adapter.SettingsAdapter;
import swaygames.online.R;
import swaygames.launcher.settings.adapter.RecMovementAdapter;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;

public class upsettingsfragment extends Fragment {

    //Slider.OnSliderTouchListener touchListener;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecMovementAdapter adapter;

    private Slider slider;
    private Slider slider1;
    private Slider slider2;

    private TextView valueTextView;
    private TextView valueTextView1;
    private TextView valueTextView2;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private int thisitem = 0;

    public upsettingsfragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_uprava, container, false);
        slider = v.findViewById(R.id.all_sound_seek_bar);
        slider1 = v.findViewById(R.id.horizontal);
        slider2 = v.findViewById(R.id.vertical);
        List<String> dataList = new ArrayList<>();
        dataList.add("Старый");
        dataList.add("Новый");
        adapter = new RecMovementAdapter(dataList);
        recyclerView = v.findViewById(R.id.rvSettingMenuSwitchHud);
        recyclerView1 = v.findViewById(R.id.rvSettingMenuSwitchChat);
        recyclerView2 = v.findViewById(R.id.rvSettingMenuSwitchKeyboard);
        recyclerView.setAdapter(adapter);
        recyclerView1.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);
        textView = v.findViewById(R.id.textView45);
        textView1 = v.findViewById(R.id.textView44);
        textView2 = v.findViewById(R.id.textView43);
        textView.setOnClickListener(view -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            // Показать RecyclerView при нажатии на TextView
            recyclerView.setVisibility(View.VISIBLE);
            if (thisitem != 1) {
                recyclerView2.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.GONE);
            }
            thisitem = 1;
        });

        textView1.setOnClickListener(view -> {
            recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView1.setAdapter(adapter);
            // Показать RecyclerView при нажатии на TextView
            recyclerView1.setVisibility(View.VISIBLE);
            if (thisitem != 2) {
                recyclerView2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
            thisitem = 2;
        });

        textView2.setOnClickListener(view -> {
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView2.setAdapter(adapter);
            // Показать RecyclerView при нажатии на TextView
            recyclerView2.setVisibility(View.VISIBLE);

            if (thisitem != 3) {
                recyclerView1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
            thisitem = 3;
        });

        adapter.setOnItemClickListener(new RecMovementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position % 2 == 0) {
                    if (thisitem == 3) {
                        textView2.setText("Старый");
                        recyclerView2.setVisibility(View.GONE);
                    } if (thisitem == 2) {
                        textView1.setText("Старый");
                        recyclerView1.setVisibility(View.GONE);
                    } if (thisitem == 1) {
                        textView.setText("Старый");
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    if (thisitem == 3) {
                        textView2.setText("Новый");
                        recyclerView2.setVisibility(View.GONE);
                    } if (thisitem == 2) {
                        textView1.setText("Новый");
                        recyclerView1.setVisibility(View.GONE);
                    } if (thisitem == 1) {
                        textView.setText("Новый");
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        });

        slider.addOnChangeListener((slider, value, fromUser) -> {
            // Обновление TextView с текущим значением слайдера
            valueTextView = v.findViewById(R.id.textView32);
            valueTextView.setText("" + value);
            if(valueTextView.getText().toString().contains("1")) {

            }
        });

        slider1.addOnChangeListener((slider, value, fromUser) -> {
            // Обновление TextView с текущим значением слайдера
            valueTextView1 = v.findViewById(R.id.textView33);
            valueTextView1.setText("" + value);
            if(valueTextView1.getText().toString().contains("1")) {

            }
        });

        slider2.addOnChangeListener((slider, value, fromUser) -> {
            // Обновление TextView с текущим значением слайдера
            valueTextView2 = v.findViewById(R.id.textView34);
            valueTextView2.setText("" + value);
            if(valueTextView2.getText().toString().contains("1")) {

            }
        });

        return v;
    }

    private void tost(String pon)
    {
        Toast.makeText(requireContext(), pon, Toast.LENGTH_SHORT).show();
    }

}
