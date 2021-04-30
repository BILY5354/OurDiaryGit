package com.example.ourdiary.entries.write_page;

import android.os.Bundle;
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
import com.example.ourdiary.db.room.diary_database.Diary;
import com.example.ourdiary.entries.DiaryActivity;
import com.example.ourdiary.entries.EntriesEntity;
import com.example.ourdiary.entries.write_page.dialog_fragment.DWUpdDiaFragment;
import com.example.ourdiary.entries.write_page.dialog_fragment.DWViewDiaFragment;
import com.example.ourdiary.shared.EditMode;

import java.util.List;

/**
 *这个Adapter将会替换就得DiaryListAdapter，将Adapter与ViewHolder结合在一起使用
 *@author home
 *@time 2021/4/29 16:42
 */
public class EntriesAdapter extends ListAdapter<Diary,EntriesAdapter.EntriesViewHolder> implements EditMode {

    private DiaryActivity diaryActivity;
    private DiaryWriteFragment diaryWriteFragment;

    protected EntriesAdapter(@NonNull DiffUtil.ItemCallback<Diary> diffCallback, DiaryActivity diaryActivity, DiaryWriteFragment diaryWriteFragment) {
        super(diffCallback);
        this.diaryActivity = diaryActivity;
        this.diaryWriteFragment = diaryWriteFragment;
    }

    @NonNull
    @Override
    public EntriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_recyclerview_diary_item, parent, false);
        return new EntriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntriesAdapter.EntriesViewHolder holder, int position) {
        Diary diary  = getItem(position);
        holder.getNu(diary.getStrId());//日记序号
        holder.getTitle(diary.getDiary_Title());//标题
        holder.getContent(diary.getDiary_Content());//内容
    }

    @Override
    public boolean isEditMode() {
        return false;
    }

    @Override
    public void setEditMode(boolean editMode) {

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



    protected class EntriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        //nu 日记标号，title日记标题，content日记内容
        private final TextView diary_nu,diary_title,diary_content;
        private final ImageView iv_write_like;

        public EntriesViewHolder(@NonNull View itemView) {
            super(itemView);
            diary_nu = itemView.findViewById(R.id.tv_diary_write_view_nu);
            diary_title = itemView.findViewById(R.id.tv_diary_write_view_title);
            diary_content = itemView.findViewById(R.id.tv_diary_write_view_content);
            iv_write_like = itemView.findViewById(R.id.iv_diary_write_like);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        //那日记序号
        public void getNu(String nu) { diary_nu.setText(nu); }

        //拿标题
        public void getTitle(String text) { diary_title.setText(text); }

        //拿内容
        public void getContent(String text) { diary_content.setText(text); }

        //拿喜欢的标志
        //public ImageView bindLike(ImageView imageView) { return imageView; }

        @Override
        public void onClick(View view) {
            Diary diary = getItem(getAdapterPosition());
            Bundle result = new Bundle();
            result.putInt("bundle_specific_nu", diary.getId());
            result.putString("bundle_specific_title", diary.getDiary_Title());
            result.putString("bundle_specific_content", diary.getDiary_Content());
            diaryWriteFragment.getParentFragmentManager().setFragmentResult("requestSpecificDiary",result);

            DWViewDiaFragment dwViewDiaFragment = new DWViewDiaFragment();
            dwViewDiaFragment.show(diaryActivity.getSupportFragmentManager(), "dwViewDiaFragment");
        }

        @Override
        public boolean onLongClick(View view) {
            Diary diary = getItem(getAdapterPosition());
            Bundle result = new Bundle();
            result.putInt("bundle_specific_nu", diary.getId());
            result.putString("bundle_specific_title", diary.getDiary_Title());
            result.putString("bundle_specific_content", diary.getDiary_Content());
            diaryWriteFragment.getParentFragmentManager().setFragmentResult("requestSpecificDiary",result);

            DWUpdDiaFragment dwUpdDiaFragment = new DWUpdDiaFragment();
            dwUpdDiaFragment.show(diaryActivity.getSupportFragmentManager(), "dwUpdDiaFragment");
            return true;
        }
    }
}
