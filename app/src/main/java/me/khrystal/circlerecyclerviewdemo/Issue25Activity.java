package me.khrystal.circlerecyclerviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.CurrentItemCallback;
import me.khrystal.library.widget.ScaleXViewMode;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 18/7/5
 * update time:
 * email: 723526676@qq.com
 */

public class Issue25Activity extends AppCompatActivity {

    private CircleRecyclerView mCircleRecyclerView;
    private ImageView topIv;

    private List<String> mCountryList;
    private int mCenterPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue12);
        mCircleRecyclerView = (CircleRecyclerView) findViewById(R.id.circle_rv);
        topIv = (ImageView) findViewById(R.id.topIv);

        mCircleRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mCircleRecyclerView.setViewMode(new ScaleXViewMode());
        mCircleRecyclerView.setNeedLoop(false);
        mCountryList = new ArrayList<>();
        mCountryList.add(0, "Mandarian");
        mCountryList.add(1, "Spanish");
        mCountryList.add(2, "English");
        mCountryList.add(3, "Arabic");
        mCountryList.add(4, "Portuguese");
        mCountryList.add(5, "Russian");
        mCountryList.add(6, "Thai    ");
        final A adapter = new A();
        mCircleRecyclerView.setAdapter(adapter);
        mCircleRecyclerView.setNeedCenterForce(true);

        mCircleRecyclerView.setCurrentItemCallback(new CurrentItemCallback() {
            @Override
            public void onItemInCenter(View centerItem) {
                mCenterPosition = (int) centerItem.getTag(R.string.item_position);
                adapter.notifyDataSetChanged();
                mCircleRecyclerView.scrollBy(1, 1);
            }
        });

    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            VH h = null;
            h = new VH(LayoutInflater.from(Issue25Activity.this)
                    .inflate(R.layout.item_h, parent, false));

            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            String countryName = mCountryList.get(position % mCountryList.size());
            holder.tv.setText(countryName);
            holder.iv.setBackgroundResource(R.color.color4);
            holder.itemView.setTag(R.string.item_position, position);
            if (mCenterPosition == position) {
                holder.tv.setTextColor(Color.BLUE);
            } else {
                holder.tv.setTextColor(Color.BLACK);
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
