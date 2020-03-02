package com.rhm.mysubsmission.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.adapter.FavoriteTvShowAdapter
import com.rhm.mysubsmission.db.TvShowDatabase
import kotlinx.android.synthetic.main.activity_favorite_tv_show.*
import kotlinx.coroutines.launch

class FavoriteTvShowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_tv_show)

        rv_favorite_tv_show.layoutManager = LinearLayoutManager(this)

        launch {
            baseContext?.let {
                val tvShows = TvShowDatabase(it).getTvShowDao().getAllTvShow()
                rv_favorite_tv_show.adapter = FavoriteTvShowAdapter(tvShows)
            }
        }
    }
}
