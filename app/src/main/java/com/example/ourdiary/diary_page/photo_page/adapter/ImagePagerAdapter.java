package com.example.ourdiary.diary_page.photo_page.adapter;

import static com.example.ourdiary.diary_page.photo_page.adapter.ImageData.IMAGE_DRAWABLES;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ourdiary.diary_page.photo_page.fragment.ImageFragment;

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    public ImagePagerAdapter(Fragment fragment) {
        // Note: Initialize with the child fragment manager.
        super(fragment.getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return ImageFragment.newInstance(IMAGE_DRAWABLES[position]);
    }

    @Override
    public int getCount() {
        return IMAGE_DRAWABLES.length;
    }
}
