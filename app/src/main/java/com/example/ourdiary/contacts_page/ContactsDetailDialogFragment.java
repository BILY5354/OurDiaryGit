package com.example.ourdiary.contacts_page;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 获取activity的topicid是本类中构造函数ContactsDetailDialogFragment（在ContactsActivity获取）用intent获取
 * 本fragment获取topicid是也是本类中构造函数用bundle获取，关键代码是setArguments
 * 所有的电话号码都会保存到同一个表中，这个表同时也保存topid，根据topicid来取不同的通讯录信息
 * @author home
 *@time 2021/4/24 13:44
*/
public class ContactsDetailDialogFragment extends DialogFragment {


    /**
     * UI
     */
    private LinearLayout ll_contacts_detail_top_content;
    private EditText et_contacts_detail_name, et_contacts_detail_phone_number;
    FloatingActionButton fab_update,fab_delete_one,fab_back;

    /**
     * Contacts Info
     */
    private int contactsId, topicId;
    private String contactsName, contactsPhoneNumber;

    /**
     * Edit or add contacts
     */
    private boolean isEditMode = false;

    /**
     * 注意此方法中的 fragment.setArguments(args)语句
     * newInstance is used in ContactsActivity and ContactsAdapter
     *@author home
     *@time 2021/4/24 11:36
    */
    public static ContactsDetailDialogFragment newInstance(
            boolean edit_state,int contactsId, String contactsName, String contactsPhoneNumber, int topicId) {
        Bundle args = new Bundle();
        ContactsDetailDialogFragment fragment = new ContactsDetailDialogFragment();
        args.putBoolean("edit_state", edit_state);
        args.putInt("contactsId", contactsId);
        args.putString("contactsName", contactsName);
        args.putString("contactsPhoneNumber", contactsPhoneNumber);
        args.putInt("topicId", topicId);
        fragment.setArguments(args);
        return fragment;
    }

    /** what is use?*/
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    /**
     *create fragment
     *@author home
     *@time 2021/5/23 16:11
    */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_contacts_detail, null);
        builder.setView(view);

        ll_contacts_detail_top_content = view.findViewById(R.id.ll_contacts_detail_top_content);

        et_contacts_detail_name = view.findViewById(R.id.et_contacts_detail_name);
        et_contacts_detail_phone_number = view.findViewById(R.id.et_contacts_detail_phone_number);

        fab_update = view.findViewById(R.id.fab_fg_contact_detail_update);
        fab_back = view.findViewById(R.id.fab_fg_contact_detail_back);
        fab_delete_one = view.findViewById(R.id.fab_fg_contact_detail_delete_one);


        /** get contacts name and photo number from INSTANCE, and set them. */
        isEditMode = getArguments().getBoolean("edit_state",false);
        contactsId = getArguments().getInt("contactsId", 1);
        contactsName = getArguments().getString("contactsName", "");
        contactsPhoneNumber = getArguments().getString("contactsPhoneNumber", "");
        et_contacts_detail_name.setText(contactsName);
        et_contacts_detail_phone_number.setText(contactsPhoneNumber);

        /** if is added button clicked, the ContactsDetailDialog will not show the delete fab*/
        if (isEditMode) {
            fab_delete_one.setVisibility(view.VISIBLE);

        } else {
            fab_delete_one.setVisibility(view.GONE);
        }

        /**below are three FAB button for update back delete one function*/
        fab_update.setOnClickListener(update -> {
            Bundle result = new Bundle();
            if (isEditMode) //if is edit mode, update it,  else, add the new one
            {

                result.putInt("contacts_detail_fg_update_id", contactsId);
                result.putString("contacts_detail_fg_update_name", et_contacts_detail_name.getText().toString());
                result.putString("contacts_detail_fg_update_phone_number",et_contacts_detail_phone_number.getText().toString());
                getParentFragmentManager().setFragmentResult("contacts_detail_fg_update", result);
                Toast.makeText(getContext(), "更新成功^-^", Toast.LENGTH_SHORT).show();
            } else {

                result.putString("contacts_detail_fg_add_name", et_contacts_detail_name.getText().toString());
                result.putString("contacts_detail_fg_add_phone_number",et_contacts_detail_phone_number.getText().toString());
                getParentFragmentManager().setFragmentResult("contacts_detail_fg_add", result);
                Toast.makeText(getContext(), "添加成功^-^", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        fab_back.setOnClickListener(back -> {
            dismiss();
        });

        fab_delete_one.setOnClickListener(delete_one -> {
            Bundle result = new Bundle();
            result.putInt("contacts_detail_fg_delete_one_id", contactsId);
            getParentFragmentManager().setFragmentResult("contacts_detail_fg_delete_one", result);
            dismiss();
        });

        return  builder.create();
    }


//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        Dialog dialog  = super.onCreateDialog(savedInstanceState);
//        // 请求除了顶部topbar的页面更新
//        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        return dialog;
//    }


    /** 用于判断是否是新增还是编辑情况显示本ContactsDetailDialogFragment
     * 如果是编辑模式，更新数据库，如果是新增，则插入数据库**/
    private void buttonOkEvent() {
        if (et_contacts_detail_name.getText().toString().length() > 0 &&
            et_contacts_detail_phone_number.getText().toString().length() > 0) {
            if (isEditMode) {
                /**数据库更新**/

            } else {
                /**数据库插入**/
            }
            dismiss();//是本fra消失
        } else {
            Toast.makeText(getActivity(), "Contacts is empty, add something", Toast.LENGTH_SHORT).show();
        }
    }


}
