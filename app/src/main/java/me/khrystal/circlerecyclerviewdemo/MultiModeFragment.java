package me.khrystal.circlerecyclerviewdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.CircularHorizontalMode;
import me.khrystal.library.widget.CircularHorizontalBTTMode;
import me.khrystal.library.widget.CircularViewMode;
import me.khrystal.library.widget.CircularViewRTLMode;
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
public class MultiModeFragment extends Fragment {

    private CircleRecyclerView mCircleRecyclerView;
    private ItemViewMode mItemViewMode;
    private LinearLayoutManager mLayoutManager;
    private List<Integer> mImgList;
    private boolean mIsNotLoop;

    private Integer[] mImgs = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12
    };

    public static MultiModeFragment newInstance(@ModeType.ModeTypeChecker int modeType) {
        MultiModeFragment fragment = new MultiModeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("mode_type", modeType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int modeType = getArguments().getInt("mode_type");
        mCircleRecyclerView = view.findViewById(R.id.circle_rv);

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
            case 6:
                mItemViewMode = new CircularViewMode();
                mLayoutManager = new LinearLayoutManager(getContext());
                mIsNotLoop = true;
                break;
            case 7:
                mItemViewMode = new CircularHorizontalMode();
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case 8:
                mItemViewMode = new CircularViewRTLMode();
                mLayoutManager = new LinearLayoutManager(getContext());
                break;
            case 9:
                // notice when use horizontal layoutManager and item in recyclerView bottom
                // the item xml file is not useful set marginTop and set alginParentBottom,
                // u can use the first argument to control the item position of the recyclerView
                // and this offset need use some density util to resolve adaptation
                // and the offset is relative recyclerView top abs distance
                mItemViewMode = new CircularHorizontalBTTMode(600, false);
                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                break;

        }

        mCircleRecyclerView.setLayoutManager(mLayoutManager);
        mCircleRecyclerView.setViewMode(mItemViewMode);
        mCircleRecyclerView.setNeedCenterForce(true);
        mCircleRecyclerView.setNeedLoop(!mIsNotLoop);

        mCircleRecyclerView.setOnCenterItemClickListener(new CircleRecyclerView.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(View v) {
                Toast.makeText(getContext(), "Center Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        mImgList = Arrays.asList(mImgs);
        Collections.shuffle(mImgList);

        mCircleRecyclerView.setAdapter(new A());

    }

    class A extends RecyclerView.Adapter<VH> {

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            VH h = null;
            if (mCircleRecyclerView.getLayoutManager().canScrollHorizontally()) {
                h = new VH(LayoutInflater.from(getContext())
                        .inflate(R.layout.item_h, parent, false));
            } else if (mCircleRecyclerView.getLayoutManager().canScrollVertically()) {
                if (mItemViewMode instanceof CircularViewMode)
                    h = new VH(LayoutInflater.from(getContext())
                            .inflate(R.layout.item_c_v, parent, false));
                else if (mItemViewMode instanceof CircularViewRTLMode)
                    h = new VH(LayoutInflater.from(getContext())
                            .inflate(R.layout.item_c_rtl_v, parent, false));
                else if (mItemViewMode instanceof CircularHorizontalBTTMode) {
                    h = new VH(LayoutInflater.from(getContext())
                            .inflate(R.layout.item_h_btt, parent, false));
                }
                else
                    h = new VH(LayoutInflater.from(getContext())
                            .inflate(R.layout.item_v, parent, false));
            }

            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
//            Glide.with(getContext())
//                    .load(mImgList.get(position % mImgList.size()))
//                    .bitmapTransform(new CropCircleTransformation(getContext()))
//                    .into(holder.iv);
            Glide.with(getContext())
                    .load(mImgList.get(position % mImgList.size()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.iv);

        }

        @Override
        public int getItemCount() {
            return mIsNotLoop ? mImgList.size() : Integer.MAX_VALUE;
        }
    }


    static class VH extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public VH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_text);
            iv = itemView.findViewById(R.id.item_img);
        }
    }
}
