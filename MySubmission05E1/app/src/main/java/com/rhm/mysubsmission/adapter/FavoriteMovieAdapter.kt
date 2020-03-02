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
import com.rhm.mysubsmission.db.MovieTable
import com.rhm.mysubsmission.ui.activity.DetailActivity
import kotlinx.android.synthetic.main.item_movie.view.*

class FavoriteMovieAdapter(val favMovies: List<MovieTable>) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMoviesViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMoviesViewHolder {
        return FavoriteMoviesViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int = favMovies.size

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        holder.bind(favMovies[position])
    }

    inner class FavoriteMoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: MovieTable) {
            Glide.with(view)
                .load(movie.poster_path)
                .transform(CenterCrop())
                .into(view.img_item_photo)

            view.tv_item_title.text = movie.title
            view.tv_item_date.text = movie.release_date
            view.tv_item_description.text = movie.overview

            view.setOnClickListener {
                Toast.makeText(view.context, " ${movie.title}", Toast.LENGTH_LONG).show()

                val detailIntent = Intent(view.context, DetailActivity::class.java)

                val listFavoriteMovie = MovieTable(
                    movie.title,
                    movie.overview,
                    movie.poster_path,
                    movie.release_date
                )
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, listFavoriteMovie)
                detailIntent.putExtra(DetailActivity.EXTRA_ADAPTER, "movieFav")

                view.context.startActivity(detailIntent)
            }

        }
    }

}