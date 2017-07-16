package com.lzm.knittinghelp2.helpers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.R;

public class FragmentHelper {

    public static void openFragment(MainActivity context, Fragment fragment, String title) {
        openFragment(context, fragment, title, null);
    }

    public static void openFragment(MainActivity context, Fragment fragment, String title, Bundle args) {
        openFragment(context, fragment, title, args, true);
    }

    public static void openFragment(MainActivity context, Fragment fragment, String title, boolean backstack) {
        openFragment(context, fragment, title, null, backstack);
    }

    private static void openFragment(MainActivity context, Fragment fragment, String title, Bundle args, boolean backstack) {
        context.setTitle(title);
        FragmentManager fragmentManager = context.getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        if (args != null) {
            fragment.setArguments(args);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);

        if (backstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
