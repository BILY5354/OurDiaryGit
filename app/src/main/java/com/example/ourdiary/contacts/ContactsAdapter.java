package com.example.ourdiary.contacts;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.Contact;



import java.util.List;


public class ContactsAdapter extends ListAdapter<Contact,ContactsAdapter.TopicViewHolder> {

    private final FragmentActivity mActivity;//这有什么用

    private final int topicId;

    private List<ContactsEntity> contactsNamesList;

    private boolean showQueryListSign = false;

    public ContactsAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffContactCallback,
                           FragmentActivity activity, List<ContactsEntity> contactsNamesList, int topicId) {
        super(diffContactCallback);
        this.mActivity = activity;
        this.contactsNamesList = contactsNamesList;
        this.topicId = topicId;
    }

    public void setShowQueryListSign(boolean showQueryListSign) {
        this.showQueryListSign = showQueryListSign;
    }

    public void setAllContacts(List<ContactsEntity> allContacts) {
        contactsNamesList.clear();
        this.contactsNamesList = allContacts;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_contacts_item, parent, false);
        return new TopicViewHolder(view);
    }


    /**猜想需要载入所有通讯录再进行排序 不然弄不了*/
    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.TopicViewHolder holder, int position) {

        if (showQueryListSign) {
            Contact contact = getItem(position);
            holder.getTVName().setText(contact.getContacts_name());//setText方法看跳转
            holder.getTVPhoneNumber().setText(contact.getContacts_phone_number());

        } else {

            if (showHeader(position)) {
                holder.getHeader().setVisibility(View.VISIBLE);
                holder.getHeader().setText(contactsNamesList.get(position).getSortLetters());
            } else {
                holder.getHeader().setVisibility(View.GONE);
            }
            holder.getTVName().setText(contactsNamesList.get(position).getName());
            holder.getTVPhoneNumber().setText(contactsNamesList.get(position).getPhoneNumber());
        }
        holder.setItemPosition(position);
    }

    /**
     *让每个通讯录显示它的头部，例如Contacts_Name 为test，那么就显示它的头部T
     * 判断的方法是检查每个Contact的getContacts_name首字母是否相同
     *@author home
     *@time 2021/5/26 11:37
     */
    private boolean showHeader(final int position) {
        if (position == 0) {
            return true;
        } else { //如果不是位于rv的0号位置，那么是可以去得到position-1的，不会为空
            if (!contactsNamesList.get(position - 1).getSortLetters().equals(
                    contactsNamesList.get(position).getSortLetters())) {
                return true;
            } else {
                return false;
            }
        }
    }


    /**Click on the sidebar to jump to the corresponding word position*/
    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = contactsNamesList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }


    public static class ContactDiff extends DiffUtil.ItemCallback<Contact> {

        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return (oldItem.getContacts_name().equals(newItem.getContacts_name()))
                    && (oldItem.getContacts_phone_number().equals(newItem.getContacts_phone_number()))
                    && (oldItem.getContacts_contacts_ref_topic_id() == newItem.getContacts_contacts_ref_topic_id());
        }
    }

    protected class TopicViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener, View.OnLongClickListener {

        //Header
        private final TextView TV_contacts_item_header;

        //Item
        private final LinearLayout LL_contacts_item_contant;
        private final ImageView IV_contacts_photo;
        private final TextView TV_contacts_name;
        private final TextView TV_contacts_phone_number;
        private int itemPosition;

        protected TopicViewHolder(View view ){
            super(view);
            this.TV_contacts_item_header = view.findViewById(R.id.TV_contacts_item_header);
            this.LL_contacts_item_contant = view.findViewById(R.id.LL_contacts_item_contant);

            this.LL_contacts_item_contant.setOnClickListener(this);
            this.LL_contacts_item_contant.setOnLongClickListener(this);
            this.IV_contacts_photo = view.findViewById(R.id.IV_contacts_photo);
            this.TV_contacts_name = view.findViewById(R.id.TV_contacts_name);
            this.TV_contacts_phone_number = view.findViewById(R.id.TV_contacts_phone_number);
        }

        public TextView getHeader() {
            return TV_contacts_item_header;
        }

        public ImageView getIVPhoto() {
            return IV_contacts_photo;
        }

        public TextView getTVName() {
            return TV_contacts_name;
        }

        public TextView getTVPhoneNumber() {
            return TV_contacts_phone_number;
        }

        public void setItemPosition(int itemPosition) {
            this.itemPosition = itemPosition;
        }


        @Override
        public void onClick(View view) {
            /*之前set text是设置没有排序的通讯录 所以顺序是不对的 现在需要用list进行设置*/
//            Contact contact = getItem(getAdapterPosition());//get specific contact in RecyclerView
//            CallDialogFragment callDialogFragment =
//                    CallDialogFragment.newInstance(contact.getContacts_name(),
//                            contact.getContacts_phone_number());
            CallDialogFragment callDialogFragment = CallDialogFragment.newInstance(
                    contactsNamesList.get(getAdapterPosition()).getName(),
                    contactsNamesList.get(getAdapterPosition()).getPhoneNumber());
            callDialogFragment.show(mActivity.getSupportFragmentManager(),"callDialogFragment");
        }

        @Override
        public boolean onLongClick(View view) {
            /*之前set text是设置没有排序的通讯录 所以顺序是不对的 现在需要用list进行设置*/
            ContactsDetailDialogFragment contactsDetailDialogFragment = ContactsDetailDialogFragment.newInstance(
                    true,
                    contactsNamesList.get(getAdapterPosition()).getContactsId(),
                    contactsNamesList.get(getAdapterPosition()).getName(),
                    contactsNamesList.get(getAdapterPosition()).getPhoneNumber(),
                    topicId);
            contactsDetailDialogFragment.show(mActivity.getSupportFragmentManager(),"contactsDetailDialogFragment");
            return true;//注意true与false区别
        }
    }
}