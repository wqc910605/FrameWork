package com.wwf.fw.viewholder;

import android.view.View;

import butterknife.ButterKnife;

/**
 * 作者: wwf
 * 描述:用来封装item布局
 *
 **/
public abstract class BaseViewHolder<T> {


    private View view;//这是布局

    public BaseViewHolder() {
        view = createItemView();

        ButterKnife.bind(this,view);
        //绑定
        view.setTag(this);

    }

    public abstract View createItemView() ;

    //更新数据
    public abstract void bindView(T t) ;

    //返回view的方法
    public View getView() {
        return view;
    }

}
