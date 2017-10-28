package com.wwf.fw.ui.fragment;

import android.view.View;

/**
 * 作者: wwf
 * 描述:
 *
 **/

public abstract class BaseSuccessFragment extends BaseFragment {
    @Override
    public abstract View getSuccessView();

    @Override
    public Object requestData() {
        return "";
    }
}
