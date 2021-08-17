package com.adesire.kmmdemo.kmmDemoAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adesire.kmmdemo.shared.Greeting
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adesire.kmmdemo.kmmDemoAndroid.adapters.RestaurantAdapter
import com.adesire.kmmdemo.kmmDemoAndroid.databinding.ActivityMainBinding
import com.adesire.kmmdemo.shared.viewmodels.RestaurantViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val greetingObj = Greeting()

    lateinit var restaurantViewModel: RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        binding.demoRv.layoutManager = LinearLayoutManager(this)

        restaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        restaurantViewModel.getListOfRestaurants()
        restaurantViewModel.restaurants().addObserver {
            val adapter = RestaurantAdapter()
            adapter.setItems(it)
            binding.demoRv.adapter = adapter
        }

        /*val languageGreet: List<String> = greetingObj.greetingInVariousLanguages()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
        languageGreet)

        val demoRv: ListView = findViewById(R.id.demoRv)
        demoRv.adapter = arrayAdapter*/
    }

    fun greet(): String {
        return greetingObj.greeting()
    }
}
