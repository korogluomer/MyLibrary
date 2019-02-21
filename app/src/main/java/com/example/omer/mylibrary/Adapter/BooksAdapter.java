package com.example.omer.mylibrary.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

public class BooksAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> fragmentList = new ArrayList<>();

    public BooksAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    //Slider a kitap sayısı kadar fragment eklemek için

    public void addFragments(Fragment fragment){
        fragmentList.add(fragment);
    }
}
