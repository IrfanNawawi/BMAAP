package id.heycoding.submissiongithubuser.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.submissiongithubuser.data.remote.response.User
import id.heycoding.submissiongithubuser.databinding.ItemRowUserBinding

class SearchUserAdapter(
    private val callback: SearchUserCallback,
    private val listUser: List<User>
) :
    RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {

            binding.apply {
                Glide.with(itemView.context).load(user.avatarUrl).into(binding.imgUserPhoto)
                binding.tvUserName.text = user.login

                itemView.setOnClickListener {
                    callback.setIntentDetailUser(user)
                }
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
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}