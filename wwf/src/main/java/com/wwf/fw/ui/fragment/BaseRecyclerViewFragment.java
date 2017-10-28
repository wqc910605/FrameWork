package com.wwf.fw.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wwf.fw.adapter.FinalListAdapter;
import com.wwf.fw.intefaces.FootType;
import com.wwf.fw.intefaces.ItemType;
import com.wwf.fw.ui.view.FootViewLayout;
import com.wwf.fw.utils.ToastUtil;
import com.wwf.fw.utils.Utils;
import com.wwf.fw.viewholder.FinalViewHolder;
import com.wwf.wwf.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 作者: wwf
 * 描述:万能的recyclerView集上拉下拉请求缓存及自动切换页面功能
 *
 **/

public abstract class BaseRecyclerViewFragment extends BaseFragment implements FinalListAdapter.AdapterBindListener, FinalListAdapter.ClickItemListener {

    @Bind(R.id.rv_new_list_layout)
    RecyclerView       mRvNewListLayout;
    @Bind(R.id.swp_new_refresh)
    SwipeRefreshLayout mSwpNewRefresh;

    //总集合
    private List<ItemType> mShowItems = new ArrayList<>();
    //万能适配器
    private FinalListAdapter mNewListAdapter;




    @Override
    protected View getSuccessView() {
        View view = View.inflate(getContext(), R.layout.fragment_base, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    //定义状态
    public static final int NONE        = 101;//空闲
    public static final int REFRESHING  = 102;//下拉刷新
    public static final int MOREREFRESH = 103;//上拉加载更多

    //定义当前的状态
    private int mCurrentRefresh = NONE;//刚开始空闲状态


    private void init() {
        //初始化步骤
        //1. 设置布局管理器
        //2. 设置adapter

        mRvNewListLayout.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        mNewListAdapter = new FinalListAdapter(getItemView(),mShowItems,this);
        mRvNewListLayout.setAdapter(mNewListAdapter);

        //设置下拉刷新
        mSwpNewRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //只有在空闲的状态下才能刷新
                if (mCurrentRefresh == NONE) {

                    //我被刷新了
                    ToastUtil.showToast("刷新了");
                    //重新请求数据

                    mCurrentRefresh = REFRESHING;
                    //刷新数据方法
                    refreshData();

                } else {
                    ToastUtil.showToast("亲别扯了,一边去!");
                }
            }
        });

        //上拉加载更多
        //根据最后一个条目显示我们去加载更多的数据
        mRvNewListLayout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mCurrentRefresh == NONE) {


                    //得到最后一个条目
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    //最后一个条目等于集合的最后一个
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == mShowItems.size() - 1 &&
                            mShowItems.get(mShowItems.size() - 1) instanceof FootType) {
                        //重新请求数据
                        mCurrentRefresh = MOREREFRESH;//加载更多
                        //刷新数据方法
                        refreshData();
                    }
                } else {
                    ToastUtil.showToast("亲,再扯打你噢!");
                }
            }
        });

        // 条目点击事件
        mNewListAdapter.setClickItemListener(this);

        //传入头部
        mNewListAdapter.setHeadLayout(getHeadview());

        //传入脚部
        mNewListAdapter.showFootView(isShowFoot());

        //设置下拉是否可用
        mSwpNewRefresh.setEnabled(isRefresh());


        //设置下拉刷新颜色
        mSwpNewRefresh.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.BLUE,Color.CYAN);
    }

    //用户是否可以刷新
    public boolean isRefresh() {
        return false;
    }

    //钩子方法
    //脚部
    public boolean isShowFoot() {
        return false;
    }

    //钩子方法
    //头部
    public int getHeadview() {
        return 0;
    }

    //子类必须传入body布局
    public abstract int getItemView();

    @Override
    public Object requestData() {

        //上拉加载更多,应该不用请求头部数据
        if (mCurrentRefresh != MOREREFRESH) {
            //请求头部数据
            requestHeadData();
        }

        //去判断如果是上拉加载更多的,那么加入下一页的token值


        //NewBodyBean newBodyBean = LoadData.getInstance().getBeanData(requestBodyURL, NewBodyBean.class);

        //子类传的数据
        List<? extends ItemType> itemList = getItemListData(mCurrentRefresh);


        //总集合大于0
        //1. body是空的,直接弹toast告诉用户信息失败
        //如果数据为空的, 那么我们应该保留以前的数据
        //2. body不是空的,那么加入到集合中
        //总集合等于0
        //1. body是空的,直接返回null
        //2. body不是空的,那么加入到集合中
        if (mShowItems.size() > 0) {
            if (itemList != null && itemList.size()  > 0) {
                //下拉刷新请求数据,第一次请求清空数据
                if (mCurrentRefresh != MOREREFRESH) {
                    //不等于上拉刷新,我们清空数据
                    mShowItems.clear();

                    /*//加入头部
                    if (mNewHeadBean != null) {
                        mShowItems.add(mNewHeadBean);
                    }*/
                }

                //赋值下一页

                mShowItems.addAll(itemList);
            } else {
                ToastUtil.showToast("网络异常");
            }
        } else {
            if (itemList != null && itemList.size() > 0) {
                //下拉刷新请求数据,第一次请求清空数据
                if (mCurrentRefresh != MOREREFRESH) {
                    //不等于上拉刷新,我们清空数据
                    mShowItems.clear();

                   /* //加入头部
                    if (mNewHeadBean != null) {
                        mShowItems.add(mNewHeadBean);
                    }*/
                }



                mShowItems.addAll(itemList);
            } else {
                //更新adapter
                Utils.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {

                        //请过完数据后状态置空
                        mCurrentRefresh = NONE;
                        //请求结束后把下拉刷新控件动画结束
                        mSwpNewRefresh.setRefreshing(false);
                    }
                });
                //直接失败了
                return null;
            }
        }
        //更新adapter
        Utils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                //使用我们的adapter刷新方法
                mNewListAdapter.update();

                mNewListAdapter.notifyDataSetChanged();
                //请过完数据后状态置空
                mCurrentRefresh = NONE;
                //请求结束后把下拉刷新控件动画结束
                mSwpNewRefresh.setRefreshing(false);
            }
        });

        return mShowItems;
    }

    //子类传入集合
    protected abstract List<? extends ItemType> getItemListData(int currentRefresh);

    //子类去请求数据
    public void requestHeadData() {
    }


    //三个更新数据的监听


    @Override
    public void bindHead(FinalViewHolder holder, ItemType itemType) {

    }

    @Override
    public abstract void bindBody(FinalViewHolder holder, ItemType itemType);

    //脚本监听,用来切换脚部
    @Override
    public void bindFoot(FootViewLayout footViewLayout) {

    }

    //钩子方法
    @Override
    public void click(int position,ItemType itemType) {
    }

    //带动画的下拉刷新
    public void refreshAniam() {
        mSwpNewRefresh.setRefreshing(true);
        refreshData();
    }

    //获取总条目数据
    public List<ItemType> getShowItems() {
        return mShowItems;
    }
}
