package me.khrystal.library.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;

import me.khrystal.library.R;


/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/14
 * update time:
 * email: 723526676@qq.com
 */
public class CircleRecyclerView extends RecyclerView implements View.OnClickListener {

    private static final String TAG = "CircleRecyclerView";

    private static final int DEFAULT_SELECTION = Integer.MAX_VALUE >> 1;

    private boolean mIsForceCentering;
    private final CenterRunnable mCenterRunnable = new CenterRunnable();
    private ItemViewMode mViewMode;
    private boolean mNeedCenterForce;
    private boolean mNeedLoop = true;
    private OnCenterItemClickListener mCenterItemClickListener;
    private View mCurrentCenterChildView;
    private OnScrollListener mOnScrollListener;
    private boolean mFirstOnLayout;
    private boolean mFirstSetAdapter = true;

    private Handler mPostHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            scrollToPosition(DEFAULT_SELECTION);
        }
    };

    public CircleRecyclerView(Context context) {
        this(context, null);
    }

    public CircleRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (mNeedLoop) {
//            scrollToPosition Notwork use delay
            if (!mFirstOnLayout) {
                mFirstOnLayout = true;
                mPostHandler.sendEmptyMessage(0);
            }
//            scrollToPosition(DEFAULT_SELECTION);
            mCurrentCenterChildView = findViewAtCenter();
            smoothScrollToView(mCurrentCenterChildView);
        } else if (!mNeedLoop && mNeedCenterForce) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
            if (layoutManager.canScrollHorizontally())
                setPadding(getWidth() / 2, 0, getWidth() / 2, 0);
            else if (layoutManager.canScrollVertically())
                setPadding(0, getHeight() / 2, 0, getHeight() / 2);
            setClipToPadding(false);
            setClipChildren(false);
            mCurrentCenterChildView = findViewAtCenter();
            smoothScrollToView(mCurrentCenterChildView);
        } else {
            setClipToPadding(false);
            setClipChildren(false);
        }

        if (mCurrentCenterChildView != null)
            mCurrentCenterChildView.setOnClickListener(this);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mViewMode != null) {
            final int count = getChildCount();
            for (int i = 0; i < count; ++i) {
                View v = getChildAt(i);
                if (v != mCurrentCenterChildView && mCenterItemClickListener != null)
                    v.setOnClickListener(null);
                if (v == mCurrentCenterChildView)
                    v.setTag(R.string.tag_is_center, true);
                else
                    v.setTag(R.string.tag_is_center, false);

                mViewMode.applyToView(v, this);
            }
        }

        if (mOnScrollListener != null)
            mOnScrollListener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();

        if (mViewMode != null && getLayoutManager() != null) {
            int count = getLayoutManager().getChildCount();
            for (int i = 0; i < count; ++i) {
                View v = getChildAt(i);
                if (v != mCurrentCenterChildView && mCenterItemClickListener != null)
                    v.setOnClickListener(null);
                if (v == mCurrentCenterChildView)
                    v.setTag(R.string.tag_is_center, true);
                else
                    v.setTag(R.string.tag_is_center, false);
                mViewMode.applyToView(v, this);
            }
        }

    }

    public void smoothScrollToView(View v) {
        int distance = 0;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            if (getLayoutManager().canScrollVertically()) {
                final float y = v.getY() + v.getHeight() * 0.5f;
                final float halfHeight = getHeight() * 0.5f;
                distance = (int) (y - halfHeight);
            } else if (getLayoutManager().canScrollHorizontally()) {
                final float x = v.getX() + v.getWidth() * 0.5f;
                final float halfWidth = getWidth() * 0.5f;
                distance = (int) (x - halfWidth);
            }

        } else
            throw new IllegalArgumentException("CircleRecyclerView just support T extend LinearLayoutManager!");
        smoothScrollBy(distance,distance);
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (mOnScrollListener != null)
            mOnScrollListener.onScrolled(dx, dy);
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state == SCROLL_STATE_IDLE) {
            if (mNeedCenterForce && !mIsForceCentering) {
                mIsForceCentering = true;
                mCurrentCenterChildView = findViewAtCenter();
                if (mCurrentCenterChildView != null && mCenterItemClickListener != null)
                    mCurrentCenterChildView.setOnClickListener(this);
                mCenterRunnable.setView(mCurrentCenterChildView);
                ViewCompat.postOnAnimation(this, mCenterRunnable);
            }
        }

        if (mOnScrollListener != null)
            mOnScrollListener.onScrollStateChanged(state);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        removeCallbacks(mCenterRunnable);
        mIsForceCentering = false;
        return super.onTouchEvent(e);
    }

    public View findViewAt(int x, int y) {
        final int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            final View v = getChildAt(i);
            final int x0 = v.getLeft();
            final int y0 = v.getTop();
            final int x1 = v.getWidth() + x0;
            final int y1 = v.getHeight() + y0;
            if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
                return v;
            }
        }
        return null;
    }

    public View findViewAtCenter() {
        if (getLayoutManager().canScrollVertically()) {
            return findViewAt(0, getHeight() / 2);
        }else if (getLayoutManager().canScrollHorizontally()) {
            return findViewAt(getWidth() / 2, 0);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        if (mCenterItemClickListener != null)
            mCenterItemClickListener.onCenterItemClick(v);
    }

    public class CenterRunnable implements Runnable {

        private WeakReference<View> mView;

        public void setView(View v) {
            mView = new WeakReference<View>(v);
        }

        @Override
        public void run() {
            smoothScrollToView(mView.get());
            if (mNeedCenterForce)
                mIsForceCentering = true;
        }
    }

    public void setNeedCenterForce(boolean needCenterForce) {
        mNeedCenterForce = needCenterForce;
    }

    /**
     * default needLoop is true
     * if not needLoop && centerForce
     * will setPadding your layoutManger direction half width or height
     * and setClipPadding(false), setClipChildren(false)
     * @param needLoop default true
     */
    public void setNeedLoop(boolean needLoop) {
        mNeedLoop = needLoop;
    }

    /**
     * set the center item clickListener
     *
     * @param listener
     */
    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        mCenterItemClickListener = listener;
    }

    public void setViewMode(ItemViewMode mode) {
        mViewMode = mode;
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    public interface OnCenterItemClickListener {
        void onCenterItemClick(View v);
    }

    public interface OnScrollListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
        void onScrollStateChanged(int state);
        void onScrolled(int dx, int dy);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (mFirstSetAdapter) {
            mFirstSetAdapter = false;
        } else {
            if (adapter != null && mNeedCenterForce)
                mPostHandler.sendEmptyMessage(0);
        }
    }
}
