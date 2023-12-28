package swaygames.online.core;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;

import com.nvidia.devtech.NvEventQueueActivity;
import swaygames.online.R;

import java.util.HashMap;
import java.util.Iterator;

public class DialogClientSettingsFPSFragment extends Fragment implements ISaveableFragment {

    private NvEventQueueActivity mContext = null;
    private View mRootView = null;

    private boolean bChangeAllowed = true;

    private SeekBar.OnSeekBarChangeListener mListenerSeekBars;
    private HashMap<ViewGroup, Drawable> mOldDrawables;

    private ViewGroup mParentView = null;

    public static DialogClientSettingsFPSFragment createInstance(String txt)
    {
        DialogClientSettingsFPSFragment fragment = new DialogClientSettingsFPSFragment();
        return fragment;
    }

    public DialogClientSettingsFPSFragment setRoot(ViewGroup root)
    {
        mParentView = root;
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.dialog_settings_fps,container,false);

        mContext = (NvEventQueueActivity)getActivity();

        getValues();
        setSeekBarListeners();

        return mRootView;
    }

    private void makeAllElementsInvisible(ViewGroup parent, View notVisible, boolean first)
    {
        if(first)
        {
            mOldDrawables = new HashMap<ViewGroup, Drawable>();
            mOldDrawables.put((ViewGroup)parent, ((ViewGroup)parent).getBackground());
            parent.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        if(parent == null) {
            return;
        }


        for(int i = 0; i < parent.getChildCount(); i++)
        {
            View view = parent.getChildAt(i);

            if(view instanceof ViewGroup)
            {
                makeAllElementsInvisible((ViewGroup)view, notVisible, false);
                mOldDrawables.put((ViewGroup)view, ((ViewGroup)view).getBackground());
                view.setBackground(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            else
            {
                if(view != notVisible)
                {
                    view.setAlpha(0.0f);
                }
            }
        }
    }

    private void makeAllElementsVisible(ViewGroup parent, View notVisible, boolean first)
    {
        if(first)
        {
            Iterator myVeryOwnIterator = mOldDrawables.keySet().iterator();
            while(myVeryOwnIterator.hasNext())
            {
                ViewGroup key=(ViewGroup)myVeryOwnIterator.next();
                Drawable value=(Drawable)mOldDrawables.get(key);

                key.setBackground(value);
            }
        }

        if(parent == null)
        {
            return;
        }
        for(int i = 0; i < parent.getChildCount(); i++)
        {
            View view = parent.getChildAt(i);

            if(view instanceof ViewGroup)
            {
                makeAllElementsVisible((ViewGroup)view, notVisible, false);
            }
            else
            {
                if(view != notVisible)
                {
                    view.setAlpha(1.0f);
                }
            }
        }
    }

    private void setSeekBarListeners()
    {
        mListenerSeekBars = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(bChangeAllowed)
                {
                    passValuesToNative();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                makeAllElementsInvisible((ViewGroup)mParentView, seekBar, true);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                makeAllElementsVisible((ViewGroup)mParentView, seekBar, true);
                mContext.onSettingsWindowSave();
            }
        };

        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_pos_x_" + i;
            String widgetY = "hud_element_pos_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);

            if(barX != null)
            {
                barX.setOnSeekBarChangeListener(mListenerSeekBars);
            }
            if(barY != null)
            {
                barY.setOnSeekBarChangeListener(mListenerSeekBars);
            }
        }

        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_scale_x_" + i;
            String widgetY = "hud_element_scale_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);

            if(barX != null)
            {
                barX.setOnSeekBarChangeListener(mListenerSeekBars);
            }
            if(barY != null)
            {
                barY.setOnSeekBarChangeListener(mListenerSeekBars);
            }
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void getValues() {
        bChangeAllowed = false;
        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_pos_x_" + i;
            String widgetY = "hud_element_pos_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);

            int[] pos = mContext.getNativeHudElementPosition(i);

            if(pos[0] == -1)
            {
                pos[0] = 1;
            }
            if(pos[1] == -1)
            {
                pos[1] = 1;
            }

            if(barX != null)
            {
                barX.setProgress(pos[0]);
            }
            if(barY != null)
            {
                barY.setProgress(pos[1]);
            }
        }

        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_scale_x_" + i;
            String widgetY = "hud_element_scale_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);

            int[] pos = mContext.getNativeHudElementScale(i);

            if(pos[0] == -1)
            {
                pos[0] = 1;
            }
            if(pos[1] == -1)
            {
                pos[1] = 1;
            }

            if(barX != null && pos[0] != -1)
            {
                barX.setProgress(pos[0]);
            }
            if(barY != null && pos[1] != -1)
            {
                barY.setProgress(pos[1]);
            }
        }

        bChangeAllowed = true;
    }

    public void passValuesToNative()
    {
        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_pos_x_" + i;
            String widgetY = "hud_element_pos_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);
            int x = -1;
            int y = -1;
            if(barX != null)
            {
                x = barX.getProgress();
            }
            if(barY != null)
            {
                y = barY.getProgress();
            }

            mContext.setNativeHudElementPosition(i, x, y);
        }

        for(int i = DialogClientSettings.mSettingsHudFPSStart; i < DialogClientSettings.mSettingsHudFPSEnd; i++)
        {
            String widgetX = "hud_element_scale_x_" + i;
            String widgetY = "hud_element_scale_y_" + i;

            int resIDX = mContext.getResources().getIdentifier(widgetX, "id", mContext.getPackageName());
            int resIDY = mContext.getResources().getIdentifier(widgetY, "id", mContext.getPackageName());

            SeekBar barX = mRootView.findViewById(resIDX);
            SeekBar barY = mRootView.findViewById(resIDY);
            int x = -1;
            int y = -1;
            if(barX != null)
            {
                x = barX.getProgress();
            }
            if(barY != null)
            {
                y = barY.getProgress();
            }

            mContext.setNativeHudElementScale(i, x, y);
        }
    }
}
