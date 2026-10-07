package ru.netology.nmedia.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostListner
import ru.netology.nmedia.adapter.PostViewHolder

import ru.netology.nmedia.databinding.FragmentOpenPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.fragment.FeedFragment.Companion.longArg
import ru.netology.nmedia.viewmodel.PostViewModel
import kotlin.getValue

class OpenPostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding = FragmentOpenPostBinding.inflate(inflater, container, false)
        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)


        val postId =
            arguments?.longArg ?: throw NullPointerException("Пришло пустое значение по id поста")


        val holder = PostViewHolder(
            binding.cardPost, object : PostListner {
                override fun onLike(post: Post) {
                    viewModel.like(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.share(post.id)
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        type = "text/plane"
                        putExtra(Intent.EXTRA_TEXT, post.content)
                    }
                    val chooser = Intent.createChooser(intent, "Share")
                    startActivity(chooser)
                }

                override fun onDislike(post: Post) {
                    viewModel.dislike(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.remove(post.id)
                    findNavController().navigateUp()
                }

                override fun onEdit(post: Post) {
                    findNavController().navigate(
                        R.id.action_openPostFragment_to_editPostFragment, Bundle().apply {
                            longArg = post.id
                        })
                    viewModel.edit(post)
                }

                override fun onClick(post: Post) {
                    Toast.makeText(
                        context, "Вы уже открыли детальный просмотр поста", Toast.LENGTH_SHORT
                    ).show()
                }
            })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStaet.collect { posts ->
                    val neededPost = viewModel.uiStaet.value.posts.find { it.id == postId }

                    holder.bind(neededPost!!)
                }
            }
        }


        return binding.root
    }


}