package pe.jsandoval.randomusers.presentation.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pe.jsandoval.randomusers.R
import pe.jsandoval.randomusers.data.local.sp.AppPreferences
import pe.jsandoval.randomusers.inject.Injector
import pe.jsandoval.randomusers.inject.ViewModelFactory
import pe.jsandoval.randomusers.presentation.adapter.UserAdapter
import pe.jsandoval.randomusers.presentation.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity(), UserAdapter.Companion.Listener {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MainViewModel

    private val adapter by lazy { UserAdapter(this) }

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setupViews()
        setUpObservers()

        viewModel.fetch(AppPreferences.isFirstTime)
        srUsers.isRefreshing = AppPreferences.isFirstTime
    }

    private fun setUpObservers() {
        viewModel.users.observeNotNull {
            adapter.users = it
            srUsers.isRefreshing = false
        }
        viewModel.errorMessage.observeNotNull {
            Timber.e(it)
        }
    }

    override fun likeOrDislikeUser(uuid: String, liked: Boolean) {
        viewModel.likeUser(uuid, liked)
    }

    private fun setupViews() {
        adapter.listener = this
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = adapter

        srUsers.setOnRefreshListener { viewModel.fetch(true) }
    }
}
