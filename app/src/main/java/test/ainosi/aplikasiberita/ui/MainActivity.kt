package test.ainosi.aplikasiberita.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import test.ainosi.aplikasiberita.R
import test.ainosi.aplikasiberita.base.BaseActivity
import test.ainosi.aplikasiberita.databinding.ActivityMainBinding
import test.ainosi.aplikasiberita.viewmodel.NewsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, NewsViewModel>(
    R.layout.activity_main,
    NewsViewModel::class.java
) {
    @Inject
    lateinit var appFragmentFactory: AppFragmentFactory

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null){
            supportFragmentManager.fragmentFactory = appFragmentFactory
        }
    }
}