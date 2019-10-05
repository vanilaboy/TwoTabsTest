package org.bitart.twotabstest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class TabActivity extends AppCompatActivity {

    private final static String SELECTED_TAB = "selected_tab";
    private final static String FIRST_FRAGMENT = "animal_fragment";
    private final static String SECOND_FRAGMENT = "second_fragment";

    private TabLayout mTabLayout;

    private Fragment mTab1;
    private Fragment mTab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int selectedPosition = 0;
        if (savedInstanceState != null) {
            mTab1 = getSupportFragmentManager().getFragment(savedInstanceState, FIRST_FRAGMENT);
            mTab2 = getSupportFragmentManager().getFragment(savedInstanceState, SECOND_FRAGMENT);
            selectedPosition = savedInstanceState.getInt(SELECTED_TAB);
        }

        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showFragment(FIRST_FRAGMENT, mTab1, "cat");
                        break;


                    case 1:
                        showFragment(SECOND_FRAGMENT, mTab2, "dog");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                onTabSelected(tab);
            }
        });

        mTabLayout.getTabAt(selectedPosition).select();

    }

    private void showFragment(String tag, Fragment fragment, String animalName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragment == null)
            fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null)
            fragment = new AnimalFragment(animalName);

        for (Fragment fr : fragmentManager.getFragments()) {
            fragmentManager.beginTransaction()
                    .hide(fr)
                    .commit();
        }
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, tag)
                    .commit();
        }
        fragmentManager.beginTransaction()
                .show(fragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAB, mTabLayout.getSelectedTabPosition());
        if (mTab1 != null && mTab1.isAdded())
            getSupportFragmentManager().putFragment(outState, FIRST_FRAGMENT, mTab1);
        if (mTab2 != null && mTab2.isAdded())
            getSupportFragmentManager().putFragment(outState, SECOND_FRAGMENT, mTab2);
    }
}
