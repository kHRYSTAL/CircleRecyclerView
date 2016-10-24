### CircleRecyclerView

the library is a loop RecyclerView, can show some effects when display

### screenshot

CircularViewMode

![CircularViewMode](https://github.com/kHRYSTAL/CircleRecyclerView/blob/master/screenshot/screenshot1.gif)

ScaleXViewMode & ScaleYViewMode

![ScaleXYViewMode](https://github.com/kHRYSTAL/CircleRecyclerView/blob/master/screenshot/screenshot2.gif)

RotateXScaleYViewMode & RotateYScaleXViewMode

![RotateXYScaleXYViewMode](https://github.com/kHRYSTAL/CircleRecyclerView/blob/master/screenshot/screenshot3.gif)

NoLoop but CenterForce (setClipPadding(l,t,r,b); setClipChildren(false);)

![](http://ww2.sinaimg.cn/large/72f96cbajw1f7yqcwf0cyg20cz0l9n54.gif)

CircularHorizontalMode

![CircularHorizontalMode](https://github.com/kHRYSTAL/CircleRecyclerView/blob/master/screenshot/screenshot4.gif)

### usage

```

mCircleRecyclerView = (CircleRecyclerView) view.findViewById(R.id.circle_rv);
mCircleRecyclerView.setLayoutManager(mLayoutManager);
mCircleRecyclerView.setViewMode(mItemViewMode); // T implements ItemViewMode, after setLayoutManager(manager)
mCircleRecyclerView.setNeedCenterForce(true); // when SCROLL_STATE_IDLE == state, nearly center itemview scroll to center

mCircleRecyclerView.setNeedLoop(true); // default is true

// if setCenterForce(true), can set this callback
mCircleRecyclerView.setOnCenterItemClickListener(new CircleRecyclerView.OnCenterItemClickListener() {
    @Override
    public void onCenterItemClick(View v) {
        Toast.makeText(getContext(), "Center Clicked", Toast.LENGTH_SHORT).show();
    }
});

```

and if loop is true, the RecyclerView.Adapter<VH> need like this:
 
```
@Override
public void onBindViewHolder(VH holder, int position) {
    positionData = mDataList.get(position % mDataList.size());
}

@Override
public int getItemCount() {
    return Integer.MAX_VALUE;
}
 
``` 