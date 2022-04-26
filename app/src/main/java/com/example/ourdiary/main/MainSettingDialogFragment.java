package com.example.ourdiary.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.ourdiary.R;
import com.example.ourdiary.login.ui.login.LoginActivity;
import com.example.ourdiary.setting.SettingActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainSettingDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    private RelativeLayout RL_main_setting_dialog;
    private ImageView IV_main_setting_add,IV_main_setting_setting,
            IV_main_setting_login,IV_main_setting_syn,IV_main_setting_about;

    private MainActivity activity;

    public MainSettingDialogFragment(MainActivity activity) {
        this.activity = activity;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.bottom_sheet_main_setting,container);

        RL_main_setting_dialog = rootView.findViewById(R.id.RL_main_setting_dialog);
        RL_main_setting_dialog.setBackgroundResource(R.color.light_blue);
        IV_main_setting_add = rootView.findViewById(R.id.IV_main_setting_add);
        IV_main_setting_add.setOnClickListener(this);
        IV_main_setting_setting = rootView.findViewById(R.id.IV_main_setting_setting);
        IV_main_setting_setting.setOnClickListener(this);
        IV_main_setting_login = rootView.findViewById(R.id.IV_main_setting_login);
        IV_main_setting_login.setOnClickListener(this);
        IV_main_setting_syn = rootView.findViewById(R.id.IV_main_setting_syn);
        IV_main_setting_syn.setOnClickListener(this);
        IV_main_setting_about = rootView.findViewById(R.id.IV_main_setting_about);
        IV_main_setting_about.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.IV_main_setting_add:
                TopicDetailDialogFragment createTopicDetailDialogFragment =
                        TopicDetailDialogFragment.newInstance(false, -1, "",
                                -1, -1);
                createTopicDetailDialogFragment.show(activity.getSupportFragmentManager(), "TopicDetailDialogFragment");
                dismiss();
                break;
            case R.id.IV_main_setting_setting:
                Intent intentSetting = new Intent(activity, SettingActivity.class);
                activity.startActivity(intentSetting);
                Log.d("MainSettingDFragment","成功点击第二个图标");
                break;
            case R.id.IV_main_setting_login:
                Log.d("MainSettingDFragment","成功点击第三个图标");
                Intent intentLogin = new Intent(activity, LoginActivity.class);
                activity.startActivity(intentLogin);
                break;
            case R.id.IV_main_setting_syn:
                Log.d("MainSettingDFragment","成功点击第四个图标");
                break;
            case R.id.IV_main_setting_about:
//                Log.d("MainSettingDFragment","成功点击第五个图标");
                break;
        }
    }
}
