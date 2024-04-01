package com.bsuir.collabhub.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bsuir.collabhub.R
import com.bsuir.collabhub.databinding.ActivityMainBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: Router

    private val navigator = AppNavigator(this, R.id.containerFragment)

    private val screensCache = mutableMapOf<Int, FragmentScreen>()

    private lateinit var binding: ActivityMainBinding


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigationView()
    }

    private fun setupNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener { item ->
            screensCache.getOrElse(item.itemId) {
                val screen = when (item.itemId) {
                    R.id.menu_tasks -> Screens.stub()

                    else -> throw RuntimeException("Unknown menu item clicked")
                }
                screensCache[item.itemId] = screen
                screen
            }.let { screen ->
                router.replaceScreen(screen)
            }
            true
        }

        binding.navigationView.setCheckedItem(R.id.menu_tasks)
        binding.navigationView.menu.performIdentifierAction(R.id.menu_tasks, 0)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}