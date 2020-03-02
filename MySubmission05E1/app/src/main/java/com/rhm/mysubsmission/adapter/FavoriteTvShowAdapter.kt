package com.rhm.mysubsmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.db.TvShowTable
import com.rhm.mysubsmission.ui.activity.DetailActivity
import kotlinx.android.synthetic.main.item_tv_show.view.*

class FavoriteTvShowAdapter(val favTvShows: List<TvShowTable>) :
    RecyclerView.Adapter<FavoriteTvShowAdapter.FavoriteTvShowsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteTvShowsViewHolder {
        return FavoriteTvShowsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_tv_show, parent, false)
        )
    }

    override fun getItemCount(): Int = favTvShows.size

    override fun onBindViewHolder(holder: FavoriteTvShowsViewHolder, position: Int) {
        holder.bind(favTvShows[position])
    }

    inner class FavoriteTvShowsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(tvShow: TvShowTable) {
            Glide.with(view)
                .load(tvShow.poster_path)
                .transform(CenterCrop())
                .into(view.img_item_photo)

            view.tv_item_title.text = tvShow.title
            view.tv_item_date.text = tvShow.release_date
            view.tv_item_description.text = tvShow.overview

            view.setOnClickListener {
                Toast.makeText(view.context, " ${tvShow.title}", Toast.LENGTH_LONG).show()

                val detailIntent = Intent(view.context, DetailActivity::class.java)

                val listFavoriteTvShow = TvShowTable(
                    tvShow.title,
                    tvShow.overview,
                    tvShow.poster_path,
                    tvShow.release_date
                )
                detailIntent.putExtra(DetailActivity.EXTRA_TV_SHOW, listFavoriteTvShow)
                detailIntent.putExtra(DetailActivity.EXTRA_ADAPTER, "tvShowFav")

                view.context.startActivity(detailIntent)
            }

        }
    }
}