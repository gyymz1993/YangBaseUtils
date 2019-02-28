//package com.utils.gyymz.base.rvAdapter;
//
//import android.view.View;
//
//public class RvListener<T extends BaseQuickAdapter> {
//
//    /**
//     * Interface definition for a callback to be invoked when an itemchild in this
//     * view has been clicked
//     */
//    public interface OnItemChildClickListener {
//        /**
//         * callback method to be invoked when an item in this view has been
//         * click and held
//         *
//         * @param view     The view whihin the ItemView that was clicked
//         * @param position The position of the view int the adapter
//         */
//        void onItemChildClick(BaseQuickAdapter adapter, View view, int position);
//    }
//
//
//    /**
//     * Interface definition for a callback to be invoked when an childView in this
//     * view has been clicked and held.
//     */
//    public interface OnItemChildLongClickListener {
//        /**
//         * callback method to be invoked when an item in this view has been
//         * click and held
//         *
//         * @param view     The childView whihin the itemView that was clicked and held.
//         * @param position The position of the view int the adapter
//         * @return true if the callback consumed the long click ,false otherwise
//         */
//        boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position);
//    }
//
//    /**
//     * Interface definition for a callback to be invoked when an item in this
//     * view has been clicked and held.
//     */
//    public interface OnItemLongClickListener {
//        /**
//         * callback method to be invoked when an item in this view has been
//         * click and held
//         *
//         * @param adapter  the adpater
//         * @param view     The view whihin the RecyclerView that was clicked and held.
//         * @param position The position of the view int the adapter
//         * @return true if the callback consumed the long click ,false otherwise
//         */
//        boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position);
//    }
//
//
//    /**
//     * Interface definition for a callback to be invoked when an item in this
//     * RecyclerView itemView has been clicked.
//     */
//    public interface OnItemClickListener {
//
//        /**
//         * Callback method to be invoked when an item in this RecyclerView has
//         * been clicked.
//         *
//         * @param adapter  the adpater
//         * @param view     The itemView within the RecyclerView that was clicked (this
//         *                 will be a view provided by the adapter)
//         * @param position The position of the view in the adapter.
//         */
//        void onItemClick(BaseQuickAdapter t, View view, int position);
//    }
//
//
//    public interface RequestLoadMoreListener {
//
//        void onLoadMoreRequested();
//
//    }
//
//}
