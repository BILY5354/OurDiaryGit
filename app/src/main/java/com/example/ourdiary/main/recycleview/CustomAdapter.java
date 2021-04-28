package com.example.ourdiary.main.recycleview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;

/**
 *这个相当是HelloWorld中的GridAdapter
 *@author home
 *@time 2021/3/19 20:53
*/

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "CustomAdapter";
    private String[] mDataSet;
    private OnItemClickListener mListener;
    private OnItemLongClickListener lListener;


    /**
     *本构造器方法，初始化dataset
     *@author home
     *@time 2021/3/19 23:42
    */
    public CustomAdapter(String[] dataset, OnItemClickListener listener,
                         OnItemLongClickListener lListener) {
        mDataSet = dataset;
        this.mListener = listener;
        this.lListener = lListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创造一个新视图
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.text_row_item,parent,false);//view就是每个item长什么样的布局
        return new ViewHolder(v);
    }

    /**
     *增加了点击和长按显示toast的方法为日后功能留用
     *@author home
     *@time 2021/3/20 0:44
    */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"Element" + position + " set.");
        holder.getTextView().setText(mDataSet[position]);
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

    /**
     *这里返回的长度就是一共有多少个 testview（因为这里显示的内容就是textview）
     *@author home
     *@time 2021/3/19 23:36
    */
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     * 声明控件的内容
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = v.findViewById(R.id.textView);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

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
