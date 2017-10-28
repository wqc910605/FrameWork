package com.wwf.fw.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wwf.fw.ui.activity.ShowActivity;
import com.wwf.fw.ui.view.LoaderPager;
import com.wwf.fw.utils.Fields;


/**
 * 作者: wwf
 * 描述:
 *
 **/

public abstract class BaseFragment extends Fragment {

    private LoaderPager mLoaderPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mLoaderPager == null) {
            mLoaderPager = new LoaderPager(container.getContext()) {
                @Override
                protected Object getNetData() {
                    return requestData();
                }


                @Override
                protected View createSuccessView() {
                    return getSuccessView();
                }
            };
        }


        return mLoaderPager;
    }

    //得到一个成功的view
    protected abstract View getSuccessView();

    //请求数据
    public abstract Object requestData();

    //刷新方法
    public void refreshData() {
        mLoaderPager.loadData();
    }


    public void startFragment(String title, Class<? extends Fragment> fragmentClass,Bundle bundle) {
        startFragment(fragmentClass, true, true, title,bundle);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass) {
        startFragment(fragmentClass, true, true, "",null);
    }

    public void startFragment(String title, Class<? extends Fragment> fragmentClass) {
        startFragment(fragmentClass, true, true, title,null);
    }

    public void startFragment(Class<? extends Fragment> fragmentClass, boolean isActionbar, boolean isArrow, String title,Bundle bundle) {
        Intent intent = new Intent(getContext(), ShowActivity.class);
        intent.putExtra(Fields.showActivity.CLASSNAME, fragmentClass);
        intent.putExtra(Fields.showActivity.ISSHOWACTIONBAR, isActionbar);
        intent.putExtra(Fields.showActivity.ISARROW, isArrow);
        intent.putExtra(Fields.showActivity.TITLE, title);
        intent.putExtra(Fields.showActivity.BUNDLE, bundle);

        startActivity(intent);
    }

    public void startFragmentForResult(Class<? extends Fragment> fragmentClass, boolean isActionbar, boolean isArrow, String title,Bundle bundle,int requestCode) {
        Intent intent = new Intent(getContext(), ShowActivity.class);
        intent.putExtra(Fields.showActivity.CLASSNAME, fragmentClass);
        intent.putExtra(Fields.showActivity.ISSHOWACTIONBAR, isActionbar);
        intent.putExtra(Fields.showActivity.ISARROW, isArrow);
        intent.putExtra(Fields.showActivity.TITLE, title);
        intent.putExtra(Fields.showActivity.BUNDLE, bundle);

        startActivityForResult(intent,requestCode);
    }
    public void startFragmentForResult(Class<? extends Fragment> fragmentClass,int requestCode) {
        startFragmentForResult(fragmentClass,false,true,"",null,requestCode);
    }


    @Override
    public void onResume() {
        super.onResume();
       /* if (this instanceof MyTweentFragment) {
            //这是我的动弹
            System.out.println("我的动弹");
            mLoaderPager.loadData();
        }*/
        //如果以后需要根据实现数据进行显示,可以在这里进行判断处理

    }


}
