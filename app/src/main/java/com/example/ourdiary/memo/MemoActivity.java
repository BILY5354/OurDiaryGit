//package com.example.ourdiary.memo;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.example.ourdiary.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MemoActivity extends AppCompatActivity
//        implements View.OnClickListener, EditMemoDialogFragment.MemoCallback{
//
//    /**
//     * getId
//     * ***/
//    private long topicId ;//测试用1 数据库同名元素
//    private int needTranstopicId;//需要转换成正常的topid
//
//    /***
//     * UI
//     * **/
//    private ImageView iv_memo_edit;
//    private View rootView;
//    private TextView tv_memo_item_add;
//
//    /**
//     * DB
//     * ***/
//    private DBManager dbManager;
//
//    /**
//     * RecyclerView
//     * **/
//    private RecyclerView RecyclerView_memo;
//    private MemoAdpter memoAdpter;
//    private List<MemoEntity> memoList;
//
//    @Override
//    public void onBackPressed() {
//        if (memoAdpter.isEditMode()) {
//            setEditModeUI(memoAdpter.isEditMode());
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_memo);
//        setTitle("Memo页面");
//        needTranstopicId = (int) getIntent().getIntExtra("topicId",-1);//获取是第几个memo进入
//        Log.d("test", "needTranstopicId is" + needTranstopicId);
//        transTopicId(needTranstopicId);
//        if(topicId == -1) {
//            finish();
//        }
//
//        iv_memo_edit = findViewById(R.id.iv_memo_write);
//        iv_memo_edit.setOnClickListener(this);
//        RecyclerView_memo = findViewById(R.id.recyclerView_memo);
//        rootView = findViewById(R.id.layout_memo_item_add);
//        tv_memo_item_add = rootView.findViewById(R.id.tv_memo_item_add);
//        tv_memo_item_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditMemoDialogFragment editMemoDialogFragment = EditMemoDialogFragment.newInstance(
//                        topicId, -1, true, "");
//                editMemoDialogFragment.show(getSupportFragmentManager(),"editMemoDialogFragment");
//            }
//        });
////测试用        RecyclerView_memo.setLayoutManager(new LinearLayoutManager(MemoActivity.this));
////测试用        RecyclerView_memo.setAdapter(new MemoAdpter());
//        memoList = new ArrayList<>();
//        dbManager = new DBManager(MemoActivity.this);
//        loadMemo(true);
//        initTopicAdapter();
//    }
//
//    private void loadMemo(boolean openDB) {
//        memoList.clear();
//        if(openDB) {
//            dbManager.opeDB();
//        }
//        Cursor memoCursor = dbManager.selectMemoAndMemoOrder(topicId);//根据不同的topid获取不同的memo
//        for (int i = 0; i< memoCursor.getCount(); i++) {
//            memoList.add(
//                    new MemoEntity(memoCursor.getLong(0),memoCursor.getString(2),memoCursor.getInt(3) > 0 ? true : false));
//            memoCursor.moveToNext();
//        }
//        memoCursor.close();
//        if(openDB) {
//            dbManager.closeDB();
//        }
//    }
//
//    private void initTopicAdapter() {
//        //初始化adapter
//        LinearLayoutManager lmr = new LinearLayoutManager(this);
//        RecyclerView_memo.setLayoutManager(lmr);
//        RecyclerView_memo.setHasFixedSize(true);
//        memoAdpter = new MemoAdpter(memoList,topicId,dbManager);
//        RecyclerView_memo.setAdapter(memoAdpter);
//    }
//
//    public void setEditModeUI(boolean isEditMode) {
//
//        if(isEditMode) {
//            iv_memo_edit.setImageDrawable(getResources().getDrawable(R.drawable.iv_memo_write));
//            rootView.setVisibility(View.GONE);
//        } else {
//            iv_memo_edit.setImageDrawable(getResources().getDrawable(R.drawable.iv_memo_unwrite));
//            rootView.setVisibility(View.VISIBLE);
//        }
//        memoAdpter.setEditMode(!isEditMode);//记得取反啊！！
//        memoAdpter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void addMemo(String memoContent) {
//        dbManager.opeDB();
//
//        //Create newMemoEntity into List first
//        MemoEntity newMemoEntity = new MemoEntity(dbManager.insertMemo(memoContent, false, topicId)
//                , memoContent, false);
//        memoList.add(0, newMemoEntity);
//        //Get size
//        int orderNumber = memoList.size();
//        //Remove this topic's all memo order
//        dbManager.deleteAllCurrentMemoOrder(topicId);
//        //sort the memo order
//        for (MemoEntity memoEntity : memoList) {
//            dbManager.insertMemoOrder(topicId, memoEntity.getMemoId(), --orderNumber);
//        }
//        //Load again
//        loadMemo(false);
//        dbManager.closeDB();
//        memoAdpter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void updateMemo() {
//        loadMemo(true);
//        memoAdpter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_memo_write:
//                setEditModeUI(memoAdpter.isEditMode());
////测试用                Log.d("test","click button");
//                break;
//        }
//    }
//
//    /**
//     *因为是自己写主界面的缘故，memo的序号并不正常 现在增加转换函数
//     * 就两个值，1和2
//     *@author home
//     *@time 2021/3/31 13:56
//    */
//    private void transTopicId( int testTopicId) {
//        if(testTopicId == 2) topicId = 1;
//        else topicId = 2;
//    }
//}