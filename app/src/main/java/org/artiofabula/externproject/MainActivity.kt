package org.artiofabula.externproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import org.artiofabula.externproject.data.Dependencies
import org.artiofabula.externproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dependencies.init(applicationContext)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val drawerLayout = binding.drawerLayout
        appBarConfiguration = AppBarConfiguration(setOf(R.id.questions_fragment), drawerLayout)

        setContentView(binding.root)
        supportActionBar?.hide()
    }
}