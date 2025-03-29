package com.example.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCurrencyFrom: Spinner
    private lateinit var spinnerCurrencyTo: Spinner
    private lateinit var editTextCurrencyAmount: EditText
    private lateinit var buttonConvertCurrency: Button
    private lateinit var textViewConvertedAmount: TextView
    private lateinit var imageViewSwap: ImageView  // Swap button

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.91,
        "INR" to 83.0,
        "GBP" to 0.78,
        "JPY" to 150.5
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI elements
        spinnerCurrencyFrom = findViewById(R.id.spinnerCurrencyFrom)
        spinnerCurrencyTo = findViewById(R.id.spinnerCurrencyTo)
        editTextCurrencyAmount = findViewById(R.id.editTextCurrencyAmount)
        buttonConvertCurrency = findViewById(R.id.buttonConvertCurrency)
        textViewConvertedAmount = findViewById(R.id.textViewConvertedAmount)
        imageViewSwap = findViewById(R.id.imageViewSwap)  // Swap button

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spinnerCurrencyFrom.adapter = adapter
        spinnerCurrencyTo.adapter = adapter

        buttonConvertCurrency.setOnClickListener {
            buttonConvertCurrency.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
            convertCurrency()
        }

        // Swap button logic
        imageViewSwap.setOnClickListener {
            swapCurrencies()
        }

        // Toolbar button to navigate to another activity
        val toolbarButton: ImageView = findViewById(R.id.right_icon)
        toolbarButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }

    // Swap the selected items of the spinners and convert the amount
    private fun swapCurrencies() {
        val fromPosition = spinnerCurrencyFrom.selectedItemPosition
        val toPosition = spinnerCurrencyTo.selectedItemPosition

        // Swap the selected values
        spinnerCurrencyFrom.setSelection(toPosition)
        spinnerCurrencyTo.setSelection(fromPosition)

        // Swap the amount dynamically
        val amountStr = editTextCurrencyAmount.text.toString()
        if (amountStr.isNotEmpty()) {
            val amount = amountStr.toDouble()
            val fromCurrency = spinnerCurrencyTo.selectedItem.toString()
            val toCurrency = spinnerCurrencyFrom.selectedItem.toString()

            val fromRate = exchangeRates[fromCurrency] ?: 1.0
            val toRate = exchangeRates[toCurrency] ?: 1.0

            val newAmount = (amount / fromRate) * toRate
            editTextCurrencyAmount.setText("%.2f".format(newAmount)) // Update input field

            // Recalculate conversion and update result
            convertCurrency()
        }

        // Animate the swap button for better UI experience
        imageViewSwap.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in))
    }

    private fun convertCurrency() {
        val fromCurrency = spinnerCurrencyFrom.selectedItem.toString()
        val toCurrency = spinnerCurrencyTo.selectedItem.toString()
        val amountStr = editTextCurrencyAmount.text.toString()

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDouble()
        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0

        val convertedAmount = (amount / fromRate) * toRate
        textViewConvertedAmount.text = "Converted Amount: %.2f $toCurrency".format(convertedAmount)
    }
}
