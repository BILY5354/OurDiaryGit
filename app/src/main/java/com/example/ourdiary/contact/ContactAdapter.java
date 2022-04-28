package com.example.ourdiary.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.local.Contact;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactAdapter extends ListAdapter<Contact, ContactAdapter.ViewHolder> {


    /**传入需要显示的list*/
    public ContactAdapter(@NonNull DiffUtil.ItemCallback<Contact> diffCallback) {
        super(diffCallback);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout LL_item_contact;
        TextView TV_name, TV_phone, TV_header;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            LL_item_contact = itemView.findViewById(R.id.LL_contacts_item_contant);
            TV_header = itemView.findViewById(R.id.TV_contacts_item_header);
            TV_name = itemView.findViewById(R.id.TV_contacts_name);
            TV_phone = itemView.findViewById(R.id.TV_contacts_phone_number);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_contacts_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactAdapter.ViewHolder holder, int position) {
        Contact contact = getItem(position);
        holder.TV_name.setText(contact.getContact_name());
        holder.TV_phone.setText(contact.getContact_phone_number());
    }

    public static class ContactDiff extends DiffUtil.ItemCallback<Contact> {

        @Override
        public boolean areItemsTheSame(@NonNull Contact oldItem, @NonNull  Contact newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull  Contact oldItem, @NonNull  Contact newItem) {
            return (oldItem.getContact_name()).equals(newItem.getContact_name())
                    && (oldItem.getContact_phone_number().equals(newItem.getContact_phone_number()));
        }
    }

}
