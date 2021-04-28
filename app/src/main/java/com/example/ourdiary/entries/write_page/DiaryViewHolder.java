package com.example.ourdiary.entries.write_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import static com.example.ourdiary.entries.write_page.LikeImageData.IMAGE_DATA;

public class DiaryViewHolder extends RecyclerView.ViewHolder {

    //nu 日记标号，title日记标题，content日记内容
    private final TextView diary_nu,diary_title,diary_content;
    ImageView iv_write_like;

    public DiaryViewHolder(@NonNull View itemView) {
        super(itemView);
        diary_nu = itemView.findViewById(R.id.tv_diary_write_view_nu);
        diary_title = itemView.findViewById(R.id.tv_diary_write_view_title);
        diary_content = itemView.findViewById(R.id.tv_diary_write_view_content);
        iv_write_like = itemView.findViewById(R.id.iv_diary_write_like);

    }

    //那日记序号
    public void bindNu(String nu) { diary_nu.setText(nu); }

    //拿标题
    public void bindTitle(String text) { diary_title.setText(text); }

    //拿内容
    public void bindContent(String text) { diary_content.setText(text); }

    //拿喜欢的标志
    public ImageView bindLike(ImageView imageView) { return imageView; }

    static DiaryViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_recyclerview_diary_item, parent, false);
        return new DiaryViewHolder(view);
    }

}
