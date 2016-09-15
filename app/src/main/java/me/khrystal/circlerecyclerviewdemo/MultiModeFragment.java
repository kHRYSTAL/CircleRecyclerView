package me.khrystal.circlerecyclerviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.CircularViewMode;
import me.khrystal.library.widget.ItemViewMode;
import me.khrystal.library.widget.RotateXScaleYViewMode;
import me.khrystal.library.widget.RotateYScaleXViewMode;
import me.khrystal.library.widget.ScaleXViewMode;
import me.khrystal.library.widget.ScaleYViewMode;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/15
 * update time:
 * email: 723526676@qq.com
 */
public class MultiModeFragment extends Fragment{

    private CircleRecyclerView mCircleRecyclerView;
    private ItemViewMode mItemViewMode;
    private LinearLayoutManager mLayoutManager;
    private List<Integer> mData = new ArrayList<>();

    public static MultiModeFragment newInstance(@ModeType.ModeTypeChecker int modeType){
        MultiModeFragment fragment = new MultiModeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode_type", modeType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int modeType = getArguments().getInt("mode_type");
        mCircleRecyclerView = (CircleRecyclerView) view.findViewById(R.id.circle_rv);

//        find itemViewMode and layoutManager
        switch (modeType) {
            case 1:
                mItemViewMode = new CircularViewMode();
                mLayoutManager = new LinearLayoutManager(getContext());
                break;
            case 2:
                mItemViewMode = new ScaleXViewMode();
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case 3:
                mItemViewMode = new ScaleYViewMode();
                mLayoutManager = new LinearLayoutManager(getContext());
                break;
            case 4:
                mItemViewMode = new RotateXScaleYViewMode();
                mLayoutManager = new LinearLayoutManager(getContext());
                break;
            case 5:
                mItemViewMode = new RotateYScaleXViewMode();
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
        }

        mCircleRecyclerView.setLayoutManager(mLayoutManager);
        mCircleRecyclerView.setViewMode(mItemViewMode);
        mCircleRecyclerView.setNeedCenterForce(true);

//        init list
        for (int i = 0; i < 100; i++) {
            mData.add(i);
        }

        mCircleRecyclerView.setAdapter(new A());

    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH h = null;
            if (mCircleRecyclerView.getLayoutManager().canScrollHorizontally()) {
                    h = new VH(LayoutInflater.from(getContext())
                        .inflate(R.layout.item_h, parent, false));
            } else if (mCircleRecyclerView.getLayoutManager().canScrollVertically()) {
                if (mItemViewMode instanceof CircularViewMode)
                    h = new VH(LayoutInflater.from(getContext())
                            .inflate(R.layout.item_c_v, parent, false));
                else
                    h = new VH(LayoutInflater.from(getContext())
                        .inflate(R.layout.item_v, parent, false));
            }

            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + mData.get(position % mData.size()));
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
        }
    }


    class VH extends RecyclerView.ViewHolder {

        TextView tv;

        public VH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
