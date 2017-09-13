package com.diandianguanjia.workspace_dd_3.adapter;

import android.content.Context;
import android.widget.RatingBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.diandianguanjia.workspace_dd_3.R;
import com.diandianguanjia.workspace_dd_3.mode.AroundServerBean;

import java.util.List;

/**
 * Created by Administrator on 2017\6\30 0030.
 */

public class BranchAdapter extends BaseQuickAdapter<AroundServerBean,BaseViewHolder>{
    private Context mCOntext;
    private List<AroundServerBean> modeLists;
    public BranchAdapter(Context mCOntext,List<AroundServerBean> modeLists) {


        super( R.layout.home_listview_items, modeLists);
        this.mCOntext=mCOntext;
        this.modeLists=modeLists;
    }

    @Override
    protected void convert(BaseViewHolder helper, AroundServerBean item) {

    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int positions) {
        super.onBindViewHolder(holder, positions);

        AroundServerBean aroundServerBean = modeLists.get(positions);
        RatingBar ratingBar=holder.getView(R.id.ratingBar1);

        // 设置RatingBar5颗星
        ratingBar.setNumStars(5);
        // 设置RatingBar 评分的步长
        ratingBar.setStepSize(0.5f);

    }
}
