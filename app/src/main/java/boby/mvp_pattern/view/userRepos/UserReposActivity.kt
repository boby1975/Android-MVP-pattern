package boby.mvp_pattern.view.userRepos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import boby.mvp_pattern.App
import boby.mvp_pattern.R
import boby.mvp_pattern.contract.UserReposContract
import boby.mvp_pattern.data.domainModels.Repo
import boby.mvp_pattern.utils.CircleTransform
import boby.mvp_pattern.utils.ViewUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_repos.*
import javax.inject.Inject

class UserReposActivity : AppCompatActivity(), UserReposContract.View {
    private val LOG_TAG = "UserReposActivity"
    private lateinit var reposRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var mContext: Context
    private var user = ""
    @Inject
    lateinit var presenter: UserReposContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repos)
        mContext = this
        App.mainComponent.inject(this)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val intent = intent
        user = intent.getStringExtra("login")?:""
        val avatarUrl = intent.getStringExtra("avatarUrl")
        val avatarImageView = findViewById<ImageView>(R.id.avatar_image_view)
        val avatarTextView = findViewById<TextView>(R.id.avatar_text_view)
        val userTextView = findViewById<TextView>(R.id.user_text_view)
        userTextView.text = user
        if (!avatarUrl.isNullOrEmpty()){
            Picasso.get().load(avatarUrl).transform(CircleTransform()).into(avatarImageView)
            avatarTextView.visibility = View.GONE
        } else {
            avatarTextView.visibility = View.VISIBLE
            avatarTextView.text = user.take(1).toUpperCase()
        }

        progressBar = findViewById(R.id.progressBar)
        reposRecyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView(reposRecyclerView)

        presenter.attachView(this)
        presenter.onViewCreated(user)
    }

    override fun onDestroy() {
        Log.d(LOG_TAG, "onDestroy")
        presenter.detachView()
        super.onDestroy()
    }

    override fun showRepos(repositories: List<Repo>) {
        val adapter: ReposAdapter = reposRecyclerView.adapter as ReposAdapter
        adapter.items = repositories
        adapter.notifyDataSetChanged()
    }

    override fun getContext(): Context {
        return this
    }

    override fun showProgressIndicator(visible: Boolean){
        when (visible) {
            true -> progressBar.visibility = View.VISIBLE
            false -> progressBar.visibility = View.GONE
        }
    }

    override fun showError(error: String) {
        ViewUtils().showSnackBarAttention(error, userContentLayout, mContext)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = ReposAdapter(emptyList())

        adapter.setCallback(object: ReposAdapter.Callback {
            override fun onItemClick(repository: Repo) {
                Log.d(LOG_TAG, "onItemClick, " + repository.name)

            }
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}