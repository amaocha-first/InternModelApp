package com.example.modeltestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupSearchBtn()
    }

    //検索ボタンを押したら次画面へ
    private fun setupSearchBtn() {
        val searchBtn = findViewById<Button>(R.id.button)
        searchBtn.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            //TODO: - ここでSearchInputEntityに実際の値を入れて、ResultActivityに渡す
            //hint: JSONシリアライズして、intent.putExtra()
            startActivity(intent)
        }
    }
}
