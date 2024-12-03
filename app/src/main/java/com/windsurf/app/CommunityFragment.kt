package com.windsurf.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.windsurf.app.data.model.CommunityPost
import com.windsurf.app.ui.community.CommunityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CommunityFragment : Fragment() {
    private val viewModel: CommunityViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressIndicator: CircularProgressIndicator
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: CommunityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_community, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews(view)
        setupTabs()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupViews(view: View) {
        recyclerView = view.findViewById(R.id.communityRecyclerView)
        progressIndicator = view.findViewById(R.id.progressIndicator)
        tabLayout = view.findViewById(R.id.tabLayout)
    }

    private fun setupTabs() {
        with(tabLayout) {
            addTab(newTab().setText("All Posts"))
            addTab(newTab().setText("Events"))
            addTab(newTab().setText("Groups"))
            
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let { 
                        viewModel.setCurrentTab(it.position)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun setupRecyclerView() {
        adapter = CommunityAdapter(
            onLikeClick = { post ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.likePost(post.id)
                }
            },
            onCommentClick = { post ->
                // TODO: Navigate to comments screen
                Snackbar.make(requireView(), "Comments coming soon!", Snackbar.LENGTH_SHORT).show()
            }
        )
        
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CommunityFragment.adapter
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.posts.collectLatest { posts ->
                adapter.submitList(posts)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.isLoading.collectLatest { isLoading ->
                progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.error.collectLatest { error ->
                error?.let {
                    Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}
