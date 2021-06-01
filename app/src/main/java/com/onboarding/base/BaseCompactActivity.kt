package com.onboarding.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseCompactActivity<Binding: ViewDataBinding,ViewModel: BaseViewModel>: AppCompatActivity() {
    protected lateinit var binding: Binding
    protected abstract val viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        initializeObserver(viewModel)
    }

    abstract fun getLayoutResID(): Int

    private fun bindViewModel() {
        binding = DataBindingUtil.setContentView(this,getLayoutResID())
        binding.apply {
            lifecycleOwner = this@BaseCompactActivity
        }
        initialize()
    }

    open fun initializeObserver(viewModel: ViewModel) {}

    open fun initialize() {}
}