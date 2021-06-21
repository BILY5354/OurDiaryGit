package com.example.ourdiary.main;

import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.contacts.ContactsActivity;
import com.example.ourdiary.db.room.topic_database.TopicEntry;
import com.example.ourdiary.db.room.topic_database.TopicViewModel;
import com.example.ourdiary.entries.DiaryActivity;


public class MainPageAdapter extends ListAdapter<TopicEntry, MainPageAdapter.MainPageViewHolder> {


    private final MainActivity activity;
    private final TopicViewModel mTopicViewModel;

    final int TYPE_CONTACTS = 0;
    final int TYPE_DIARY = 1;
    final int TYPE_MEMO = 2;


    public MainPageAdapter(@NonNull DiffUtil.ItemCallback<TopicEntry> diffCallback, MainActivity activity,
                           TopicViewModel mTopicViewModel) {
        super(diffCallback);
        this.activity = activity;
        this.mTopicViewModel = mTopicViewModel;
    }


    @NonNull
    @Override
    public MainPageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new MainPageViewHolder(LayoutInflater.from(parent.getContext())
               .inflate(R.layout.row_main_page_item, parent, false));
    }


    /**
     *知道现在按下什么图标，
     *@time 2021/3/28 22:37
    */
    @Override
    public int getItemViewType(int getTopic_type) {
        if(getTopic_type % 3 == 0) return TYPE_CONTACTS;
        else if(getTopic_type % 3 == 1) return TYPE_DIARY;
        else return TYPE_MEMO;
    }


    /**
     *Previously, three VHs were used to display, but this is not necessary. As long as the IV and TV
        are public, the corresponding icons can be displayed according to their types.
     *@time 2021/6/14 19:20
    */
    @Override
    public void onBindViewHolder(@NonNull MainPageViewHolder holder, final int position) {
        TopicEntry topicEntry = getItem(position);

        //Log.d("test","id is:\t" + topicEntry.getEntryId() + "\tnow in the adapter:\t" + String.valueOf(topicEntry.getTopic_name()));
        if(topicEntry.getTopic_type() == 0) {
            holder.getIv_row_memo_item().setImageResource(R.drawable.iv_new_contacts);
            holder.getTv_row_memo_item().setText(topicEntry.getTopic_name());
        }
        else if(topicEntry.getTopic_type() == 1) {
            holder.getIv_row_memo_item().setImageResource(R.drawable.iv_new_diary);
            holder.getTv_row_memo_item().setText(topicEntry.getTopic_name());
        }
        else if(topicEntry.getTopic_type() == 2){
            holder.getIv_row_memo_item().setImageResource(R.drawable.iv_tag);
            holder.getTv_row_memo_item().setText(topicEntry.getTopic_name());
        }
        //** Click the corresponding icon to jump the corresponding page.
        holder.itemView.setOnClickListener(view -> {
            //Log.d("test","click" + position);
            jumpOthersPage(getItemViewType(topicEntry.getTopic_type()), position);
        });
        /*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","click" + position);
                jumpOthersPage(getItemViewType(position), position);
            }
        });
        */
        // Long click the each recyclerview item can go to the edit(detail) dialog fragment.
        holder.itemView.setOnLongClickListener(viewLong -> {
            TopicDetailDialogFragment createTopicDetailDialogFragment =
                    TopicDetailDialogFragment.newInstance(true, topicEntry.getEntryId(),
                            topicEntry.getTopic_name(), topicEntry.getTopic_type(), -1);
            createTopicDetailDialogFragment.show(activity.getSupportFragmentManager(), "TopicDetailDialogFragment");
            return true;    // caution the different between true and false
        });
    }

    public static class TopicEntryDiff extends DiffUtil.ItemCallback<TopicEntry> {

        @Override
        public boolean areItemsTheSame(@NonNull TopicEntry oldItem, @NonNull TopicEntry newItem) {
            return oldItem.getEntryId() == newItem.getEntryId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TopicEntry oldItem, @NonNull TopicEntry newItem) {
            return oldItem.getTopic_name().equals(newItem.getTopic_name());
        }
    }

    /**
     *点击不同的图标跳不同页面Ac（点通讯录跳通讯录,点日记跳日记）
     *@author home
     *@time 2021/3/28 15:57
     *@param type pos位置
    */
    public void jumpOthersPage(final int type, final int position) {
        switch (type) {
            case TYPE_CONTACTS:
                Log.d("test", "You have clicked the\t" + position + "\tContact");
                Log.d("test", "You have clicked the\t" + getItem(position).getEntryId() + "\tContact");
                Intent goContactsPageIntent = new Intent(activity, ContactsActivity.class);
                goContactsPageIntent.putExtra("topicId", getItem(position).getEntryId());
                goContactsPageIntent.putExtra("contactTitle", getItem(position).getTopic_name());
                activity.startActivity(goContactsPageIntent);
                break;
            case TYPE_DIARY:
                Intent goDiaryPageIntent = new Intent(activity, DiaryActivity.class);
                goDiaryPageIntent.putExtra("topicId", getItem(position).getEntryId());
                goDiaryPageIntent.putExtra("diaryTitle", getItem(position).getTopic_name());
                activity.startActivity(goDiaryPageIntent);
                break;
            case TYPE_MEMO:
                Log.d("test", "You have clicked the\t" + getItemCount() + "\tMemo");
//                Intent goMemoPageIntent = new Intent(activity, MemoActivity.class);
//                goMemoPageIntent.putExtra("topicId", pos);//判断时第几个按下
//                activity.startActivity(goMemoPageIntent);
//                break;
        }
    }


    public static class MainPageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private final ImageView iv_row_item;
        private final TextView tv_row_item;

        public MainPageViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_row_item = itemView.findViewById(R.id.iv_row_main_page_item);
            tv_row_item = itemView.findViewById(R.id.tv_row_main_page_item);
        }

        public ImageView getIv_row_memo_item() { return  iv_row_item;}

        public TextView getTv_row_memo_item() { return tv_row_item; }

        @Override
        public void onClick(View view) { }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }

}
