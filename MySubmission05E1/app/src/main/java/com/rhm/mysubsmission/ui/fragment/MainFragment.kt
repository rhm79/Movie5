package com.rhm.mysubsmission.ui.fragment


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhm.mysubsmission.R
import com.rhm.mysubsmission.adapter.MovieAdapter
import com.rhm.mysubsmission.adapter.TvShowAdapter
import com.rhm.mysubsmission.api.MovieItems
import com.rhm.mysubsmission.api.TvShowItems
import com.rhm.mysubsmission.repo.MovieRepository
import com.rhm.mysubsmission.repo.TvShowRepository
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    private lateinit var discoverMoviesAdapter: MovieAdapter
    private lateinit var discoverTvShowsAdapter: TvShowAdapter

    private var movies: ArrayList<MovieItems> = arrayListOf()
    private var tvShows: ArrayList<TvShowItems> = arrayListOf()
    private lateinit var verifyView: String

    companion object {
        private const val STATE_RESULT = "state_result"

        private val ARG_SECTION_NUMBER = "section_number"

        fun newInstance(index: Int): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt(ARG_SECTION_NUMBER, index)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var index = 1
        if (arguments != null) {
            index = arguments?.getInt(ARG_SECTION_NUMBER, 0) as Int
        }

        rv_movie_tvShow.layoutManager = LinearLayoutManager(this.activity)

        val verify = "$index"
        if (verify == "1" && savedInstanceState == null) {
            verifyView = verify

            discoverMoviesAdapter = MovieAdapter(listOf())
            rv_movie_tvShow.adapter = discoverMoviesAdapter

            showLoading(true)

            MovieRepository.getDiscoverMovies(
                onSuccess = ::onDiscoverMoviesFetched,
                onError = ::onError
            )

            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable) {
                    MovieRepository.getSearchMovies(
                        search = editable.toString(),
                        onSuccess = ::onDiscoverMoviesFetched,
                        onError = ::onError
                    )
                    filterMovie(editable.toString())
                }
            })

        } else if (verify == "2" && savedInstanceState == null) {
            verifyView = verify

            discoverTvShowsAdapter = TvShowAdapter(listOf())
            rv_movie_tvShow.adapter = discoverTvShowsAdapter

            showLoading(true)

            TvShowRepository.getDiscoverTvShows(
                onSuccess = ::onDiscoverTvShowsFetched,
                onError = ::onError
            )

            editTextSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable) {
                    TvShowRepository.getSearchTvShows(
                        search = editable.toString(),
                        onSuccess = ::onDiscoverTvShowsFetched,
                        onError = ::onError
                    )
                    filterTvShow(editable.toString())
                }
            })

        }

        if (savedInstanceState != null) {
            val result = savedInstanceState.getString(STATE_RESULT) as String
            verifyView = result

            if (verifyView == "1") {
                movies =
                    savedInstanceState.getParcelableArrayList<MovieItems>("movies") ?: arrayListOf()
                discoverMoviesAdapter = MovieAdapter(movies)
                rv_movie_tvShow.adapter = discoverMoviesAdapter

            } else {
                tvShows = savedInstanceState.getParcelableArrayList<TvShowItems>("tvShows")
                    ?: arrayListOf()
                discoverTvShowsAdapter = TvShowAdapter(tvShows)
                rv_movie_tvShow.adapter = discoverTvShowsAdapter
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_RESULT, verifyView)
        outState.putParcelableArrayList("movies", movies)
        outState.putParcelableArrayList("tvShows", tvShows)
        super.onSaveInstanceState(outState)
    }

    private fun onDiscoverMoviesFetched(movies: List<MovieItems>) {
        this.movies.addAll(movies)
        discoverMoviesAdapter.updateMovies(movies)
        showLoading(false)
    }

    private fun onDiscoverTvShowsFetched(tvShows: List<TvShowItems>) {
        this.tvShows.addAll(tvShows)
        discoverTvShowsAdapter.updateTvShows(tvShows)
        showLoading(false)
    }

    private fun onError() {
        Toast.makeText(this.activity, getString(R.string.error_fetch), Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun filterMovie(text: String) {
        val filteredTitle = ArrayList<MovieItems>()
        movies.filterTo(filteredTitle) {
            it.title.toLowerCase().contains(text.toLowerCase())
        }
        discoverMoviesAdapter.filterList(filteredTitle)
    }


    private fun filterTvShow(text: String) {
        val filteredTitle = ArrayList<TvShowItems>()
        tvShows.filterTo(filteredTitle) {
            it.name.toLowerCase().contains(text.toLowerCase())
        }

        discoverTvShowsAdapter.filterList(filteredTitle)

    }

}
