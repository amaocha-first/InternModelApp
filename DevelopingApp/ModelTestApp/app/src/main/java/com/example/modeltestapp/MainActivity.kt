package com.example.modeltestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.modeltestapp.entities.SearchInputEntity
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val priceList = listOf<String>("5万以下", "6万", "7万", "8万", "9万", "10万", "15万以上")
    private val priceDic = hashMapOf("" to "")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSearchBtn()
        setupSpinner()
    }

    private fun setupSpinner() {
        val minSpinner: Spinner = findViewById<Spinner>(R.id.min_spinner)
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, priceList)
        minSpinner.adapter = adapter

        val maxSpinner: Spinner = findViewById<Spinner>(R.id.max_spinner)
        maxSpinner.adapter = adapter
    }

    //検索ボタンを押したら次画面へ
    private fun setupSearchBtn() {
        val searchBtn = findViewById<Button>(R.id.button)
        searchBtn.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            //TODO: - ここでSearchInputEntityに実際の値を入れて、ResultActivityに渡す
            val input = this.createObject()
            //hint: JSONシリアライズして、intent.putExtra()
            intent.putExtra("INPUT_KEY", input)
            startActivity(intent)
        }
    }

    private fun createObject(): String {
        val input = SearchInputEntity(minPrice = 30000, maxPrice = 60000, areaText = "", keyword = "")
        return Gson().toJson(input)
    }
}
