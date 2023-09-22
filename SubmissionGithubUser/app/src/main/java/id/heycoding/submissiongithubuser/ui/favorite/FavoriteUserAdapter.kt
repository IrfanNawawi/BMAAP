package id.heycoding.submissiongithubuser.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.heycoding.submissiongithubuser.data.local.entity.UserEntity
import id.heycoding.submissiongithubuser.databinding.ItemRowUserBinding

class FavoriteUserAdapter(
    private val callback: FavoriteUserCallback,
    private val listFavorite: List<UserEntity>
) : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userEntity: UserEntity) {

            binding.apply {
                Glide.with(itemView.context).load(userEntity.avatarUrl)
                    .into(binding.imgUserPhoto)
                binding.tvUserName.text = userEntity.username

                itemView.setOnClickListener {
                    callback.setIntentDetailUser(userEntity)
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
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size
}