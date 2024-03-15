package swaygames.online.gui;

import android.animation.ObjectAnimator;
import android.app.Activity;

import swaygames.online.R;
import swaygames.online.gui.util.Utils;
import com.nvidia.devtech.NvEventQueueActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.*;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class nphone {
    public Activity activity;
    public ConstraintLayout reytiz_layout;
    public ImageView telegram;
    public ImageView vk;
    public View red_arrow;
    public ImageView hud_help;
    public ImageView br_menu_ruble;
    public ImageView discord;
    public ImageView clocks;
    public ImageView help_desk;
    public TextView textViewDate;
    public TextView textViewTime;
    public ImageView phone;
    public nphone(Activity activity) {
        reytiz_layout = activity.findViewById(R.id.nphone);
        telegram = activity.findViewById(R.id.tg_btn);
        clocks = activity.findViewById(R.id.imageView41);
        red_arrow = activity.findViewById(R.id.imageView39);
        br_menu_ruble = activity.findViewById(R.id.imageView42);
        vk = activity.findViewById(R.id.imageView38);
        discord = activity.findViewById(R.id.imageView37);
        hud_help = activity.findViewById(R.id.imageView43);
        help_desk = activity.findViewById(R.id.imageView40);
        textViewDate = activity.findViewById(R.id.textViewDate);
        textViewTime = activity.findViewById(R.id.textViewTime);
        phone = activity.findViewById(R.id.imageView35);
        NestedScrollView nestedScrollView = activity.findViewById(R.id.nestedscrollview);

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        ImageView set_btn = activity.findViewById(R.id.stt_btn);
        /*
        set_btn.setOnClickListener(view -> {
                NvEventQueueActivity.getInstance().showClientSettings();
        });
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private Handler handler = new Handler();
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                handler.removeCallbacksAndMessages(null);
                if (scrollY > oldScrollY) {
                    reytiz_layout.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.slide_up));
                    handler.postDelayed(() -> {
                        NvEventQueueActivity.getInstance().hidephone();
                        reytiz_layout.setTranslationY(0);
                    }, 1000);
                } else {
                    // Прокрутка вверх
                }
            }
        });

        telegram.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/tg").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        hud_help.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/helpphone").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        help_desk.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/ask").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
        clocks.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/time").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        vk.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/vk").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        });

        br_menu_ruble.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/donate").getBytes("windows-1251"));
                NvEventQueueActivity.getInstance().hidephone();
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            NvEventQueueActivity.getInstance().togglePlayer(1);

        });

        discord.setOnClickListener( view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            try {
                NvEventQueueActivity.getInstance().sendCommand(("/ds").getBytes("windows-1251"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

        });

        red_arrow.setOnClickListener( view -> {
            NvEventQueueActivity.getInstance().hidephone();

        });

         */

        setListeners(activity);
        close();

    }


    private void startActivity(Intent intent) {
    }


    public void setListeners(Activity aactivity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse("http://t.me/tollesonproject"));
        startActivity(intent);
    }


    public void show(int reytiz) {

        Utils.ShowLayout(reytiz_layout, true);
    }

    public void close() {
        Utils.HideLayout(reytiz_layout, false);
    }
}
