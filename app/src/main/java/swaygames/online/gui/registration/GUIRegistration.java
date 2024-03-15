package swaygames.online.gui.registration;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nvidia.devtech.NvEventQueueActivity;

import org.ini4j.Wini;

import java.io.File;

import swaygames.launcher.ui.containers.UIContainer;
import swaygames.online.R;
import swaygames.online.gui.registration.ui.UILoginLayout;

public class GUIRegistration {
    public LinearLayout main_layout;
    public UIContainer main_container;

    public GUIRegistration(Activity activity) {
        main_layout = activity.findViewById(R.id.reg_main_layout);
        main_layout.setVisibility(View.GONE);

        main_container = activity.findViewById(R.id.reg_container);
        main_container.addLayout(1, new UILoginLayout(activity));
        main_container.setCurrentLayout(1);

        TextView nick = activity.findViewById(R.id.reg_nickname);
        TextView reg_info = activity.findViewById(R.id.reg_info);

        try {
            Wini wini = new Wini(new File(Environment.getExternalStorageDirectory() + "/SwayCommunity/SAMP/settings.ini"));
            nick.setText(wini.get("client", "name"));
            wini.store();
        } catch (Exception exception) {
            Log.e("TEST", "тест пон");
        }

        reg_info.setText("Введите пароль чтобы войти в игру.\n\nИнформация: если данный аккаунт\nявляется не вашим, то выйдите\nиз игры и смените игровое имя на новое!");
    }

    public void ShowGUIRegistration() {
        NvEventQueueActivity.getInstance().newChat.chat.setVisibility(View.GONE);
        main_layout.setVisibility(View.VISIBLE);
    }
}
