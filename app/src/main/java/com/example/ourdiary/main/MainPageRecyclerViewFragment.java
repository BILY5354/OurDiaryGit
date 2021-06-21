package com.example.ourdiary.main;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;

import com.example.ourdiary.db.room.topic_database.TopicEntry;
import com.example.ourdiary.db.room.topic_database.TopicViewModel;


public class MainPageRecyclerViewFragment extends Fragment {

    private static final String TAG = "MainPageRecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private MainActivity activity;
    private TopicViewModel mTopicViewModel;
    private int result_specific_topic_nu, result_specific_topic_type;
    private String result_title;

    public MainPageRecyclerViewFragment(MainActivity activity) { this.activity = activity; }

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER,
        GRID_LAYOUT_MANAGER,
    }


    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;

    protected RecyclerView mRecyclerView;
    protected MainPageAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String[] mDataset;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //add the specific item(contact diary memo)
        getParentFragmentManager().setFragmentResultListener("topic_detail_dia_fg_add", this, (requestKey, bundle) -> {

            result_title = bundle.getString("topic_detail_dia_fg_add_title");
            result_specific_topic_type = bundle.getInt("topic_detail_dia_fg_add_type");
            TopicEntry topicEntry = new TopicEntry(result_title, result_specific_topic_type,
                    null, null, null);
            mTopicViewModel.insertTopicEntry(topicEntry);
        });

        //update the specific item(contact diary memo)
        getParentFragmentManager().setFragmentResultListener("topic_detail_dia_fg_update", this, (requestKey, bundle) -> {

            result_specific_topic_nu = bundle.getInt("topic_detail_dia_fg_update_id");
            result_title = bundle.getString("topic_detail_dia_fg_update_title");
            result_specific_topic_type = bundle.getInt("topic_detail_dia_fg_update_type");
            TopicEntry topicEntry = new TopicEntry(result_title, result_specific_topic_type,
                    null, null, null);
            topicEntry.setEntryId(result_specific_topic_nu);
            mTopicViewModel.updateTopicEntry(topicEntry);
        });


    }


    /***/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view_frag,container,false);
        rootView.setTag(TAG);
        return rootView;
    }


    /***/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //start init
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;//这一行代码决定先显示单列还是双列
        mTopicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);

        mLinearLayoutRadioButton = view.findViewById(R.id.lineat_layout_rb);
        mGridLayoutRadioButton = view.findViewById(R.id.grid_layout_rb);

        mAdapter = new MainPageAdapter(new MainPageAdapter.TopicEntryDiff(), activity, mTopicViewModel);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            //用于保存layout类型（显示单列还是双列）
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManage(mCurrentLayoutManagerType);
        //end init

        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRecyclerViewLayoutManage(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });

        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRecyclerViewLayoutManage(LayoutManagerType.GRID_LAYOUT_MANAGER);
            }
        });

        //Observe the data
        mTopicViewModel.getAllTopicEntriesLive().observe(activity, topicEntries -> {
            mAdapter.submitList(topicEntries);
        });
    }


    /**
     *Change the RecyclerView's LayoutManage to display in single or dual columns.
     *@time 2021/3/20 0:03
    */
    public void setRecyclerViewLayoutManage(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


}
