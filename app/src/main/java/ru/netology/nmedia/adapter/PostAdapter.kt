package ru.netology.nmedia.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.floor
import androidx.core.net.toUri
import com.bumptech.glide.Glide


interface PostListner {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onClick(post: Post)
}

object PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Post,
        newItem: Post
    ): Boolean {
        return oldItem == newItem
    }

}

class PostAdapter(
    private val listner: PostListner
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding, listner)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listner: PostListner,

    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        with(binding) {
            tvNameAuthor.text = post.author
            tvPublished.text = post.published
            tvPost.text = post.content
            tvCountLikes.text = convertNumbers(post.likes)
            btnImgShare.text = convertNumbers(post.countShare)
            tvViews.text = convertNumbers(post.countViews)



            Glide.with(imgAvatar)
                .load(post.authorPicture)
                .circleCrop()
                .placeholder(R.drawable.avatarIsEmpty)
                .error(R.drawable.ERROR)
                .into(imgAvatar)

            if(post.attachment != null){
                myGroup.visibility = View.VISIBLE
                Glide.with(imgViewPost)
                    .load("http://10.0.2.2:9999/images/${post.attachment.image}")
                    .into(imgViewPost)

                descr.text = post.attachment.describe
                link.text = post.attachment.url
            } else myGroup.visibility = View.GONE


            tvPost.setOnClickListener {
                listner.onClick(post)
            }

            if(post.video != null){
                btnZaglushka.isVisible = true
            } else{
                btnZaglushka.isVisible = false
            }


            imgBtnMore.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.post_menu)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listner.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                listner.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }

            btnZaglushka.setOnClickListener {
                val url = post.video ?: return@setOnClickListener
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                it.context.startActivity(intent)
            }


            btnImgLike.isChecked = post.likedByMe

            btnImgLike.setOnClickListener {
                listner.onLike(post)
            }
            btnImgShare.setOnClickListener {
                listner.onShare(post)
            }
        }
    }

    private fun convertNumbers(number: Int): String {
        if (number < 1000) {
            return number.toString()
        } else if (number < 9_999 && number > 1_000) {
            val result = number / 1000.0
            val rounded = floor(result * 10) / 10.0
            return rounded.toString() + "k"

        } else if (number > 9_999 && number < 1_000_000) {
            val result = number / 1000.0
            return result.toInt().toString() + "k"
        } else if (number >= 1_000_000) {
            if (number % 1_000_000 == 0 || number % 1_000_000 < 99_999) {
                val result = number / 1_000_000.0
                return result.toInt().toString() + "M"
            } else {
                val result = number / 1_000_000.0
                val rounded = floor(result * 10) / 10.0
                return rounded.toString() + "M"
            }
        }
        return " "
    }
}