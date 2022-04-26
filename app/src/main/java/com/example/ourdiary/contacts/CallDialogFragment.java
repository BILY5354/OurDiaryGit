package com.example.ourdiary.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;

public class CallDialogFragment extends DialogFragment implements View.OnClickListener {

    /**
     * UI
     */
    private RelativeLayout RL_contacts_call_name;
    private TextView TV_contacts_call_name;
    private Button But_contacts_call_cancel, But_contacts_call_call;

    /**
     * Contacts Info
     */
    private String contactsName, contactsPhoneNumber;

    /**
     * Permission
     */
    private static final int REQUEST_CALL_PHONE_PERMISSION = 2;
    private boolean havePermission = false;
    private boolean RequestPermissionsResult = false;

    public static CallDialogFragment newInstance(String contactsName, String contactsPhoneNumber) {
        Bundle args = new Bundle();
        CallDialogFragment fragment = new CallDialogFragment();
        args.putString("contactsName", contactsName);
        args.putString("contactsPhoneNumber", contactsPhoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(R.layout.dialog_fragment_contacts_call, container);

        RL_contacts_call_name = rootView.findViewById(R.id.RL_contacts_call_name);

        TV_contacts_call_name = rootView.findViewById(R.id.TV_contacts_call_name);

        But_contacts_call_call = rootView.findViewById(R.id.btn_contacts_call_call);
        But_contacts_call_call.setOnClickListener(this);
        But_contacts_call_cancel = rootView.findViewById(R.id.btn_contacts_call_cancel);
        But_contacts_call_cancel.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (checkPermission(REQUEST_CALL_PHONE_PERMISSION)) {
            RequestPermissionsResult = true;
            havePermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        RequestPermissionsResult = true;
        switch (requestCode) {
            case REQUEST_CALL_PHONE_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Do nothing , just show this dialog
                    havePermission = true;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle(getString(R.string.contacts_call_phone_permission_title))
                            .setMessage(getString(R.string.contacts_call_phone_permission_content))
                            .setPositiveButton(getString(R.string.dialog_button_ok), null);
                    builder.show();
                    havePermission = false;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (RequestPermissionsResult) {
            if (havePermission) {
                contactsName = getArguments().getString("contactsName", "");
                contactsPhoneNumber = getArguments().getString("contactsPhoneNumber", "");
                if (!"".equals(contactsName)) {
                    TV_contacts_call_name.setText(contactsName);
                }
            } else {
                dismiss();
            }
        }
    }

    /**检查电话权限**/
    private boolean checkPermission(final int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CALL_PHONE)) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        requestCode);
                return false;
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        requestCode);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contacts_call_call:
                TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                if (tm.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
                    //No module for calling phone
                    Toast.makeText(getActivity(), getString(R.string.contacts_call_phone_no_call_function), Toast.LENGTH_LONG)
                            .show();
                } else {
                    //Can call phone
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contactsPhoneNumber));
                    startActivity(intent);
                }
                dismiss();
                break;
            case R.id.btn_contacts_call_cancel:
                dismiss();
                break;
        }
    }
}
