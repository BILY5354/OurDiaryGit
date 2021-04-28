package com.example.ourdiary.contacts;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ourdiary.R;

/**
 *获取activity的topicid是本类中构造函数ContactsDetailDialogFragment（在ContactsActivity获取）用intent获取
 *本fragment获取topicid是也是本类中构造函数用bundle获取，关键代码是setArguments
 * 所有的电话号码都会保存到同一个表中，这个表同时也保存topid，根据topicid来取不同的通讯录信息
 * @author home
 *@time 2021/4/24 13:44
*/
public class ContactsDetailDialogFragment extends DialogFragment implements View.OnClickListener {


    public interface ContactsDetailCallback {
        void addContacts();

        void updateContacts();

        void deleteContacts();
    }

    /**
     * UI
     */
    private LinearLayout LL_contacts_detail_top_content;
    private EditText EDT_contacts_detail_name, EDT_contacts_detail_phone_number;
    private Button But_contacts_detail_delete, But_contacts_detail_cancel, But_contacts_detail_ok;

    /**
     * CallBack
     */
    private ContactsDetailCallback callback;

    /**
     * Contacts Info
     */
    private long contactsId;
    private String contactsName, contactsPhoneNumber;
    private long topicId;

    /**
     * Edit or add contacts
     */
    private boolean isEditMode = false;
    public static final long ADD_NEW_CONTACTS = -1;

    /**
     *注意此方法中的 fragment.setArguments(args)语句
     *@author home
     *@time 2021/4/24 11:36
    */
    public static ContactsDetailDialogFragment newInstance(
            long contactsId, String contactsName, String contactsPhoneNumber, long topicId) {
        Bundle args = new Bundle();
        ContactsDetailDialogFragment fragment = new ContactsDetailDialogFragment();
        //contactsId = -1 is edit
        args.putLong("contactsId", contactsId);
        args.putString("contactsName", contactsName);
        args.putString("contactsPhoneNumber", contactsPhoneNumber);
        args.putLong("topicId", topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (ContactsDetailCallback) context;
        } catch (ClassCastException e) {}
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog  = super.onCreateDialog(savedInstanceState);
        // 请求除了顶部topbar的页面更新
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    /**
     *创建fg视图部分
     *@author home
     *@time 2021/4/24 11:48
    */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);
        View rootview = inflater.inflate(R.layout.dialog_fragment_contacts_detail, container);

        LL_contacts_detail_top_content = rootview.findViewById(R.id.ll_contacts_detail_top_content);
        //项目这里是设置背景

        EDT_contacts_detail_name = rootview.findViewById(R.id.et_contacts_detail_name);
        EDT_contacts_detail_phone_number = rootview.findViewById(R.id.et_contacts_detail_phone_number);

        But_contacts_detail_ok = rootview.findViewById(R.id.btn_contacts_detail_ok);
        But_contacts_detail_ok.setOnClickListener(this);

        But_contacts_detail_cancel = rootview.findViewById(R.id.btn_contacts_detail_cancel);
        But_contacts_detail_cancel.setOnClickListener(this);

        But_contacts_detail_delete = rootview.findViewById(R.id.btn_contacts_detail_delete);
        //
        return rootview;
    }

    /**
     *DetailDialogFragment是新增与长按通讯录编辑功能公用的界面，如果在编辑模式下是不会显示删除按键的
     *@author home
     *@time 2021/4/24 11:55
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        contactsId = getArguments().getLong("contactsId", -1);
        if (contactsId == ADD_NEW_CONTACTS) {
            isEditMode = false;
            But_contacts_detail_delete.setVisibility(View.GONE);

            topicId = getArguments().getLong("topicId", -1);
        } else {
            isEditMode = true;
            But_contacts_detail_delete.setVisibility(View.VISIBLE);
            But_contacts_detail_delete.setOnClickListener(this);

            //从哪儿get？从CallDialogFragment中get！
            contactsName = getArguments().getString("contactsName", "");
            contactsPhoneNumber = getArguments().getString("contactsPhoneNumber", "");
            EDT_contacts_detail_name.setText(contactsName);
            EDT_contacts_detail_phone_number.setText(contactsPhoneNumber);
        }
    }

    /**
     *数据库编辑到啦啦
     *@author home
     *@time 2021/4/24 11:59
    */

    /** 用于判断是否是新增还是编辑情况显示本ContactsDetailDialogFragment
     * 如果是编辑模式，更新数据库，如果是新增，则插入数据库**/
    private void buttonOkEvent() {
        if (EDT_contacts_detail_name.getText().toString().length() > 0 &&
            EDT_contacts_detail_phone_number.getText().toString().length() > 0) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_contacts_detail_delete:
                //deleteContacts();
                callback.deleteContacts();
                dismiss();
                break;
            case R.id.btn_contacts_detail_cancel:
                dismiss();
                break;
            case R.id.btn_contacts_detail_ok:
                buttonOkEvent();
                break;
        }
    }

}
