package com.wwf.fw.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.wwf.fw.utils.Fields;


/**
 * 不知道,,以后有可能会用到
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void startFragment(String title, Class<? extends Fragment> fragmentClass, Bundle bundle) {
        startFragment(fragmentClass, true, true, title,bundle);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass) {
        startFragment(fragmentClass, true, true, "",null);
    }

    public void startFragment(String title, Class<? extends Fragment> fragmentClass) {
        startFragment(fragmentClass, true, true, title,null);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass, boolean isActionbar, boolean isArrow, String title,Bundle bundle) {
        Intent intent = new Intent(this, ShowActivity.class);
        intent.putExtra(Fields.showActivity.CLASSNAME, fragmentClass);
        intent.putExtra(Fields.showActivity.ISSHOWACTIONBAR, isActionbar);
        intent.putExtra(Fields.showActivity.ISARROW, isArrow);
        intent.putExtra(Fields.showActivity.TITLE, title);
        intent.putExtra(Fields.showActivity.BUNDLE, bundle);

        startActivity(intent);
    }
}
