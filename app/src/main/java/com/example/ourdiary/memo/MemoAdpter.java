//package com.example.ourdiary.memo;
//
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.StrikethroughSpan;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.ourdiary.R;
//import com.example.ourdiary.db.mydiary.DBManager;
//import com.example.ourdiary.main.recycleview.MainPageAdapter;
//import com.example.ourdiary.shared.EditMode;
//
//import java.util.List;
//
//public class MemoAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements EditMode {
//
//   private List<MemoEntity> memoList;
//
//   private long topicId;
//   private DBManager dbManager;
//   private boolean isEditMode = false;
//
//    public MemoAdpter(List<MemoEntity> memoList, long topicId,
//                      DBManager dbManager ) {
//        this.memoList = memoList;
//        this.topicId = topicId;
//        this.dbManager = dbManager;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.rv_memo_item,parent,false);
//        return new MemoViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
////测试用        ((MemoAdpter.MemoViewHolder)holder).getTextView().setText("测试Memo");
//        if (holder instanceof MemoViewHolder) {
//            ((MemoViewHolder) holder).setItemPosition(position);
//            ((MemoViewHolder) holder).initView();
//            setMemoContent(((MemoViewHolder) holder), position);
//        }
//    }
//
//    private void setMemoContent(MemoViewHolder holder, final int position) {
//        if(memoList.get(position).isChecked()) {
//            SpannableString spannableContent = new SpannableString(memoList.get(position).getContent());
//            spannableContent.setSpan(new StrikethroughSpan(), 0, spannableContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.getTVContent().setText(spannableContent);
//            holder.getTVContent().setAlpha(0.4F);
//        } else {
//            holder.getTVContent().setText(memoList.get(position).getContent());
//            holder.getTVContent().setAlpha(1F);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return memoList.size();
//    }
//
//
//    private class MemoViewHolder extends RecyclerView.ViewHolder implements
//            View.OnClickListener {
//
//        private View rootView;
//        private ImageView iv_memo_item_dot;
//        private ImageView iv_memo_item_delete;
//        private TextView tv_memo_item_content;
//        private RelativeLayout rl_memo_item_root_view;
//        private int itemPosition;
//
//
//        public MemoViewHolder(@NonNull View itemView) {
//            super(itemView);
//            this.rootView = itemView;
//            rl_memo_item_root_view = rootView.findViewById(R.id.rl_memo_item_root_view);
//            iv_memo_item_dot = rootView.findViewById(R.id.iv_memo_item_dot);
//            tv_memo_item_content = rootView.findViewById(R.id.tv_memo_item_content);
//            iv_memo_item_delete = rootView.findViewById(R.id.iv_memo_item_delete);
//        }
//
//        @Override
//        public void onClick(View view) {
//            switch (view.getId()) {
//                case R.id.iv_memo_item_delete:
//                    dbManager.opeDB();
//                    dbManager.delMemo(memoList.get(itemPosition).getMemoId());
//                    dbManager.deleteMemoOrder(memoList.get(itemPosition).getMemoId());
//                    dbManager.closeDB();
//                    memoList.remove(itemPosition);
//                    notifyDataSetChanged();
//                    break;
//                case R.id.rl_memo_item_root_view://给memo加入划线
////测试用                    Log.d("test","click rl");
//                    memoList.get(itemPosition).toggleChecked();
//                    dbManager.opeDB();
//                    dbManager.updateMemoChecked(memoList.get(itemPosition).getMemoId(), memoList.get(itemPosition).isChecked());
//                    dbManager.closeDB();
//                    setMemoContent(this, itemPosition);
//                    break;
//            }
//        }
//
//        private void initView() {
//            if (isEditMode) {
//                iv_memo_item_delete.setVisibility(View.VISIBLE);
//                iv_memo_item_delete.setOnClickListener(this);
//                rl_memo_item_root_view.setOnClickListener(this);
//            } else {
//                iv_memo_item_delete.setVisibility(View.GONE);
//                iv_memo_item_delete.setOnClickListener(null);
//                rl_memo_item_root_view.setOnClickListener(this);
//            }
//        }
//
//        public TextView getTVContent() {
//            return tv_memo_item_content;
//        }
//
//        private void setItemPosition(int itemPosition) {
//            this.itemPosition = itemPosition;
//        }
//    }
//
//
//    @Override
//    public boolean isEditMode() {
//        return isEditMode;
//    }
//
//    @Override
//    public void setEditMode(boolean editMode) {
//        isEditMode = editMode;
//    }
//}
