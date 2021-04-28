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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.contact_database.Contact;

import java.util.List;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.TopicViewHolder> {

    private FragmentActivity mActivity;

    //是否需要？
    private ContactsDetailDialogFragment.ContactsDetailCallback callback;
    private int topicId;

    //ContactsEntity不是
    private List<ContactsEntity> contactsNamesList;


    public ContactsAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffContactCallback, FragmentActivity activity, int topicId) {
        super();
        this.mActivity = activity;
        this.topicId = topicId;
    }


    @NonNull
    @Override
    public ContactsAdapter.TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_contacts_item, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public int getItemCount() {

        return contactsNamesList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.TopicViewHolder holder, int position) {
        if (showHeader(position)) {
            holder.getHeader().setVisibility(View.VISIBLE);
            holder.getHeader().setText(contactsNamesList.get(position).getSortLetters());
        } else {
            holder.getHeader().setVisibility(View.GONE);
        }

        holder.getTVName().setText(contactsNamesList.get(position).getName());
        holder.getTVPhoneNumber().setText(contactsNamesList.get(position).getPhoneNumber());
        holder.setItemPosition(position);
    }

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

    private boolean showHeader(final int position) {
        if (position == 0) {
            return true;
        } else {
            if (!contactsNamesList.get(position - 1).getSortLetters().equals(
                    contactsNamesList.get(position).getSortLetters())) {
                return true;
            } else {
                return false;
            }
        }
    }

    protected class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener {

        //Header
        private TextView TV_contacts_item_header;

        //Item
        private LinearLayout LL_contacts_item_contant;
        private ImageView IV_contacts_photo;
        private TextView TV_contacts_name;
        private TextView TV_contacts_phone_number;
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
            CallDialogFragment callDialogFragment =
                    CallDialogFragment.newInstance(contactsNamesList.get(itemPosition).getName(),
                            contactsNamesList.get(itemPosition).getPhoneNumber());
            callDialogFragment.show(mActivity.getSupportFragmentManager(),"callDialogFragment");
        }

        @Override
        public boolean onLongClick(View view) {
            ContactsDetailDialogFragment contactsDetailDialogFragment =
                    ContactsDetailDialogFragment.newInstance(contactsNamesList.get(itemPosition).getContactsId(),
                            contactsNamesList.get(itemPosition).getName(), contactsNamesList.get(itemPosition).getPhoneNumber(),
                            topicId);
            contactsDetailDialogFragment.show(mActivity.getSupportFragmentManager(),"contactsDetailDialogFragment");
            return true;//注意true与false区别
        }
    }


    public static class ContactDiff extends DiffUtil.ItemCallback<Contact> {

        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Contact oldItem, @NonNull Contact newItem) {
            return oldItem.getContacts_name().equals(newItem.getContacts_name());
        }
    }

}
