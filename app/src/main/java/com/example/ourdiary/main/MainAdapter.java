package com.example.ourdiary.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.remote.data.model.Article;
import com.example.ourdiary.remote.data.model.LoggedInUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<Article> mArticleList;
    private LoggedInUser mLoggedInUser;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView IV_item;
        TextView TV_title_item, TV_intro_item;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            IV_item = itemView.findViewById(R.id.IV_row_rv_main);
            TV_title_item = itemView.findViewById(R.id.TV_row_rv_main_title);
            TV_intro_item = itemView.findViewById(R.id.TV_row_rv_main_intro);
        }
    }

    /**
     * 传入用户信息与文章列表
     */
    public MainAdapter(LoggedInUser loggedInUser, List<Article> articleList) {
        mLoggedInUser = loggedInUser;
        mArticleList = articleList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rv_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

        Article article = mArticleList.get(position);
        LoggedInUser loggedInUser = mLoggedInUser;
        holder.TV_title_item.setText(article.getArticleTitle());
        holder.TV_intro_item.setText(loggedInUser.getIntro());
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
