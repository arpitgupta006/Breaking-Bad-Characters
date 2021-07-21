package com.arpit.breakingbadcharacters

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var characterAdapter : MyAdapter
    private var TAG = "MainActivity"
    private var characterData= mutableListOf<ResponseBBItem?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshLayout.setOnRefreshListener {
            getCharacterInfo()
            refreshLayout.isRefreshing = false
        }
        getCharacterInfo()

        characterAdapter = MyAdapter(characterData, this)
        recyclerView.adapter = characterAdapter

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.getAnimation()
        layoutManager.setPagerFlingVelocity(3000)
        recyclerView.layoutManager = layoutManager

        layoutManager.setItemChangedListener(object : StackLayoutManager.ItemChangedListener {
            override fun onItemChanged(position: Int) {
                    getCharacterInfo()
                }
        })
    }


            private fun getCharacterInfo() {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = Client.api.getInfo()
                        if (response.isSuccessful) {
                            val characterList = response.body()
                            withContext(Dispatchers.Main) {
                                if (characterList != null) {
                                    characterData.addAll(characterList)
                                    characterAdapter.notifyDataSetChanged()
                                    progressBar.visibility = GONE
                                    getCharacterInfo()
                                } else {
                                    Toast.makeText(applicationContext, "List Empty", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Cannot Load Data", Toast.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }

