package com.wwf.fw.bean;

import android.support.v4.app.Fragment;

/**
 * 作者: wwf
 * 描述:资讯显示的bean
 *
 **/

public class FragmentInfo {
    //fragment
    public Fragment fragment;
    //title
    public String title;

    public FragmentInfo(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }
}
