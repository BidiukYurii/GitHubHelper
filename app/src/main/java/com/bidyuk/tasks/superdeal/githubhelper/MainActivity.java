package com.bidyuk.tasks.superdeal.githubhelper;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bidyuk.tasks.superdeal.githubhelper.fragments.RepositoryFragment;
import com.bidyuk.tasks.superdeal.githubhelper.fragments.SearchUserFragment;

import org.eclipse.egit.github.core.User;

public class MainActivity extends AppCompatActivity {

    private Fragment currentActiveFragment;
    private static final String STATE_TAG ="Tag1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentActiveFragment != null)
            replaceFragment(currentActiveFragment);
        else
            showSearchUserFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager manager = getSupportFragmentManager();
        if (currentActiveFragment.isAdded())
            manager.putFragment(outState, STATE_TAG, currentActiveFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        instantiateFragments(inState);
    }

    private void replaceFragment(Fragment fragment) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }

    private void instantiateFragments(Bundle inState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (inState != null) {
            currentActiveFragment = manager.getFragment(inState, STATE_TAG);
        }
    }

    public void showSearchUserFragment() {
        currentActiveFragment = new SearchUserFragment();
        replaceFragment(new SearchUserFragment());
    }

    public void showRepositoryFragment(User user) {
        currentActiveFragment = RepositoryFragment.newInstance(user);
        replaceFragment(currentActiveFragment);
    }

}
