package com.example.encrypto.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encrypto.adapters.FavouritesAdapter
import com.example.encrypto.databinding.ActivityFavBinding
import com.example.encrypto.listeners.FavCLickListener
import com.example.encrypto.models.User
import com.example.encrypto.viewmodels.FavViewModel

class FavActivity : AppCompatActivity(), FavCLickListener {

    private lateinit var binding: ActivityFavBinding

    private lateinit var favouritesAdapter: FavouritesAdapter
    private var list = ArrayList<User>()

    private lateinit var viewModel: FavViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        favouritesAdapter = FavouritesAdapter(list, this, this)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavActivity)
            adapter = favouritesAdapter
        }

        viewModel.favList.observe(this, { result ->
            list.clear()
            list.addAll(result)
            favouritesAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = INVISIBLE
        })

    }

    override fun deleteUser(user: User) {
        viewModel.deleteUser(user)
    }

}