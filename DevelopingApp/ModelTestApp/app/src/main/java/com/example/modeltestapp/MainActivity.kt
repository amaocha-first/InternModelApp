package com.example.modeltestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.modeltestapp.entities.SearchInputEntity
import com.google.gson.Gson
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    //Input用データの定義
    private val priceList = listOf("5万以下", "6万", "7万", "8万", "9万", "10万", "15万以上")
    private val priceHashMap = hashMapOf(
        priceList[0] to 50000,
        priceList[1] to 60000,
        priceList[2] to 70000,
        priceList[3] to 80000,
        priceList[4] to 90000,
        priceList[5] to 100000,
        priceList[6] to 150000
    )

    //Inputコンポーネントの定義
    private var minSpinner: Spinner? = null
    private var maxSpinner: Spinner? = null
    private var areaEditText: EditText? = null
    private var freeWordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        areaEditText = findViewById<EditText>(R.id.area_edit_text)
        freeWordEditText = findViewById<EditText>(R.id.free_word_edit_text)
        setupSearchBtn()
        setupSpinner()
    }

    private fun setupSpinner() {
        minSpinner = findViewById<Spinner>(R.id.min_spinner)
        maxSpinner = findViewById<Spinner>(R.id.max_spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priceList)
        minSpinner!!.adapter = adapter
        maxSpinner!!.adapter = adapter
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

    //Inputオブジェクトの生成
    private fun createObject(): String {
        val input = SearchInputEntity(
            minPrice = priceHashMap["${minSpinner!!.selectedItem}"]!!,
            maxPrice = priceHashMap["${maxSpinner!!.selectedItem}"]!!,
            areaText = areaEditText!!.text.toString(),
            keyword = freeWordEditText!!.text.toString()
        )
        return Gson().toJson(input)
    }
}
