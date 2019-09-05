package com.example.modeltestapp

import android.content.Context
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.modeltestapp.entities.SearchInputEntity
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {

    //データを用意
    val names = listOf("¥97,000", "¥56,000", "¥45,000", "¥89,000", "¥110,000")
    val images = ArrayList<Int>()

    data class PropertyData(val price: String, val imageId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        //全画面へ戻る
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "検索結果"

        //API通信する前にMainActivityから渡されたObjectをデシリアライズする
        val json = intent.extras!!.getString("INPUT_KEY")
        val input = Gson().fromJson<SearchInputEntity>(json, SearchInputEntity::class.java)
        Log.d("デシリアライズ", "${input}")

        //TODO: - 画面が立ち上がるときにAPI通信を始める（通信が終わるまではindicatorをだす）

        //APIデータにサムネ画像がないので、localの適当な画像を表示
        this.names.forEach { images.add(R.drawable.test_image) }
        setupListView()
    }

    //カスタムListViewのセットアップ
    private fun setupListView() {
        val property = List(names.size) { i -> PropertyData(price = names[i], imageId = this.images[i])}

        val adapter = PropertyListAdapter(this, properties = property)
        val listView = findViewById<ListView>(R.id.result_list_view)
        listView.adapter = adapter

        // 項目をタップしたときの処理
        listView.setOnItemClickListener {parent, view, position, id ->

            val imageView = ImageView(this)

            Picasso.get()
                //画像URL
                .load("https://1.bp.blogspot.com/-kwMHBpDRC98/WMfCOCDhmCI/AAAAAAABClk/0YhKPlx69H8akEluJniMmVV-RoJCRtPvACLcB/s800/onsei_ninshiki_smartphone.png")
                .resize(300, 300) //表示サイズ指定
                .centerCrop() //resizeで指定した範囲になるよう中央から切り出し
                .into(imageView) //imageViewに流し込み

            AlertDialog.Builder(this)
                .setTitle("タイトル")
                .setView(imageView)
                .setPositiveButton("ok"){ dialog, which ->
                }.show()
        }
    }


    //ViewHolderの定義
    data class ViewHolder(val priceTextView: TextView, val propertyImageView: ImageView)

    //独自アダプターの定義
    class PropertyListAdapter(context: Context, properties: List<PropertyData>): ArrayAdapter<PropertyData>(context, 0, properties) {
        private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView
            var holder: ViewHolder

            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item, parent, false)
                val price_text_view = view.findViewById<TextView>(R.id.price_text_view)
                val property_image_view = view.findViewById<ImageView>(R.id.property_image_view)
                holder = ViewHolder(priceTextView = price_text_view, propertyImageView = property_image_view)
                view.tag = holder
            } else {
                holder = view.tag as ViewHolder
            }

            val property = getItem(position) as PropertyData
            holder.priceTextView.text = property.price
            holder.propertyImageView.setImageBitmap(BitmapFactory.decodeResource(context.resources, property.imageId))
            return view!!
        }
    }

    //前画面へ戻る
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

}
