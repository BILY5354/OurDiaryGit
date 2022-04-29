package com.example.ourdiary.write;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ourdiary.R;
import com.example.ourdiary.remote.data.model.Article;
import com.example.ourdiary.remote.data.model.Tag;

import java.util.List;
import java.util.Locale;

/**
 * 浏览博客与写博客的页面为公用的
 */
public class WriteActivity extends AppCompatActivity {

    /**
     * Intent获取的文章数据
     */
    private Integer id;
    private String title;
    private String content;
    private String category;
    private List<Tag> tagList;
    private String cover;

    /**
     * 页面组件
     */
    private TextView TV_write_title, TV_write_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        //获取intent
        loadArticle();
        //组件初始化
        init();
        //如果有数据则赋值 虽然传递的是一个list 但是该list只有一个数据 所以下标为0
        if (id != -1) {
            TV_write_title.setText(title);
            TV_write_content.setText(content);
        }
    }

    private void loadArticle() {
        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        id = it.getIntExtra("ArticleId", -1); //-1即获取失败
        title = it.getStringExtra("ArticleTitle");
        content = it.getStringExtra("ArticleContent");
        category = it.getStringExtra("ArticleCategory");
        tagList = (List<Tag>) bundle.getSerializable("ArticleTagList");
        cover = it.getStringExtra("ArticleCover");
    }

    private void init() {
        TV_write_title = findViewById(R.id.TV_write_title);
        TV_write_content = findViewById(R.id.TV_write_content);
    }
}