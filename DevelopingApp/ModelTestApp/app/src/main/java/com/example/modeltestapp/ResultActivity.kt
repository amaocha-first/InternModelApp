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
import com.example.modeltestapp.entities.AddressEntity
import com.example.modeltestapp.entities.PropertyEntity
import com.example.modeltestapp.entities.ResultEntity
import com.example.modeltestapp.entities.SearchInputEntity
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {

    //APIから取得できた結果
    private var result: ResultEntity? = null

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
        //正常に値が返ってきたと想定して、、
        val entity = PropertyEntity(
            price = "90000",
            addressEntity = AddressEntity(prefecture = "東京都", city = "渋谷区", tyome = "神宮前1-2-12"),
            nearestStation = "JR線渋谷駅",
            distance = "徒歩15分"
        )
        val entity2 = PropertyEntity(
            price = "56000",
            addressEntity = AddressEntity(prefecture = "東京都", city = "千代田区", tyome = "神田駿河台2-1-34"),
            nearestStation = "JR線御茶ノ水駅",
            distance = "徒歩5分"
        )

        val entity3 = PropertyEntity(
            price = "78000",
            addressEntity = AddressEntity(prefecture = "東京都", city = "調布市", tyome = "菊野台3-3-23"),
            nearestStation = "JR線調布駅",
            distance = "徒歩10分"
        )

        val entity4 = PropertyEntity(
            price = "102000",
            addressEntity = AddressEntity(prefecture = "東京都", city = "港区", tyome = "麻布十番1-1-13"),
            nearestStation = "JR線麻布十番駅",
            distance = "徒歩10分"
        )

        val properties = arrayListOf(entity, entity2, entity3, entity4)
        this.result = ResultEntity(id = "0", properties = properties)
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
                .resize(600, 600) //表示サイズ指定
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
