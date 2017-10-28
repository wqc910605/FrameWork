package com.wwf.fw.ui.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wwf.fw.adapter.TablayoutAdapter;
import com.wwf.fw.bean.FragmentInfo;
import com.wwf.wwf.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者: wwf
 * 描述:
 *
 **/

public abstract class BaseTablayoutFragment extends BaseFragment {
    @Bind(R.id.tab_new_title_layout)
    TabLayout mTabNewTitleLayout;
    @Bind(R.id.vp_news_show_layout)
    ViewPager mVpNewsShowLayout;

    //显示的集合
    private List<FragmentInfo> mShowItmes = new ArrayList<>();


    @Override
    protected View getSuccessView() {
        //获取数据
        View view = View.inflate(getContext(), R.layout.fragment_tabs, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    @Override
    public Object requestData() {
        return "";
    }

    //初始化界面
    private void init() {

        mShowItmes = getItemList();


        int nomrlColor = Color.parseColor("#9c9c9c");
        int selectColor = Color.parseColor("#0B9A27");

        if (getNomrlColor() != 0) {
            nomrlColor = getNomrlColor();
        }

        if (getSelectColor() != 0) {
            selectColor = getSelectColor();
        }
        //viewpager初始化
        //只要是fragment里面的fragment管理器就用getChildFragmentManager()
        mVpNewsShowLayout.setAdapter(new TablayoutAdapter(getChildFragmentManager(), mShowItmes));

        //tablayout与viewpager进行绑定
        mTabNewTitleLayout.setupWithViewPager(mVpNewsShowLayout);

        //初始化tablayout 样式
        mTabNewTitleLayout.setTabTextColors(nomrlColor, selectColor);

        //初始化指示器的颜色
        mTabNewTitleLayout.setSelectedTabIndicatorColor(selectColor);

        //设置模式
        //填充模式
        boolean isModeScrollable = isModeScrollable();
        if (isModeScrollable) {

            //滚动模式
            mTabNewTitleLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            //填充模式
            mTabNewTitleLayout.setTabMode(TabLayout.MODE_FIXED);
        }

    }

    //选中的颜色
    public int getSelectColor() {
        return 0;
    }

    //返回一个默认的颜色
    public int getNomrlColor() {
        return 0;
    }

    public abstract List<FragmentInfo> getItemList();

    public boolean isModeScrollable() {
        return false;
    }

}
