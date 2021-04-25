package com.picpay.desafio.android.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.states.OnLoadingState
import com.picpay.desafio.android.states.OnPicPayServiceResponse
import com.picpay.desafio.android.R
import com.picpay.desafio.android.activity.viewmodel.ContatosViewModel
import com.picpay.desafio.android.adapter.UserListAdapter
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.repository.ContatosRespository

class ContatosActivity : AppCompatActivity(R.layout.contatos_activity) {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val progressBar by lazy { findViewById<ProgressBar>(R.id.user_list_progress_bar) }
    private val adapter by lazy {  UserListAdapter() }
    private val viewModel by lazy {
        ViewModelProvider(
            this, ContatosViewModel.ContatosViewModelFactory(
                ContatosRespository()
            )
        )[ContatosViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contatos_activity)

        viewModel.fetchPicPayUsers()
        subscribeUI()
    }

    private fun subscribeUI() {
        subscribePicPayUsers()
        subscribeLoading()
    }

    private fun subscribePicPayUsers() {
        viewModel.onPicPayServiceResponse.observe(this, Observer {
            when (it) {
                is OnPicPayServiceResponse.OnSuccess -> it.users?.let { users ->
                    onSuccessfulPicpayServiceResponse(users)
                }
                OnPicPayServiceResponse.OnFailure -> onFailurePicpayServiceResponse()
            }
        })
    }

    private fun subscribeLoading() {
        viewModel.onLoadingState.observe(this, Observer {
            when (it) {
                OnLoadingState.Show -> progressBar.visibility = View.VISIBLE
                OnLoadingState.Hide -> progressBar.visibility = View.GONE
            }
        })
    }

    private fun onSuccessfulPicpayServiceResponse(users: List<User>) {
        adapter.users = users
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onFailurePicpayServiceResponse() {
        val message = getString(R.string.error)
        recyclerView.visibility = View.GONE
        Toast.makeText(this@ContatosActivity, message, Toast.LENGTH_SHORT).show()
    }
}