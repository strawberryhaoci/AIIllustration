package com.util;/*
 *name: AIIllustration
 *description:
 */

import com.home.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @program: AIIllustration
 * @description: fragment operation
 */
public class FragmentUtil {
    public static void replaceFragment(FragmentManager fragmentManager,Fragment fragment){
//        FragmentManager fragmentManager = fragment.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.silde_in_right,R.anim.slide_out_left);
        transaction.replace(R.id.nav_host_fragment,fragment);
        transaction.commit();
    }
}
