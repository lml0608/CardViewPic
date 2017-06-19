package com.example.android.cardviewpic;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        albumList = new ArrayList<>();


        adapter = new AlbumsAdapter(this, albumList);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Log.i(TAG, String.valueOf(dpToPx(10)));//35
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);

        try {
            Glide.with(this).load(R.drawable.cover).into(backdrop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar() {

        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {

                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }

            }
        });
    }


    private void prepareAlbums() {

        int[] covers = new int[]{

                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        albumList.add(new Album("True Romance", 13, covers[0]));
        albumList.add(new Album("Xscpae", 8, covers[1]));
        albumList.add(new Album("Maroon 5", 11, covers[2]));
        albumList.add(new Album("Born to Die", 12, covers[3]));
        albumList.add(new Album("Honeymoon", 14, covers[4]));
        albumList.add(new Album("I Need a Doctor", 1, covers[5]));
        albumList.add(new Album("Loud", 11, covers[6]));
        albumList.add(new Album("Legend", 14, covers[7]));
        albumList.add(new Album("Hello", 11, covers[8]));
        albumList.add(new Album("Greatest Hits", 17, covers[9]));

        adapter.notifyDataSetChanged();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {

            this.spanCount = spanCount;//2
            this.spacing = spacing;//35
            this.includeEdge = includeEdge;//true

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            int position = parent.getChildAdapterPosition(view);//item position

            Log.i(TAG, String.valueOf(position));
            int column = position % spanCount; //列 column
            Log.i(TAG, String.valueOf(column));

            if (includeEdge) {
                // 35 - 0 * 35 / 2 = 35
                //35  - 1 * 35 / 2 =17.5
                //第0列 的左边距 = 35 右边距 = 17.5
                //第一列 的左边距 = 17.5 右边距 = 35
                outRect.left = spacing - column * spacing / spanCount;

                // 1 * 35 / 2 = 17.5
                // 2 * 35 / 2 = 35
                outRect.right = (column + 1) * spacing / spanCount;


                if (position < spanCount) {

                    //第一行的上边距 = 35
                    outRect.top = spacing;
                }
                //下边距 = 35
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        //return dp * r.getDisplayMetrics().density
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
