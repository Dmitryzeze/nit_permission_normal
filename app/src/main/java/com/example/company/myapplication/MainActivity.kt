package com.example.company.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {
        interface UserService {
            @GET("/")
            fun getResponse(): Call<String>
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            val editText: EditText = findViewById(R.id.editText)
            val textView: TextView = findViewById(R.id.textView)
            val button: Button = findViewById(R.id.button)
            button.setOnClickListener {
                val url = editText.text.toString()
                if (url.contains("http")) {
                    val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                    val userService = retrofit.create(UserService::class.java)
                    val res = userService.getResponse()
                    res.enqueue(object : Callback<String> {
                        override fun onResponse(p0: Call<String>, p1: Response<String>) {
                            if (p1.isSuccessful) textView.text = "Ok" else textView.text = "Failed"
                        }

                        override fun onFailure(p0: Call<String>, p1: Throwable) {
                            textView.text = "Failed"
                        }
                    })
                } else {
                    textView.text = "Failed"
                }
            }
        }
    }
