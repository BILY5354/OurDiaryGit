package com.example.ourdiary.main.recycleview;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.contacts.ContactsActivity;
import com.example.ourdiary.entries.DiaryActivity;
import com.example.ourdiary.main.MainActivity;

public class MainPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private String[] mDataSet;
    private OnItemClickListener mListener;
    private MainActivity activity;

    final int TYPE_CONTACTS = 0;
    final int TYPE_DIARY = 1;
    final int TYPE_MEMO = 2;


    public MainPageAdapter(MainActivity activity, String[] dataset, OnItemClickListener listener) {
        this.activity = activity;
        this.mDataSet = dataset;
        this.mListener = listener;
    }


    /**
     * 0显示通讯率 1显示日记 3显示memo
     *@author home
     *@time 2021/3/28 10:17
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            return  new ContactsViewHolder(LayoutInflater.from
                    (parent.getContext()).inflate(R.layout.row_contacts_item, parent, false));
        }
        else if(viewType == 1) {
            return new DiaryViewHolder(LayoutInflater.from
                    (parent.getContext()).inflate(R.layout.row_diary_item, parent, false));
        }
        else {
            return new MemoViewHolder(LayoutInflater.from
                    (parent.getContext()).inflate(R.layout.row_memo_item,parent,false));
        }
    }

    /**
     *知道现在按下什么图标，
     *@author home
     *@time 2021/3/28 22:37
    */
    @Override
    public int getItemViewType(int position) {
        if(position % 3 == 0) return TYPE_CONTACTS;
        else if(position % 3 == 1) return TYPE_DIARY;
        else return TYPE_MEMO;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(getItemViewType(position) == 0) {
            ((ContactsViewHolder)holder).getTv_row_contacts_item().setText(mDataSet[position]);
        }
        else if(getItemViewType(position) == 1) {
            ((DiaryViewHolder)holder).getTv_row_diary_item().setText(mDataSet[position]);
        }
        else {
            ((MemoViewHolder)holder).getTv_row_memo_item().setText(mDataSet[position]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("test","click" + position);
                jumpOthersPage(getItemViewType(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    /**
     *点击不同的图标跳不同页面Ac（点通讯录跳通讯录,点日记跳日记）
     *@author home
     *@time 2021/3/28 15:57
     *@param type pos位置
    */
    public void jumpOthersPage(final int type, int pos) {
        switch (type) {
            case TYPE_CONTACTS:
                Intent goContatsPageIntent = new Intent(activity, ContactsActivity.class);
                activity.startActivity(goContatsPageIntent);
                break;
            case TYPE_DIARY:
                Intent goDiaryPageIntent = new Intent(activity, DiaryActivity.class);
                activity.startActivity(goDiaryPageIntent);
                break;
//            case TYPE_MEMO:
//                Intent goMemoPageIntent = new Intent(activity, MemoActivity.class);
//                goMemoPageIntent.putExtra("topicId", pos);//判断时第几个按下
//                activity.startActivity(goMemoPageIntent);
//                break;
        }
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_row_contacts_item;
        private TextView tv_row_contacts_item;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"Contacts Element " + getAdapterPosition() + "clicker.");
                }
            });
            iv_row_contacts_item = itemView.findViewById(R.id.iv_row_contacts_item);
            tv_row_contacts_item = itemView.findViewById(R.id.tv_row_contacts_item);
        }

        public TextView getTv_row_contacts_item() {
            return tv_row_contacts_item;
        }
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_row_diary_item;
        private TextView tv_row_diary_item;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"Diary Element " + getAdapterPosition() + "clicker.");
                }
            });
            iv_row_diary_item = itemView.findViewById(R.id.iv_row_diary_item);
            tv_row_diary_item = itemView.findViewById(R.id.tv_row_diary_item);
        }

        public TextView getTv_row_diary_item() {
            return tv_row_diary_item;
        }
    }

    class MemoViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_row_memo_item;
        private TextView tv_row_memo_item;

        public MemoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"Memo Element " + getAdapterPosition() + "clicker.");
                }
            });
            iv_row_memo_item = itemView.findViewById(R.id.iv_row_memo_item);
            tv_row_memo_item = itemView.findViewById(R.id.tv_row_memo_item);
        }

        public TextView getTv_row_memo_item() {
            return tv_row_memo_item;
        }
    }

    /**
     *自己写的 短按监听
     *@author home
     *@time 2021/3/4 20:43
     */
    public interface OnItemClickListener {
        void onClick(int pos);
    }
}
