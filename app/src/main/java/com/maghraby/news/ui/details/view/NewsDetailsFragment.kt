package com.maghraby.news.ui.details.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maghraby.news.data.model.News
import com.maghraby.news.databinding.FragmentNewsDetailsBinding
import com.maghraby.news.utils.clickableLink
import com.maghraby.news.utils.setDate
import com.maghraby.news.utils.setImage
import com.maghraby.news.utils.setTime

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
            if (newsObject?.url != null) {
                binding.linkTV.clickableLink(newsObject!!.url)
            }

            newsObject?.published_at?.let {
                binding.pubDateTV.setDate(it)
                binding.pubTimeTV.setTime(it)
            }
            binding.authorTV.text = newsObject?.author
        }

    }
}