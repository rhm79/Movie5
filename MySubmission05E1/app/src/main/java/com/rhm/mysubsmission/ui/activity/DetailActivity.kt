package com.rhm.mysubsmission.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.api.MovieItems
import com.rhm.mysubsmission.api.TvShowItems
import com.rhm.mysubsmission.db.MovieDatabase
import com.rhm.mysubsmission.db.MovieTable
import com.rhm.mysubsmission.db.TvShowDatabase
import com.rhm.mysubsmission.db.TvShowTable
import com.rhm.mysubsmission.helper.toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.launch

class DetailActivity : BaseActivity() {
    private lateinit var verifyView: String
    private lateinit var tvTitle: TextView
    private lateinit var tvReleaseDate: TextView
    private lateinit var tvDescription: TextView
    private lateinit var ivPoster: ImageView
    private lateinit var stringPoster: String

    private var fromAdapter: String? = null

    companion object {
        const val EXTRA_ADAPTER = "extra_adapter"
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        tvTitle = findViewById(R.id.tv_title)
        tvReleaseDate = findViewById(R.id.tv_date_release)
        tvDescription = findViewById(R.id.tv_description)
        ivPoster = findViewById(R.id.iv_Poster)

        fromAdapter = intent.getStringExtra(EXTRA_ADAPTER)

        if (fromAdapter == "movie") {

            fromMovie()
        } else if (fromAdapter == "tvShow") {

            fromTvShow()
        } else if (fromAdapter == "movieFav") {

            btn_action.text = getString(R.string.delete)
            fromMovieFav()
        } else if (fromAdapter == "tvShowFav") {

            btn_action.text = getString(R.string.delete)
            fromTvShowFav()
        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(EXTRA_ADAPTER) as String
            verifyView = result
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EXTRA_ADAPTER, verifyView)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarDetail.visibility = View.VISIBLE
        } else {
            progressBarDetail.visibility = View.GONE
        }
    }


    private fun fromMovie() {
        verifyView = fromAdapter!!
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieItems

        showLoading(true)

        tvTitle.text = movie.title
        tvReleaseDate.text = movie.release_date
        tvDescription.text = movie.overview

        stringPoster = "https://image.tmdb.org/t/p/w342" + movie.poster_path
        Glide.with(this)
            .load(stringPoster)
            .into(ivPoster)

        if (tvDescription.text != ".") {
            showLoading(false)
        }

        btn_action.setOnClickListener {
            onClickMovie()
        }
    }

    private fun fromTvShow() {
        verifyView = fromAdapter!!
        val tvShow = intent.getParcelableExtra(EXTRA_TV_SHOW) as TvShowItems

        showLoading(true)

        tvTitle.text = tvShow.name
        tvReleaseDate.text = tvShow.release_date
        tvDescription.text = tvShow.overview

        stringPoster = "https://image.tmdb.org/t/p/w342" + tvShow.poster_path
        Glide.with(this)
            .load(stringPoster)
            .into(ivPoster)

        if (tvDescription.text != ".") {
            showLoading(false)
        }

        btn_action.setOnClickListener {
            onClickTvShow()
        }
    }

    private fun fromMovieFav() {
        btn_action.text = getString(R.string.delete)

        verifyView = fromAdapter!!
        val movie = intent.getParcelableExtra(EXTRA_MOVIE) as MovieTable

        showLoading(true)

        tvTitle.text = movie.title
        tvReleaseDate.text = movie.release_date
        tvDescription.text = movie.overview

        stringPoster = movie.poster_path
        Glide.with(this)
            .load(stringPoster)
            .into(ivPoster)

        if (tvDescription.text != ".") {
            showLoading(false)
        }

        btn_action.setOnClickListener {
            onClickMovie()
        }
    }

    private fun fromTvShowFav() {
        btn_action.text = getString(R.string.delete)

        verifyView = fromAdapter!!
        val tvShow = intent.getParcelableExtra(EXTRA_TV_SHOW) as TvShowTable

        showLoading(true)

        tvTitle.text = tvShow.title
        tvReleaseDate.text = tvShow.release_date
        tvDescription.text = tvShow.overview

        stringPoster = tvShow.poster_path
        Glide.with(this)
            .load(stringPoster)
            .into(ivPoster)

        if (tvDescription.text != ".") {
            showLoading(false)
        }

        btn_action.setOnClickListener {
            onClickTvShow()
        }
    }

    private fun onClickMovie() {
        val title = tvTitle.text.toString().trim()
        val description = tvDescription.text.toString().trim()
        val releaseDate = tvReleaseDate.text.toString().trim()
        val poster = stringPoster.trim()

        launch {
            val movieFav = MovieTable(
                title,
                description,
                poster,
                releaseDate
            )
            baseContext?.let {
                if (btn_action.text == getString(R.string.delete)) {
                    MovieDatabase(it).getMovieDao().getDeleteMovie(title)
                    it.toast(getString(R.string.delete_item))
                    btn_action.text = getString(R.string.save)
                } else {
                    val count = MovieDatabase(it).getMovieDao().getCountMovie(title)
                    if (count >= 1) {
                        it.toast(getString(R.string.already_available))
                        return@let
                    } else {
                        MovieDatabase(it).getMovieDao().addMovie(movieFav)
                        it.toast(getString(R.string.has_been_saved))
                        btn_action.text = getString(R.string.delete)
                    }
                }
            }
        }
    }

    private fun onClickTvShow() {
        val title = tvTitle.text.toString().trim()
        val description = tvDescription.text.toString().trim()
        val releaseDate = tvReleaseDate.text.toString().trim()
        val poster = stringPoster.trim()

        launch {
            val tvShowFav = TvShowTable(
                title,
                description,
                poster,
                releaseDate
            )
            baseContext?.let {
                if (btn_action.text == getString(R.string.delete)) {
                    TvShowDatabase(it).getTvShowDao().getDeleteTvShow(title)
                    it.toast(getString(R.string.delete_item))
                    btn_action.text = getString(R.string.save)
                } else {
                    val count = TvShowDatabase(it).getTvShowDao().getCountTvShow(title)
                    if (count >= 1) {
                        it.toast(getString(R.string.already_available))
                        return@let
                    } else {
                        TvShowDatabase(it).getTvShowDao().addTvShow(tvShowFav)
                        it.toast(getString(R.string.has_been_saved))
                        btn_action.text = getString(R.string.delete)
                    }
                }
            }
        }
    }

}
