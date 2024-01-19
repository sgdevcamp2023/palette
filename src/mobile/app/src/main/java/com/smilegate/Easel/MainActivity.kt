package com.smilegate.Easel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.smilegate.Easel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /* Status Bar & Navigation Bar */
        val barColor = ContextCompat.getColor(this, R.color.white)
        with(window) {
            statusBarColor = barColor
            navigationBarColor = barColor
        }
        with(WindowInsetsControllerCompat(window, window.decorView)) {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        window.decorView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_container)
        setSupportActionBar(toolbar)

        // 타이틀을 비워서 보이지 않도록 설정
        supportActionBar?.title = ""
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
    }
}
