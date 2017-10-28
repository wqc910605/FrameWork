package com.wwf.fw.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wwf.fw.bean.HeadTypeBean;
import com.wwf.fw.bean.NewFootBean;
import com.wwf.fw.intefaces.BodyType;
import com.wwf.fw.intefaces.FootType;
import com.wwf.fw.intefaces.HeadType;
import com.wwf.fw.intefaces.ItemType;
import com.wwf.fw.ui.view.FootViewLayout;
import com.wwf.fw.viewholder.FinalViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wwf
 * 描述:
 *
 **/

public class FinalListAdapter extends RecyclerView.Adapter<FinalViewHolder> {

    private List<ItemType> mShowItems = new ArrayList<>();
    //头部的布局
    private int mHeadLayout;
    //body的布局
    private int mBodyLayout;
    private FootViewLayout mFootViewLayout;

    public FinalListAdapter(int bodyLayout, List<ItemType> showItems, AdapterBindListener adapterBindListener) {
        this.mAdapterBindListener = adapterBindListener;
        this.mBodyLayout = bodyLayout;
        mShowItems = showItems;
    }

    //返回一个viewholder
    @Override
    public FinalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //        View view = View.inflate(parent.getContext(), R.layout.item_list_news, null);
        View view = null;
        switch (viewType) {
            case HEADTYPE:
                // view = View.inflate(parent.getContext(), R.layout.item_list_news_header, null);

                //这里headview这个应该让展示的时候自己传入,谁用谁传
                //1. 是否是必须的
                //2. 不是必须的

                view = LayoutInflater.from(parent.getContext()).inflate(mHeadLayout, parent, false);
                break;
            case BODYTYPE:

                //1. 是否是必须的
                //2. 必须的
                //3. 抽象跟构造
                //4. 要不要子类
                //5. 构造
                if (mBodyLayout == 0) {
                    throw new RuntimeException("SB传入item布局吧");
                }
                view = LayoutInflater.from(parent.getContext()).inflate(mBodyLayout, parent, false);
                break;
            case FOOTTYPE:
                //这个底部管理的类
                mFootViewLayout = new FootViewLayout(parent.getContext());
                view = mFootViewLayout;
                break;

            default:
                break;

        }

        return new FinalViewHolder(view);
    }

    //更新数据
    @Override
    public void onBindViewHolder(FinalViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickItemListener.click(position,mShowItems.get(position));
            }
        });

        //更新数据
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HEADTYPE:


                mAdapterBindListener.bindHead(holder,mShowItems.get(position));
                break;
            case BODYTYPE:

                mAdapterBindListener.bindBody(holder,mShowItems.get(position));

                break;
            case FOOTTYPE:

                mAdapterBindListener.bindFoot(mFootViewLayout);

                break;

            default:
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mShowItems.size();
    }

    //多条目
    public static final int HEADTYPE = 101;
    public static final int BODYTYPE = 102;
    public static final int FOOTTYPE = 103;


    //类型
    @Override
    public int getItemViewType(int position) {
       /* if (0 == position) {
            return HEADTYPE;
        }
        if (mShowItems.size() - 1 == position) {
            return FOOTTYPE;
        }*/

        if (mShowItems.get(position) instanceof HeadType) {
            return HEADTYPE;
        }

        if (mShowItems.get(position) instanceof BodyType) {
            return BODYTYPE;
        }

        if (mShowItems.get(position) instanceof FootType) {
            return FOOTTYPE;
        }

        //有可能走到这里吗?一般不可能走到这里
        return BODYTYPE;
    }

    //把数据自己动刷新去掉多余的头部跟脚部
    public void update() {
        //多的头部干掉
        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof HeadType) {
                mShowItems.remove(i);
            }

        }
        //如果用户传入了头部的布局,那么显示头
        if (mHeadLayout != 0) {
            mShowItems.add(0,new HeadTypeBean());
        }

        for (int i = 0; i < mShowItems.size(); i++) {
            if (mShowItems.get(i) instanceof FootType) {
                mShowItems.remove(i);
            }
        }

        //根据用户的需求自动添加脚部


        //添加脚部
        if (mIsShowFootView) {
        mShowItems.add(new NewFootBean());
        }


        //刷新
        notifyDataSetChanged();
    }

    //接口回调
    public interface ClickItemListener{
        void click(int position, ItemType itemType);
    }

    public ClickItemListener mClickItemListener;

    public void setClickItemListener( ClickItemListener clickItemListener) {
        this.mClickItemListener = clickItemListener;
    }

    //用户传入我们的头部
    public void setHeadLayout(int headLayout) {
        this.mHeadLayout = headLayout;
    }

    //让外部数据更新
    public interface AdapterBindListener{
        void bindHead(FinalViewHolder holder, ItemType itemType);
        void bindBody(FinalViewHolder holder, ItemType itemType);
        void bindFoot(FootViewLayout footViewLayout);
    }
    //更新必须有
    //1. 是否必须的
    //2. 必须的
    //3. 抽象还是构造
    //4. 构造

    public AdapterBindListener mAdapterBindListener;

    //接收是否显示
    public boolean mIsShowFootView = false;

    //传入是否显示脚部
    public void showFootView(boolean isShow) {
        this.mIsShowFootView = isShow;
    }


}
