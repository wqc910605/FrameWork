package com.wwf.fw.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 作者: wwf
 * 描述:万能的viewholder
 *
 **/
//正名

public class FinalViewHolder extends RecyclerView.ViewHolder {
    public FinalViewHolder(View itemView) {
        super(itemView);
    }

    //private HashMap<Integer, View> mViews = new HashMap<>();

    //这是android中性能比较高的hashmap但是key一定是integer类型
    private SparseArray<View> mViews = new SparseArray<>();

    //根据id返回对应的view
    public View getViewById(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViews.put(id, view);
        }

        //到这里一定有view
        return view;

    }
}
