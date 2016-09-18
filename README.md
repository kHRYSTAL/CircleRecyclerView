### CircleRecyclerView

the library is a loop RecyclerView, can show some effects when display

### screenshot




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