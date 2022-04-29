# 命名规范
1. 在xml中 组件开头一律大写，如 ImageView 命名为 IV_main
2. 资源图片一律命名开头为 ic 小写，并且是什么就叫什么 不要ic_main_setting 直接ic_setting 即可

# intent 列表 位于
- ```MainAdapter```
    ```
    intent.putExtra("ArticleId", article.getId());
    intent.putExtra("ArticleTitle", article.getArticleTitle());
    intent.putExtra("ArticleContent", article.getArticleContent());
    intent.putExtra("ArticleCategory", article.getCategoryName());
    intent.putExtra("ArticleTagList", (Serializable) article.getTagList());
    intent.putExtra("ArticleCover", article.getArticleCover());
    ```