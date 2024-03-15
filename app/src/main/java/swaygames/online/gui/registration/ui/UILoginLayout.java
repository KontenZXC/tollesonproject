package swaygames.online.gui.registration.ui;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import com.nvidia.devtech.NvEventQueueActivity;

import org.ini4j.Wini;

import java.io.File;
import java.io.UnsupportedEncodingException;

import swaygames.launcher.ui.UILayout;
import swaygames.online.R;

public class UILoginLayout extends UILayout {
    public Activity activity;

    public UILoginLayout(Activity main) {
        this.activity = main;
    }

    public View getView() {
        return null;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View login_layout = inflate(layoutInflater, viewGroup);

        EditText password = activity.findViewById(R.id.password_enter);
        AppCompatButton play_button = activity.findViewById(R.id.play_but);

        LinearLayout pon = activity.findViewById(R.id.fingerprint_but);
        pon.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));
            Toast.makeText(activity, "Данная функция не доступна на вашем устройстве!", Toast.LENGTH_SHORT).show();
        });

        play_button.setOnClickListener(view -> {
            view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.button_click));

            if(password.getText().toString().isEmpty()) {
                Toast.makeText(activity, "Поле \"пароль\" не должно быть пустым!", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    NvEventQueueActivity.getInstance().sendDialogResponse(2, 1, 0, password.getText().toString().getBytes("windows-1251"));
                    NvEventQueueActivity.getInstance().guiRegistration.main_layout.setVisibility(View.GONE);
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(activity, "Ошибка, сервер не ответил!", Toast.LENGTH_LONG).show();
                }
            }

        });

        return login_layout;
    }

    public void onLayoutClose() {

    }

    public void onLayoutShown() {

    }

    public View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        View inflate = layoutInflater.inflate(R.layout.reg_login, viewGroup, false);
        viewGroup.addView(inflate);
        return inflate;
    }
}
