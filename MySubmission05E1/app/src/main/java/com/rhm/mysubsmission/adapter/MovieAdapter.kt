package com.rhm.mysubsmission.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.api.MovieItems
import com.rhm.mysubsmission.ui.activity.DetailActivity

class MovieAdapter(
    private var movies: List<MovieItems>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.item_movie, viewGroup, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    fun updateMovies(movies: List<MovieItems>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: ImageView = itemView.findViewById(R.id.img_item_photo)
        private val title: TextView = itemView.findViewById(R.id.tv_item_title)
        private val release_date: TextView = itemView.findViewById(R.id.tv_item_date)
        private val description: TextView = itemView.findViewById(R.id.tv_item_description)

        fun bind(movie: MovieItems) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.poster_path}")
                .transform(CenterCrop())
                .into(poster)

            title.text = movie.title
            release_date.text = movie.release_date
            description.text = movie.overview

            itemView.setOnClickListener {
                Toast.makeText(itemView.context, " ${movie.title}", Toast.LENGTH_SHORT).show()

                val detailIntent = Intent(itemView.context, DetailActivity::class.java)

                val listMovie = MovieItems(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.poster_path,
                    movie.backdrop_path,
                    movie.rating,
                    movie.release_date
                )
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, listMovie)
                detailIntent.putExtra(DetailActivity.EXTRA_ADAPTER, "movie")

                itemView.context.startActivity(detailIntent)
            }
        }
    }

    fun filterList(filteredTitle: ArrayList<MovieItems>) {
        this.movies = filteredTitle
        notifyDataSetChanged()
    }

}