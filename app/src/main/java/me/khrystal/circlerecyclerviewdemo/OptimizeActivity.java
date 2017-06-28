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
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.CircularViewMode;
import me.khrystal.library.widget.CircularViewRTLMode;
import me.khrystal.library.widget.ScaleXViewMode;

public class OptimizeActivity extends AppCompatActivity {

    private CircleRecyclerView mCircleRecyclerView;
    private List<Integer> mImgList;

    private Integer[] mImgs = {
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4,
            R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8,
            R.drawable.img_9, R.drawable.img_10, R.drawable.img_11, R.drawable.img_12
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCircleRecyclerView.setViewMode(new ScaleXViewMode());
        mCircleRecyclerView.setNeedCenterForce(true);
        mCircleRecyclerView.setNeedLoop(true);
        mCircleRecyclerView.setOptimizeLoadImg(true);

        mCircleRecyclerView.setOnCenterItemClickListener(new CircleRecyclerView.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClick(View v) {
                Toast.makeText(OptimizeActivity.this, "Center Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        mImgList = Arrays.asList(mImgs);
        Collections.shuffle(mImgList);

        mCircleRecyclerView.setAdapter(new A());
    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH h = null;
            if (mCircleRecyclerView.getLayoutManager().canScrollHorizontally()) {
                h = new VH(LayoutInflater.from(OptimizeActivity.this)
                        .inflate(R.layout.item_h, parent, false));
            }
            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Number :" + (position % mImgList.size()));
            if (mCircleRecyclerView.isLoadAccess()) {
                Glide.with(OptimizeActivity.this)
                        .load(mImgList.get(position % mImgList.size()))
                        .bitmapTransform(new CropCircleTransformation(OptimizeActivity.this))
                        .into(holder.iv);
            }
        }

        @Override
        public int getItemCount() {
            return Integer.MAX_VALUE;
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
