package com.rhm.mysubsmission.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.db.MovieDatabase
import com.rhm.mysubsmission.db.MovieTable

internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    //private val context: Context? = null
    private val widgetId = 0
    //private val favorites: List<Favorite> = java.util.ArrayList<Favorite>()
    private val mWidgetItems = ArrayList<Bitmap>()
    private val mmWidgetItems = ArrayList<MovieTable>()
    //private val mmWidgetItems:MutableList<MovieTable> = ArrayList()


    override fun onCreate() {

    }

    override fun onDataSetChanged() {
        /*
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.zdarth_vader))
        //mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.zfalcon))
        //mWidgetItems.add(BitmapFactory.decodeResource(mContext.resources, R.drawable.zstarwars))
        mWidgetItems.add(
            BitmapFactory.decodeResource(
                mContext.resources,
                R.drawable.zstorm_trooper
            )
        )
         */

        mmWidgetItems = MovieDatabase(mContext).getMovieDao().getFavMovie()
        //mmWidgetItems = MovieDatabase.invoke(mContext).getMovieDao().getAllMovie()

    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        /*
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        rv.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extras = bundleOf(
            FavoriteMovieWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
        */

        //
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        if (!mmWidgetItems.isEmpty()) {
            val favorite= mmWidgetItems[position]
            try {
                val imageView: Bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w342/" + favorite.poster_path)
                    .centerCrop()
                    .submit()
                    .get()
                rv.setImageViewBitmap(R.id.imageView, imageView)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val extras = bundleOf(
            FavoriteMovieWidget.EXTRA_ITEM to position
        )

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv
        //
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(i: Int): Long = 0

    override fun hasStableIds(): Boolean = false

}