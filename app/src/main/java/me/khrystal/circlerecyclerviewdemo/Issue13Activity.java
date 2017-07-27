package me.khrystal.circlerecyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.ScaleYViewMode;

/**
 * usage: RealLoop recyclerView, but efficiency not higher 2^30
 * author: kHRYSTAL
 * create time: 17/7/27
 * update time:
 * email: 723526676@qq.com
 */

public class Issue13Activity extends AppCompatActivity {

    private CircleRecyclerView mCircleRecyclerView;
    private List<Integer> mImgList;
    int firstRow, lastRow, totalRow = 6;


    private Integer[] mImgs = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12
    };
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue13);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        layoutManager = new LinearLayoutManager(Issue13Activity.this);
        mCircleRecyclerView.setLayoutManager(layoutManager);
        mCircleRecyclerView.setViewMode(new ScaleYViewMode());
        mCircleRecyclerView.setNeedLoop(false);  // set no fake loop, it's mean not use MAX_VALUE >> 1


        //region config real loop
        mImgList = new ArrayList<>();
        firstRow = mImgs.length - 1;
        while (firstRow < 0) {
            firstRow += mImgs.length;
        }

        for (int i = 0; i < totalRow; i++) {
            mImgList.add(mImgs[firstRow]);
            firstRow++;
            if (firstRow >= mImgs.length)
                firstRow = 0;
        }

        firstRow -= totalRow;
        while (firstRow < 0) {
            firstRow += mImgs.length;
        }

        lastRow = firstRow + totalRow - 1;
        while (lastRow >= mImgs.length) {
            lastRow -= mImgs.length;
        }


        mCircleRecyclerView.smoothScrollToPosition(totalRow / 2);
        mCircleRecyclerView.setOnScrollListener(new CircleRecyclerView.OnScrollListener() {
            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                if (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()
                        >= layoutManager.getItemCount()) {
                    mImgList = new ArrayList<Integer>();
                    lastRow -= totalRow / 2;
                    while (lastRow < 0) {
                        lastRow += mImgs.length;
                    }
                    for (int i = 0; i < totalRow; i++) {
                        mImgList.add(mImgs[lastRow]);
                        lastRow++;
                        if (lastRow >= mImgs.length) {
                            lastRow = 0;
                        }
                    }
                    firstRow = lastRow - totalRow;
                    while (firstRow < 0) {
                        firstRow += mImgs.length;
                    }
                    lastRow--;
                    while (lastRow < 0) {
                        lastRow += mImgs.length;
                    }

                    // TODO: 17/7/27
                    mCircleRecyclerView.smoothScrollToView(mCircleRecyclerView.findViewAtCenter());

                }

                if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                    mImgList = new ArrayList<Integer>();
                    firstRow -= totalRow / 2;
                    while (firstRow < 0) {
                        firstRow += mImgs.length;
                    }
                    for (int i = 0; i < totalRow; i++) {
                        mImgList.add(mImgs[firstRow]);
                        firstRow++;
                        if (firstRow >= mImgs.length) {
                            firstRow = 0;
                        }
                    }
                    firstRow -= totalRow;
                    while (firstRow < 0) {
                        firstRow += mImgs.length;
                    }
                    lastRow = firstRow + totalRow - 1;
                    while (lastRow >= mImgs.length) {
                        lastRow -= mImgs.length;
                    }
                    mCircleRecyclerView.setAdapter(new A());
                    mCircleRecyclerView.smoothScrollToPosition((totalRow / 2) + 1);
                }

            }

            @Override
            public void onScrollStateChanged(int state) {

            }

            @Override
            public void onScrolled(int dx, int dy) {

            }
        });


        //endregion

    }

    class A extends RecyclerView.Adapter<Issue13Activity.VH> {

        @Override
        public Issue13Activity.VH onCreateViewHolder(ViewGroup parent, int viewType) {
            Issue13Activity.VH h = null;
            h = new Issue13Activity.VH(LayoutInflater.from(Issue13Activity.this)
                    .inflate(R.layout.item_v, parent, false));

            return h;
        }

        @Override
        public void onBindViewHolder(Issue13Activity.VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            Glide.with(Issue13Activity.this)
                    .load(mImgList.get(position % mImgList.size()))
                    .bitmapTransform(new CropCircleTransformation(Issue13Activity.this))
                    .into(holder.iv);

        }

        @Override
        public int getItemCount() {
            return mImgList.size();
        }
    }


    class VH extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public VH(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_text);
            iv = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }


}

