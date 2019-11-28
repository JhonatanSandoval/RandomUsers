package pe.jsandoval.randomusers.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import kotlinx.android.synthetic.main.item_user.view.*
import pe.jsandoval.randomusers.R
import pe.jsandoval.randomusers.domain.model.User

class UserAdapter constructor(val context: Context) : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var users = listOf<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var listener: Listener? = null

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) = holder.bindHolder(users[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserHolder(view, listener)
    }

    inner class UserHolder(view: View, private val listener: Listener?) : RecyclerView.ViewHolder(view) {

        fun bindHolder(user: User?) {
            user ?: return

            itemView.ivThumbnail.load(user.picMedium) {
                transformations(CircleCropTransformation())
                crossfade(true)
            }
            itemView.tvNames.text = user.getFullName()
            itemView.tvEmail.text = user.email
            itemView.tvGender.text = user.gender

            val drawable = if (user.liked) ContextCompat.getDrawable(context, R.drawable.ic_thumb_up) else ContextCompat.getDrawable(context, R.drawable.ic_thumbs_outline)
            itemView.ivAction.setImageDrawable(drawable)
            itemView.ivAction.setOnClickListener {
                val toChange = !user.liked
                listener?.likeOrDislikeUser(user.uuid, toChange)
                user.liked = toChange
                notifyItemChanged(adapterPosition)
            }
        }
    }

    companion object {
        interface Listener {
            fun likeOrDislikeUser(uuid: String, liked: Boolean)
        }
    }
}