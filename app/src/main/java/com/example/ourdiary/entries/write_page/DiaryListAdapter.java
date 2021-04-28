package com.example.ourdiary.entries.write_page;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.ourdiary.db.room.diary_database.Diary;
import com.example.ourdiary.main.recycleview.CustomAdapter;

/**
 *这里没有getItem，即没有获取数量的，因为它会新建DiaryWriteFragment写
 *@author home
 *@time 2021/4/18 14:37
*/
public class DiaryListAdapter extends ListAdapter<Diary, DiaryViewHolder> {

    private OnItemClickListener mListener;
    private OnItemLongClickListener lListener;

    //不用监听器就用这些参数new
    protected DiaryListAdapter(@NonNull DiffUtil.ItemCallback<Diary> diffCallback) {
        super(diffCallback);
    }

    //用短监听和长监听就用这些参数 new，同理只想要一种监听根据参数填入即可
    protected DiaryListAdapter(@NonNull DiffUtil.ItemCallback<Diary> diffCallback,
                               OnItemClickListener listener,
                               OnItemLongClickListener lListener) {
        super(diffCallback);
        this.mListener = listener;
        this.lListener = lListener;
    }


    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return DiaryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        Diary current  = getItem(position);
        holder.bindNu(current.getStrId());//日记序号
        holder.bindTitle(current.getDiary_Title());//标题
        holder.bindContent(current.getDiary_Content());//内容

        //设置监听器 要实现什么功能在 DiaryWriteFragment写
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                lListener.onLongClick(position);
                return true;//注意true和false的区别
            }
        });
    }

    public static class DiaryDiff extends DiffUtil.ItemCallback<Diary> {

        @Override
        public boolean areItemsTheSame(@NonNull Diary oldItem, @NonNull Diary newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Diary oldItem, @NonNull Diary newItem) {
            return oldItem.getDiary_Title().equals(newItem.getDiary_Title());
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

    /**
     *自己写的 长按监听
     *@author home
     *@time 2021/3/4 20:42
     */
    public interface OnItemLongClickListener {
        void onLongClick(int pos);
    }


}
