package com.ant8.flowplayground.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ant8.flowplayground.R
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var textView: TextView
    private lateinit var plusButton: Button
    private lateinit var minusButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)
        .apply {
            textView = findViewById(R.id.message)
            plusButton = findViewById(R.id.plus_button)
            minusButton = findViewById(R.id.minus_button)
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        lifecycleScope.launch {
            plusButton.setOnClickListener { viewModel.sendIntent(MainViewModel.Intent.PLUS) }
            minusButton.setOnClickListener { viewModel.sendIntent(MainViewModel.Intent.MULTIPLE_MINUS) }

            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.stateFlow
                    .onEach { textView.text = it.toString() }
                    .collect()
            }
        }
    }


}