package org.maktab.maktablauncher.controller.activity;

import androidx.fragment.app.Fragment;

import org.maktab.maktablauncher.controller.fragment.MaktabLauncherFragment;

public class MaktabLauncherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return MaktabLauncherFragment.newInstance();
    }
}