package com.example.darling.MainFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.darling.MainFragments.StockFragment;
import com.example.darling.MainFragments.PageFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(PageFragment fragmentActivity){
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return (StockFragment.newInstance(position));
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
