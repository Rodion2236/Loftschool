package com.rodion2236.loftmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodion2236.loftmoney.databinding.ActivityAdditemBinding
import com.rodion2236.loftmoney.databinding.ActivityMainBinding

class AdditemActivity : AppCompatActivity() {

    lateinit var bindingClass: ActivityAdditemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindingClass.root)
    }

    fun AddNewTitle(view: View) {

        intent.putExtra("key" , bindingClass.title.text.toString())
        intent.putExtra("key" , bindingClass.cost.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }
}