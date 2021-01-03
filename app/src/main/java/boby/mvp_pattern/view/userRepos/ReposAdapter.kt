package boby.mvp_pattern.view.userRepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import boby.mvp_pattern.App
import boby.mvp_pattern.R
import boby.mvp_pattern.data.domainModels.Repo

class ReposAdapter(var items: List<Repo>): RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {
    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)

        return ReposViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        holder.bind(items.getOrElse(position){items.first()})
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ReposViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.text_item_title)
        private val description = itemView.findViewById<TextView>(R.id.text_item_description)
        private val container = itemView.findViewById<LinearLayout>(R.id.layout_content)
        private val stars = itemView.findViewById<TextView>(R.id.text_stars)
        private val watchers = itemView.findViewById<TextView>(R.id.text_watchers)
        private val forks = itemView.findViewById<TextView>(R.id.text_forks)

        internal fun bind(item: Repo) {
            title.text = item.name
            description.text = item.description
            stars.text = App.getAppResources().getString(R.string.text_stars, item.stars)
            watchers.text = App.getAppResources().getString(R.string.text_watchers, item.watchers)
            forks.text = App.getAppResources().getString(R.string.text_forks, item.forks)

            container.setOnClickListener {
                callback?.onItemClick(item)
            }
        }
    }

    interface Callback {
        fun onItemClick(repository: Repo)
    }
}