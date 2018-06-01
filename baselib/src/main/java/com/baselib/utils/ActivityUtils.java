package com.baselib.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 *
 *描述   ：
 *
 *作者   ：Created by DR on 2018/6/1.
 */
public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void addFragmentToFragment(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment sourceFragment,
                                             @NonNull Fragment toFragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        if (sourceFragment.isVisible()) {
            transaction.hide(sourceFragment).add(frameId, toFragment);
            transaction.commit();
        }
    }
}
