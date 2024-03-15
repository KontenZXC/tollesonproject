package swaygames.launcher.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
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
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import org.ini4j.Wini;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import swaygames.launcher.Api;
import swaygames.launcher.Downloader;
import swaygames.online.R;

public class MainActivity extends AppCompatActivity {
    public AppCompatButton button_play, button_settings;
    public EditText nickname_input;
    public String nick_as_ini = "";
    public TextView nick_name_info;
    public ImageButton info_nick_button, button_discord, button_vk, button_telegram;
    public FirebaseRemoteConfig firebaseRemoteConfig;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        HashMap<String, Object> defaultsRate = new HashMap<>();
        defaultsRate.put("json", String.valueOf(0));

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings settings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(1).build();
        firebaseRemoteConfig.setConfigSettingsAsync(settings);
        firebaseRemoteConfig.setDefaultsAsync(defaultsRate);

        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(MainActivity.this, task -> {
            if(task.isSuccessful()) {
                final String return_cache_link = firebaseRemoteConfig.getString("cache_link");
                final String return_client_link = firebaseRemoteConfig.getString("client_link");
                final String return_update_link = firebaseRemoteConfig.getString("update_link");
                final int return_version_cache = Integer.valueOf(firebaseRemoteConfig.getString("version_cache"));
                final int return_version_client = Integer.valueOf(firebaseRemoteConfig.getString("version_client"));

                Api.setAllValues(return_cache_link, return_client_link, return_update_link, return_version_cache, return_version_client);
            } else {
                Toast.makeText(MainActivity.this, "Поддержка лаунчера прекращена!", Toast.LENGTH_SHORT).show();
                new CountDownTimer(1000, 2000) {
                    public void onTick(long l) {

                    }

                    public void onFinish() {
                        onDestroy();
                    }
                }.start();
            }
        });

        nickname_input = (EditText) findViewById(R.id.edit_text_name);
        button_play = (AppCompatButton) findViewById(R.id.button_play);
        button_settings = (AppCompatButton) findViewById(R.id.button_clean_game);
        nick_name_info = (TextView) findViewById(R.id.text_view_info_about_nickname);
        info_nick_button = (ImageButton) findViewById(R.id.ib_info);
        button_discord = (ImageButton) findViewById(R.id.button_discord);
        button_vk = (ImageButton) findViewById(R.id.button_vk);
        button_telegram = (ImageButton) findViewById(R.id.button_telegram);

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        ClickUserButtons();
        RenderUserNickName();
        ChangeUserNickName();
        ShowDialogPrivacyPolicy();
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

            if(CheckValidCache()) {
                if (nick_as_ini.isEmpty() || !nick_as_ini.contains("_")) {
                    nickname_input.setBackgroundResource(R.drawable.launcher_main_edit_text_red_bg);
                    nick_name_info.setVisibility(View.VISIBLE);
                } else {
                    int cache_version = 0, client_version = 0;

                    try {
                        Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/launcher/config.ini"));
                        cache_version = Integer.valueOf(cache_get.get("settings", "version_cache"));
                        client_version = Integer.valueOf(cache_get.get("settings", "version_client"));
                        cache_get.store();
                    } catch (Exception ioException) {
                        Log.e("ERROR CLICKS", "Cannot load nick string!");
                    }

                    /*if(cache_version != Api.version_cache) {
                        Api.typeLoad = Api.eTypeLoad.UPDATE;
                        startActivity(new Intent(MainActivity.this, Downloader.class));
                    } else if(client_version != Api.version_client) {
                        Api.typeLoad = Api.eTypeLoad.CLIENT;
                        startActivity(new Intent(MainActivity.this, Downloader.class));
                    } else if(cache_version == Api.version_cache || client_version == Api.version_client) {*/
                        startActivity(new Intent(MainActivity.this, swaygames.online.core.GTASA.class));
                    //}
                }
            } else {
                Api.typeLoad = Api.eTypeLoad.FILES;
                startActivity(new Intent(MainActivity.this, Downloader.class));
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
            Intent SecAct = new Intent(MainActivity.this, SettAct.class);
            startActivity(SecAct);
        });
    }

    private void RenderUserNickName() {
        try {
            Wini cache_get = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
            nickname_input.setText(String.valueOf(cache_get.get("client", "name")));
            cache_get.store();
        } catch (Exception ioException) {
            Log.e("ERROR RENDER", "1Cannot load nick string!");
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return false;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1000) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }

    private boolean CheckValidCache() {
        File img = new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/texdb/gta3.img");
        return img.exists();
    }

    @SuppressLint("ResourceType")
    private void ShowDialogPrivacyPolicy() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.launcher_dialog_privacy);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(17170445);
        dialog.getWindow().setLayout(-1, -2);

        dialog.findViewById(R.id.button_ok).setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));
            dialog.hide();
        });

        dialog.findViewById(R.id.button_no).setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.button_click));
            onDestroy();
        });

        dialog.show();
    }
} 