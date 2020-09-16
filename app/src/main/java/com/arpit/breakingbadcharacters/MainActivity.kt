package com.arpit.breakingbadcharacters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception

const val baseurl = "https://www.breakingbadapi.com/api/"

class MainActivity : AppCompatActivity() {

//    "https://www.breakingbadapi.com/api/"
    private var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCharacterInfo()

        linearLayout.setOnClickListener {
            getCharacterInfo()
        }
    }

    private fun getCharacterInfo() {


        val api = Retrofit.Builder()
            .baseUrl(baseurl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getInfo().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    Log.d(TAG, data.toString())

                    withContext(Dispatchers.Main) {
                        Picasso.get().load(data.img).into(ivImage)
                        tvName.text = data.name
                        tvOccupation.text = data.occupation.toString()
                        tvActor.text = data.portrayed
                        tvAppearance.text = data.appearance.toString()
                        tvStatus.text = data.status
                    }
                }
            }
            catch (e:Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Cannot Load Data" , Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}