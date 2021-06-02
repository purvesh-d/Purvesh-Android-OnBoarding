package com.onboarding.ui.activity

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.onboarding.R
import com.onboarding.base.BaseCompactActivity
import com.onboarding.databinding.ActivityMainBinding
import com.onboarding.viewmodel.ActivityMainViewModel
import com.tapadoo.alerter.Alerter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseCompactActivity<ActivityMainBinding, ActivityMainViewModel>(), View.OnClickListener {

    override val viewModel: ActivityMainViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun initialize() {
        super.initialize()
        binding.apply {
            clickHandler = this@MainActivity
            viewModel = this@MainActivity.viewModel
        }
    }

    override fun initializeObservers(viewModel: ActivityMainViewModel) {
        viewModel.apiResponse.observe(this, Observer {
            Alerter.create(this).setText(it).setBackgroundColorRes(R.color.teal_200).setIcon(R.drawable.ic_baseline_error_24).show()
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> {
                if (viewModel.isValidDetails()) {
                    Toast.makeText(this, applicationContext.getString(R.string.validating), Toast.LENGTH_LONG).show()
                    viewModel.doLogin()
                }
            }
        }
    }
}