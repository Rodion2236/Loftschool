package com.rodion2236.loftmoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodion2236.loftmoney.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val adapter = LoftRVAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        binding.apply {
            loftRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            loftRecycler.adapter = adapter

            loftRecycler.addItemDecoration(DividerItemDecoration(baseContext, LinearLayoutManager.VERTICAL))
        }
    }

    fun newObjectAdd(view: View) {

        val i = Intent(this, AdditemActivity::class.java)
        i.putExtra("key", "")
        startActivityForResult(i,100 )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

        } else {

        }
    }
}