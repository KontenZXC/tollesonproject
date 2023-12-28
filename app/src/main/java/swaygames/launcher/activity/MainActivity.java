package swaygames.launcher.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.GetChars;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;

import swaygames.online.R;

public class MainActivity extends AppCompatActivity {
    public AppCompatButton button_play, button_settings;
    public EditText nickname_input;
    public String nick_as_ini = "";
    public TextView nick_name_info;
    public ImageButton info_nick_button, button_discord, button_vk, button_telegram;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        nickname_input = (EditText) findViewById(R.id.edit_text_name);
        button_play = (AppCompatButton) findViewById(R.id.button_play);
        button_settings = (AppCompatButton) findViewById(R.id.button_clean_game);
        nick_name_info = (TextView) findViewById(R.id.text_view_info_about_nickname);
        info_nick_button = (ImageButton) findViewById(R.id.ib_info);
        button_discord = (ImageButton) findViewById(R.id.button_discord);
        button_vk = (ImageButton) findViewById(R.id.button_vk);
        button_telegram = (ImageButton) findViewById(R.id.button_telegram);

        ClickUserButtons();
        RenderUserNickName();
        ChangeUserNickName();
    }

    private void ClickUserButtons() {
        button_play.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

            try {
                Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
                nick_as_ini = cache_get.get("client", "name");
                cache_get.store();
            } catch (Exception ioException) {
                Log.e("ERROR CLICKS", "Cannot load nick string!");
            }

            if (nick_as_ini.isEmpty() || !nick_as_ini.contains("_")) {
                nickname_input.setBackgroundResource(R.drawable.launcher_main_edit_text_red_bg);
                nick_name_info.setVisibility(View.VISIBLE);
            }

        });

        info_nick_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

            if(nick_name_info.getVisibility() == View.INVISIBLE) {
                nick_name_info.setVisibility(View.VISIBLE);
            } else {
                nick_name_info.setVisibility(View.INVISIBLE);
            }
        });

        button_discord.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("discord.com/personkaa"));
            startActivity(intent);
        });

        button_telegram.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://t.me/regerds"));
            startActivity(intent);
        });

        button_vk.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("vk.com/"));
            startActivity(intent);
        });

        button_settings.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));
            String nick = "";

            try {
                Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
                nick = cache_get.get("client", "name");
                cache_get.store();
            } catch (Exception ioException) {
                Log.e("ERROR CLICKS", "Cannot load nick string!");
            }

            if(nick.equals("Regerds_Regerds")) {
                // сделать показ уи настроек
            } else {
                //Toast.makeText(MainActivity.this, "Настройки будут доступны в версии - 2.0", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void RenderUserNickName() {
        try {
            Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
            nickname_input.setText(String.valueOf(cache_get.get("client", "name")));
            cache_get.store();
        } catch (Exception ioException) {
            Log.e("ERROR RENDER", "Cannot load nick string!");
        }
    }

    private void ChangeUserNickName() {
        nickname_input.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if(!textView.getText().toString().isEmpty() || textView.getText().toString().contains("_")) {
                    try {
                        if(nick_name_info.getVisibility() == View.VISIBLE) {
                            nick_name_info.setVisibility(View.INVISIBLE);
                            nickname_input.setBackgroundResource(R.drawable.launcher_main_edit_text_bg);
                        }

                        Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
                        cache_get.put("client", "name", textView.getText().toString());
                        cache_get.store();
                    } catch (Exception ioException) {
                        Toast.makeText(this, "Ошибка при обработке данных, попробуйте позже!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            return false;
        });
    }
} 