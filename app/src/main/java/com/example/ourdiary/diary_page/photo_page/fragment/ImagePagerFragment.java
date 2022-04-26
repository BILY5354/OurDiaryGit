package com.example.ourdiary.diary_page.photo_page.fragment;

import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ourdiary.R;
import com.example.ourdiary.diary_page.DiaryActivity;
import com.example.ourdiary.diary_page.photo_page.adapter.ImagePagerAdapter;

import java.util.List;
import java.util.Map;

public class ImagePagerFragment extends Fragment {

    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager) inflater.inflate(R.layout.fragment_pager,
                container,false);
        viewPager.setAdapter(new ImagePagerAdapter(this));
        //添加最近的位置并增加一个可以当滑动页面时更新选择协调器的位置监听器
        viewPager.setCurrentItem(DiaryActivity.currentPosition);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                DiaryActivity.currentPosition = position;
            }
        });

        prepareSharedElementTransition();

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return viewPager;
    }

    /**
     * Prepares the shared element transition from and back to the grid fragment.
     */
    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the image view at the primary fragment (the ImageFragment that is currently
                        // visible). To locate the fragment, call instantiateItem with the selection position.
                        // At this stage, the method will simply return the fragment at the position and will
                        // not create a new one.
                        Fragment currentFragment = (Fragment) viewPager.getAdapter()
                                .instantiateItem(viewPager, DiaryActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }

                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), view.findViewById(R.id.iv_diary_photo_page));
                    }
                });
    }
}