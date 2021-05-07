package com.adesire.kmmdemo.KMMDemoAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.adesire.kmmdemo.shared.Greeting
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val greetingObj = Greeting()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = greet()

        val languageGreet: List<String> = greetingObj.greetingInVariousLanguages()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,
        languageGreet)

        val demoRv: ListView = findViewById(R.id.demoRv)
        demoRv.adapter = arrayAdapter
    }

    fun greet(): String {
        return greetingObj.greeting()
    }
}
