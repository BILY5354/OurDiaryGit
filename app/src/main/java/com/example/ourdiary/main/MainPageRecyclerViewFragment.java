package com.example.ourdiary.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ourdiary.R;
import com.example.ourdiary.db.room.topic_database.TopicViewModel;


public class MainPageRecyclerViewFragment extends Fragment {

    private static final String TAG = "MainPageRecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private MainActivity activity;
    private TopicViewModel mTopicViewModel;

    public MainPageRecyclerViewFragment(MainActivity activity) {
        this.activity = activity;

    }

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


    }

    /***/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recycler_view_frag,container,false);
        rootView.setTag(TAG);
//        initDataset();
        return rootView;
    }

    /***/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;//这一行代码决定先显示单列还是双列
        mTopicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);

        if (savedInstanceState != null) {
            //用于保存layout类型（显示单列还是双列）
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        setRecyclerViewLayoutManage(mCurrentLayoutManagerType);

        mAdapter = new MainPageAdapter(new MainPageAdapter.TopicEntryDiff(), activity, mTopicViewModel);
        mRecyclerView.setAdapter(mAdapter);

        mLinearLayoutRadioButton = view.findViewById(R.id.lineat_layout_rb);
        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRecyclerViewLayoutManage(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
            }
        });

        mGridLayoutRadioButton = view.findViewById(R.id.grid_layout_rb);
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

//    /**
//     * Generates Strings for RecyclerView's adapter. This data would usually come
//     * from a local content provider or remote server.
//     */
//    private void initDataset() {
//        mDataset = new String[DATASET_COUNT];
//        int sign = 0;
//        for(int i = 0; i < DATASET_COUNT; i++) {
//            if( i % 3 == 0) mDataset[i] = "This is Contacts # " + ++sign;
//            else if(i % 3 == 1)  mDataset[i] = "This is Diary #" + sign;
//            else if( i % 3 == 2) mDataset[i] = "This is Memo #" + sign;
//        }
//    }
}
