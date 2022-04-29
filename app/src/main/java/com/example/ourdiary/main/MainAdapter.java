package com.example.ourdiary.main;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.ourdiary.write.WriteActivity;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private final MainActivity mActivity;
    private List<Article> mArticleList;
    private LoggedInUser mLoggedInUser;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView IV_item;
        TextView TV_title_item, TV_intro_item;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            IV_item = itemView.findViewById(R.id.IV_row_rv_main);
            TV_title_item = itemView.findViewById(R.id.TV_row_rv_main_title);
            TV_intro_item = itemView.findViewById(R.id.TV_row_rv_main_intro);
        }


        @Override
        public void onClick(View view) {

        }
    }

    /**
     * 传入用户信息与文章列表
     */
    public MainAdapter(MainActivity activity, LoggedInUser loggedInUser, List<Article> articleList) {
        mActivity = activity;
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

        holder.itemView.setOnClickListener(view -> {
            jumpBlogPage(article, position);
        });
    }

    /**
     * 获取当前日记内容并跳转到浏览wrtieAc
     * 使用tag List 记得向下转型
     */
    public void jumpBlogPage(Article article, final int position) {
        Intent intent = new Intent(mActivity, WriteActivity.class);
        intent.putExtra("ArticleId", article.getId());
        intent.putExtra("ArticleTitle", article.getArticleTitle());
        intent.putExtra("ArticleContent", article.getArticleContent());
        intent.putExtra("ArticleCategory", article.getCategoryName());
        //传递tagList
        Bundle bundle = new Bundle();
        bundle.putSerializable("ArticleTagList", (Serializable) article.getTagList());
        intent.putExtra("ArticleCover", article.getArticleCover());
        mActivity.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }
}
