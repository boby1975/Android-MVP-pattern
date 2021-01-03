package boby.mvp_pattern.view.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import boby.mvp_pattern.R
import boby.mvp_pattern.data.domainModels.User
import boby.mvp_pattern.utils.CircleTransform
import com.squareup.picasso.Picasso

class UsersAdapter(var items: List<User>): RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
    private var callback: Callback? = null

    fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return UsersViewHolder(v)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items.getOrElse(position){items.first()})
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val login = itemView.findViewById<TextView>(R.id.login_text_view)
        private val id = itemView.findViewById<TextView>(R.id.id_text_view)
        private val container = itemView.findViewById<ConstraintLayout>(R.id.user_item_container)
        private val avatarImageView = itemView.findViewById<ImageView>(R.id.avatar_image_view)
        private val avatarTextView = itemView.findViewById<TextView>(R.id.avatar_text_view)

        internal fun bind(item: User) {

            if (!item.avatarUrl.isNullOrEmpty()){
                Picasso.get().load(item.avatarUrl).transform(CircleTransform()).into(avatarImageView)
                avatarTextView.visibility = View.GONE
            } else {
                avatarTextView.visibility = View.VISIBLE
                avatarTextView.text = item.login.take(1).toUpperCase()
            }

            login.text = item.login
            id.text = item.userId.toString()

            container.setOnClickListener {
                callback?.onItemClick(item)
            }
        }
    }

    interface Callback {
        fun onItemClick(user: User)
    }
}