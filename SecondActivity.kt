package com.example.currencyconverter

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var buttonConvert: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonSwap: ImageView

    data class Currency(val name: String, val flag: Int, val rate: Double)

    private val currencyList = listOf(
        Currency("USD - United States", R.drawable.ic_usa, 1.0),
        Currency("EUR - Euro", R.drawable.ic_uro, 0.91),
        Currency("INR - India", R.drawable.ic_india, 83.0),
        Currency("GBP - United Kingdom", R.drawable.ic_uk, 0.78),
        Currency("JPY - Japan", R.drawable.ic_japan, 150.5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advance_app)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editTextAmount = findViewById(R.id.editTextAmount)
        buttonConvert = findViewById(R.id.buttonConvert)
        textViewResult = findViewById(R.id.textViewResult)
        buttonSwap = findViewById(R.id.buttonSwap) // Swap button

        val adapter = CurrencyAdapter(this, currencyList)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        buttonConvert.setOnClickListener {
            convertCurrency()
        }

        buttonSwap.setOnClickListener {
            swapCurrencies()
        }

        val toolbarButton: ImageView = findViewById(R.id.left_icon)
        toolbarButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun convertCurrency() {
        val fromCurrency = currencyList[spinnerFrom.selectedItemPosition]
        val toCurrency = currencyList[spinnerTo.selectedItemPosition]
        val amountStr = editTextAmount.text.toString()

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amount = amountStr.toDouble()
        val convertedAmount = (amount / fromCurrency.rate) * toCurrency.rate

        textViewResult.text = "Converted Amount: %.2f ${toCurrency.name}".format(convertedAmount)
    }

    private fun swapCurrencies() {
        val fromPosition = spinnerFrom.selectedItemPosition
        val toPosition = spinnerTo.selectedItemPosition

        // Swap selected positions
        spinnerFrom.setSelection(toPosition)
        spinnerTo.setSelection(fromPosition)
    }
}
