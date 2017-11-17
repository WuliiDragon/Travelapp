//package wlxy.com.travelapp;
//
//import android.support.v4.view.GestureDetectorCompat;
//import android.support.v7.widget.RecyclerView;
//import android.view.GestureDetector;
//import android.view.MotionEvent;
//import android.view.View;
//
///**
// * Created by guardian on 2017/10/17.
// */
//
//public abstract class OnItemClickListener implements RecyclerView.OnItemTouchListener {
////gesturerdetectorcompat手势识别兼容
//    private GestureDetectorCompat mGestureDetector;
//    private RecyclerView mrecyclerView;
//
//
//    public OnItemClickListener(RecyclerView view) {
//        this.mrecyclerView = view;
//        mGestureDetector = new GestureDetectorCompat(mrecyclerView.getContext(), new ItemTouchHelperGestureListener());
//    }
//
//
//    @Override
//    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//        mGestureDetector.onTouchEvent(e);
//        return false;
//    }
//
//    @Override
//    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        mGestureDetector.onTouchEvent(e);
//    }
//
//    @Override
//    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//    }
//    public abstract void onItemClick(RecyclerView.ViewHolder holder, int position);
//    class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            View child = mrecyclerView.findChildViewUnder(e.getX(), e.getY());
//            if (child != null) {
//                int position = mrecyclerView.indexOfChild(child);
//                onItemClick(mrecyclerView.getChildViewHolder(child), position);
//            }
//            return true;
//        }
//    }
//}