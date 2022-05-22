package com.rodion2236.loftmoney.second

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.rodion2236.loftmoney.LoftApp
import com.rodion2236.loftmoney.R
import com.rodion2236.loftmoney.remote.moneyApi.MoneyApi
import com.rodion2236.loftmoney.databinding.ActivityAdditemBinding
import com.rodion2236.loftmoney.main.fragment_budget.BudgetFragment
import io.reactivex.disposables.CompositeDisposable

class AdditemActivity : AppCompatActivity() {

    lateinit var bindingAdditemBinding: ActivityAdditemBinding
    private var moneyApi: MoneyApi? = null
    private val compositeDisposable = CompositeDisposable()
    private var addItemViewModel: AddItemViewModel? = null

    companion object {
        const val KEY_COST = "key_cost"
        const val KEY_TITLE = "key_title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAdditemBinding = ActivityAdditemBinding.inflate(layoutInflater)
        setContentView(bindingAdditemBinding.root)

        setTextWatcher(bindingAdditemBinding.title, bindingAdditemBinding.addButton)
        setTextWatcher(bindingAdditemBinding.cost, bindingAdditemBinding.addButton)

        addItemViewModel = ViewModelProvider(this).get(AddItemViewModel::class.java)
        addItemViewModel!!.successAddItem.observe(this) { isRequestSuccess: Boolean ->
            if (isRequestSuccess) {
                finish()
            }
        }
        addItemViewModel!!.messageString.observe(this) { error: String ->
            if (!error.isEmpty()) {
                Toast.makeText(this@AdditemActivity, error, Toast.LENGTH_SHORT).show()
            }
        }

        bindingAdditemBinding.toolbar.setNavigationOnClickListener {
                view: View? -> onBackPressed()
        }

        bindingAdditemBinding.addItemButtonBack.setOnClickListener {
                view: View? -> onBackPressed()
        }

        moneyApi = (application as LoftApp).moneyApi
        bindingAdditemBinding.addButton.setOnClickListener {
            val name = bindingAdditemBinding.title.text.toString()
            val price = bindingAdditemBinding.cost.text.toString().toInt()
            val arguments = intent.extras
            val type = arguments!!.getString(BudgetFragment.TYPE)
            val token = getSharedPreferences(getString(R.string.app_name), 0)
                .getString(LoftApp.AUTH_KEY, "")
            addItemViewModel!!.addItemStream(moneyApi!!,name,price,type,token)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun setTextWatcher(title: TextInputEditText, addButton: Button) {
        title.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                addButton.isEnabled = bindingAdditemBinding.title.text.toString().isNotEmpty()
                                    && bindingAdditemBinding.cost.text.toString().isNotEmpty()
            }
        })
    }
}


