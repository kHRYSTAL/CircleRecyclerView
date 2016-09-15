package me.khrystal.circlerecyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.khrystal.library.widget.CircleRecyclerView;
import me.khrystal.library.widget.RotateXScaleYViewMode;
import me.khrystal.library.widget.RotateYScaleXViewMode;

public class MainActivity extends AppCompatActivity {

    private List<Integer> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CircleRecyclerView c = (CircleRecyclerView) findViewById(R.id.circle_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        c.setLayoutManager(layoutManager);
        c.setViewMode(new RotateYScaleXViewMode());
        c.setNeedCenterForce(true);
        for (int i = 0; i < 100; i++) {
            mData.add(i);
        }

        c.setAdapter(new A());

    }

    class A extends RecyclerView.Adapter<VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {

            VH h = new VH(LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.item, parent, false));
            return h;
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            holder.tv.setText("Num:" + mData.get(position % mData.size()));
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
