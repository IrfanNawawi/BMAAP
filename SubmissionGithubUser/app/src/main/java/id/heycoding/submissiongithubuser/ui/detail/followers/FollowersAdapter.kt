package id.heycoding.submissiongithubuser.ui.detail.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.databinding.ItemRowUserBinding

class FollowersAdapter(
    private val listFollowersUser: List<User>
) :
    RecyclerView.Adapter<FollowersAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

            binding.apply {
                Glide.with(itemView.context).load(user.avatarUrl).into(binding.imgUserPhoto)
                binding.tvUserName.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFollowersUser[position])
    }

    override fun getItemCount(): Int = listFollowersUser.size
}