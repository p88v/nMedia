package ru.netology.nmedia.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.adapter.PostListner
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        val adapter = PostAdapter(
            object : PostListner {
                override fun onLike(post: Post) {
                    viewModel.like(post.id)
                }

                override fun onClick(post: Post) {
                    findNavController().navigate(
                        R.id.action_feedFragment_to_openPostFragment,
                        Bundle().apply {
                            longArg = post.id
                        }
                    )
                }

                override fun onDislike(post: Post) {
                    viewModel.dislike(post.id)
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

                override fun onRemove(post: Post) {
                    viewModel.remove(post.id)
                }

                override fun onEdit(post: Post) {

                    findNavController().navigate(
                        R.id.action_feedFragment_to_editPostFragment,
                        Bundle().apply {
                            longArg = post.id
                        }
                    )

                    viewModel.edit(post)
                }
            }
        )




        binding.addNewPost.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createPostFragment)
        }

        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStaet.collect { postUiState ->
                    adapter.submitList(postUiState.posts)

                    binding.swipeRefresh.isRefreshing = postUiState.loading

                    postUiState.error?.let { errorText ->
                        Toast.makeText(requireContext(), errorText, Toast.LENGTH_SHORT).show()
                    }

                    if (postUiState.error == null && postUiState.loading == false && postUiState.empty) {
                        binding.emptyPost.isVisible = true
                    } else {
                        binding.emptyPost.isVisible = false
                    }


                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadPostsFromServer()
        }


        return binding.root
    }

    companion object {
        var Bundle.longArg by LongArg
    }


}


