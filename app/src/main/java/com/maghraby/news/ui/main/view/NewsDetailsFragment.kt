package com.maghraby.news.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.maghraby.news.R
import com.maghraby.news.data.model.News
import com.maghraby.news.databinding.FragmentNewsDetailsBinding
import com.maghraby.news.ui.main.viewmodel.MainViewModel
import com.maghraby.news.utils.setImage
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private var newsObject: News? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey("NEWS_POS")) {
                newsObject = it.getParcelable("NEWS_POS")
            }
        }
        activity?.actionBar?.setHomeButtonEnabled(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsObject?.let {
            binding.mainIV.setImage(newsObject?.image)
            binding.titleTV.text = newsObject?.title
            binding.descriptionTV.text = newsObject?.description
        }

    }
}