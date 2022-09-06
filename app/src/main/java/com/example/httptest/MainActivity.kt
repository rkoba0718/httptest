package com.example.httptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.eclipsesource.json.Json
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //val URL = "https://qiita.com/api/v2/items/bf3e4e06022eebe8e3eb" //サンプルとしてQiitaのAPIサービスを利用します
    val URL = "http://10.0.2.2:3000/profile"
    var result = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var getButton = findViewById<Button>(R.id.button1)
        var postButton = findViewById<Button>(R.id.button2)

        getButton.setOnClickListener { onParallelGetButtonClick() }
        postButton.setOnClickListener { onParallelPostButtonClick() }
    }

    //非同期処理でHTTP GETを実行します。
    fun onParallelGetButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        val http_get = HttpUtil()
        //Mainスレッドでネットワーク関連処理を実行するとエラーになるためBackgroundで実行
        async(Dispatchers.Default) { http_get.httpGet(URL) }.await().let {
            //minimal-jsonを使って　jsonをパース
            val result = Json.parse(it)
            val textView = findViewById(R.id.text) as TextView
            textView.setText(result.toString())
        }
    }

    fun onParallelPostButtonClick() = GlobalScope.launch(Dispatchers.Main) {
        val http_post = HttpUtil()
        async(Dispatchers.Default) { http_post.httpPost(URL) }.await().let {
            val textView = findViewById<TextView>(R.id.text)
            textView.setText("post finish")
        }
    }
}
