package com.example.submission3_mygithubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3_mygithubapp.adapter.ListUserAdapter
import com.example.submission3_mygithubapp.model.FollowViewModel
import com.example.submission3_mygithubapp.R
import com.example.submission3_mygithubapp.databinding.FragmentFollowersFollowingBinding

class FollowersFragment : Fragment(R.layout.fragment_followers_following) {
    private var _binding : FragmentFollowersFollowingBinding? = null
    private val binding get() =_binding!!
    private lateinit var followViewModel : FollowViewModel
    private lateinit var adapter: ListUserAdapter

    companion object {
        const val ARG_NAME = "username"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFollowersFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowersFollowingBinding.bind(view)

        followViewModel = ViewModelProvider(this).get(FollowViewModel::class.java)

        val username = arguments?.getString(ARG_NAME).toString()
        followViewModel.getFollowers(username)

        listFollowers()
        showLoading(true)
    }

    private fun listFollowers(){
        followViewModel.followUser.observe(viewLifecycleOwner){
            adapter = ListUserAdapter(it)
            binding.rvUser.adapter = adapter
            binding.rvUser.layoutManager = LinearLayoutManager(requireActivity())
            showLoading(false)
        }

        binding.rvUser.setHasFixedSize(true)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}