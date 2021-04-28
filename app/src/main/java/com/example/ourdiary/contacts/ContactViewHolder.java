package com.example.ourdiary.contacts;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;

public class ContactViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnLongClickListener{

    //Header
    private TextView TV_contacts_item_header;

    //Item
    private LinearLayout LL_contacts_item_contant;
    private ImageView IV_contacts_photo;
    private TextView TV_contacts_name;
    private TextView TV_contacts_phone_number;
    private int itemPosition;

    public ContactViewHolder(@NonNull View itemView) {
        super(itemView);
        this.TV_contacts_item_header = itemView.findViewById(R.id.TV_contacts_item_header);
        this.LL_contacts_item_contant = itemView.findViewById(R.id.LL_contacts_item_contant);

        this.LL_contacts_item_contant.setOnClickListener(this);
        this.LL_contacts_item_contant.setOnLongClickListener(this);
        this.IV_contacts_photo = itemView.findViewById(R.id.IV_contacts_photo);
        this.TV_contacts_name = itemView.findViewById(R.id.TV_contacts_name);
        this.TV_contacts_phone_number = itemView.findViewById(R.id.TV_contacts_phone_number);

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

        /*(CallDialogFragment callDialogFragment =
                CallDialogFragment.newInstance(contactsNamesList.get(itemPosition).getName(),
                        contactsNamesList.get(itemPosition).getPhoneNumber());
        callDialogFragment.show(mActivity.getSupportFragmentManager(),"callDialogFragment");*/
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
