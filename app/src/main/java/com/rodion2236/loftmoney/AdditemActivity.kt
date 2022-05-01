package com.rodion2236.loftmoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.adapters.TextViewBindingAdapter.setTextWatcher
import com.rodion2236.loftmoney.databinding.ActivityAdditemBinding

class AdditemActivity : AppCompatActivity()  {

    lateinit var bindingClass: ActivityAdditemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityAdditemBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.addButton.setOnClickListener(View.OnClickListener {
            intent.putExtra("key_title" , bindingClass.title.text.toString())
            intent.putExtra("key_cost" , bindingClass.cost.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        })


    }
}