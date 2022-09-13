package com.maghraby.news.ui.main.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maghraby.news.R
import com.maghraby.news.ui.main.adapter.model.News
import com.maghraby.news.databinding.FragmentNewsBinding
import com.maghraby.news.ui.main.adapter.CountryAdapter
import com.maghraby.news.ui.main.adapter.NewsAdapter
import com.maghraby.news.ui.main.adapter.model.Country
import com.maghraby.news.ui.main.viewmodel.MainViewModel
import com.maghraby.news.utils.Resource
import com.maghraby.news.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private var selectedCountries = arrayListOf<String>()
    private val mainViewModel: MainViewModel by viewModel()
    lateinit var mLayoutManger: LinearLayoutManager
    private val lastVisibleItemPosition: Int
        get() = mLayoutManger.findLastCompletelyVisibleItemPosition()
    private val adapter: NewsAdapter by lazy {
        NewsAdapter(arrayListOf()) {
            findNavController().navigate(
                R.id.newsDetailsFragment,
                bundleOf("NEWS_POS" to it)
            )
        }
    }
    private val countryAdapter: CountryAdapter by lazy {
        CountryAdapter(arrayListOf(), {
            selectedCountries.add(it)
            getNews()
        }, {
            selectedCountries.remove(it)
            getNews()
        })
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
        mLayoutManger = LinearLayoutManager(requireActivity())
        val xLayoutManger = LinearLayoutManager(requireActivity(), HORIZONTAL, false)
        mLayoutManger.isSmoothScrollbarEnabled = true
        binding.newsRV.layoutManager = mLayoutManger
        binding.newsRV.adapter = adapter
        binding.countriesRV.layoutManager = xLayoutManger
        binding.countriesRV.adapter = countryAdapter
        initRecyclerViewScrollListener()
    }

    private fun setUpObserver() {
        mainViewModel.news.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { news -> renderList(news) }
                    binding.newsRV.visibility = View.VISIBLE
                }
            }
        }

        mainViewModel.countries.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) renderCountries(it)
        }
    }

    private fun getNews() {
        if (selectedCountries.isEmpty()) {
            mainViewModel.fetchNews()
            return
        }
        val countries = StringBuilder()
        selectedCountries.forEachIndexed { index, it ->
            countries.append(it)
            countries.append(',')
        }
        countries.removeSurrounding(",")
        mainViewModel.fetchNewsByCountry(countries.toString())
    }

    private fun renderList(news: List<News>) {
        if(mainViewModel.offset==25){
            adapter.addData(news,true)
        }else{
            adapter.addData(news)
        }

        adapter.notifyDataSetChanged()
    }

    private fun renderCountries(countries: List<Country>) {
        countryAdapter.addData(countries)
        countryAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerViewScrollListener() {
        binding.newsRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (adapter.itemCount > 1)
                    if (lastVisibleItemPosition == adapter.itemCount - 1
                        && mainViewModel.news.value != Resource.loading(null)
                    ) {
                        getNews()
                    }
            }
        })
    }
}