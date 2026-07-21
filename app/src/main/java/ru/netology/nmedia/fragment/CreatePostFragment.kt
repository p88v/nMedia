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
import ru.netology.nmedia.databinding.FragmentCreatePostBinding
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.viewmodel.PostViewModel

class CreatePostFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
        val binding = FragmentCreatePostBinding.inflate(inflater, container, false)



        binding.saveNewPsot.setOnClickListener {
            viewModel.save(binding.editText.text.toString())
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }

        return binding.root
    }

    companion object{

        var Bundle.stringArg by StringArg

    }

}





