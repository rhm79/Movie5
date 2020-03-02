package com.rhm.mysubsmission.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.adapter.FavoriteMovieAdapter
import com.rhm.mysubsmission.db.MovieDatabase
import kotlinx.android.synthetic.main.activity_favorite_movie.*
import kotlinx.coroutines.launch

class FavoriteMovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_movie)

        rv_favorite_movie.layoutManager = LinearLayoutManager(this)

        launch {
            baseContext?.let {
                val movies = MovieDatabase(it).getMovieDao().getAllMovie()
                rv_favorite_movie.adapter = FavoriteMovieAdapter(movies)
            }
        }
    }
}
