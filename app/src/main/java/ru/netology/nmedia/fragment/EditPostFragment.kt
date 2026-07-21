package ru.netology.nmedia.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentEditPostBinding
import ru.netology.nmedia.fragment.CreatePostFragment.Companion.stringArg
import ru.netology.nmedia.fragment.FeedFragment.Companion.longArg
import ru.netology.nmedia.util.LongArg
import ru.netology.nmedia.util.LongArg.getValue
import ru.netology.nmedia.util.LongArg.setValue
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.util.StringArg.getValue
import ru.netology.nmedia.util.StringArg.setValue
import ru.netology.nmedia.viewmodel.PostViewModel

class EditPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPostBinding.inflate(inflater, container, false)
        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)


        val postID = arguments?.longArg ?: throw NullPointerException("На редактирование пришел id типа null")
        val neededPost = viewModel.uiStaet.value.posts.find { it.id == postID }


       binding.editT.setText(neededPost?.content)


        binding.saveEditedPost.setOnClickListener {
            val newText = binding.editT.text.toString()
            viewModel.save(newText)
            findNavController().navigateUp()
        }

        return binding.root
    }

}

