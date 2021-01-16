package boby.mvp_pattern.view.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import boby.mvp_pattern.App
import boby.mvp_pattern.Constants.AVATAR_URL_KEY
import boby.mvp_pattern.Constants.LOGIN_KEY
import boby.mvp_pattern.Constants.USER_ID_KEY
import boby.mvp_pattern.R
import boby.mvp_pattern.contract.MainContract
import boby.mvp_pattern.data.domainModels.Rate
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.utils.ViewUtils
import boby.mvp_pattern.view.userRepos.UserReposActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_repos.toolbar
import javax.inject.Inject

class MainActivity: AppCompatActivity(), MainContract.View {
    private val LOG_TAG = "MainActivity"
    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mContext: Context
    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        App.mainComponent.inject(this)

        setSupportActionBar(toolbar)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh)
        usersRecyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView(usersRecyclerView)


        swipeRefreshLayout.setColorSchemeColors(Color.BLUE)
        swipeRefreshLayout.setOnRefreshListener {
            Log.d(LOG_TAG, "onRefresh called from SwipeRefreshLayout")
            presenter.onRefresh()
        }

        presenter.attachView(this)
        presenter.onViewCreated()
    }

    override fun onResume() {
        Log.d(LOG_TAG, "onResume")
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        presenter.detachView()
        super.onDestroy()
    }

    override fun showUsers(users: List<User>) {
        val adapter: UsersAdapter = usersRecyclerView.adapter as UsersAdapter
        adapter.items = users
        adapter.notifyDataSetChanged()
    }

    override fun showRate(rate: Rate) {
        textLimit.text = getString(R.string.text_limit, rate.limit)
        textRemaining.text = getString(R.string.text_remaining, rate.remaining)
        textUsed.text = getString(R.string.text_used, rate.used)
    }

    override fun getContext(): Context {
        return this
    }

    override fun openUserDetail(user: User) {
        val intent = Intent(mContext, UserReposActivity::class.java)
        intent.putExtra(USER_ID_KEY, user.userId)
        intent.putExtra(LOGIN_KEY, user.login)
        intent.putExtra(AVATAR_URL_KEY, user.avatarUrl)
        startActivity(intent)
    }

    override fun showProgressIndicator(visible: Boolean){
         when (visible) {
             true -> swipeRefreshLayout.isRefreshing = true
             false -> swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun showError(error: String) {
        ViewUtils.showSnackBarAttention(error, mainContentLayout, mContext)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = UsersAdapter(emptyList())

        adapter.setCallback(object : UsersAdapter.Callback {
            override fun onItemClick(user: User) {
                Log.d(LOG_TAG, "onItemClick, " + user.userId)
                presenter.onItemClick(user)
            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}