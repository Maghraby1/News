package com.maghraby.news.ui.main.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.maghraby.news.R
import com.maghraby.news.data.model.News
import com.maghraby.news.databinding.FragmentNewsBinding
import com.maghraby.news.ui.main.adapter.NewsAdapter
import com.maghraby.news.ui.main.viewmodel.MainViewModel
import com.maghraby.news.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val mainViewModel: MainViewModel by viewModel()
    private val adapter: NewsAdapter by lazy {
        NewsAdapter(arrayListOf()) {
            findNavController().navigate(
                R.id.newsDetailsFragment,
                bundleOf("NEWS_POS" to (mainViewModel.news.value?.data?.get(it)))
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpObserver()
    }

    private fun setUpUI() {
        binding.newsRV.layoutManager = LinearLayoutManager(context)
        binding.newsRV.addItemDecoration(
            DividerItemDecoration(
                binding.newsRV.context,
                (binding.newsRV.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.newsRV.adapter = adapter
    }

    private fun setUpObserver() {
        mainViewModel.news.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.newsRV.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.newsRV.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun renderList(news: List<News>) {
        adapter.addData(news)
        adapter.notifyDataSetChanged()
    }
}