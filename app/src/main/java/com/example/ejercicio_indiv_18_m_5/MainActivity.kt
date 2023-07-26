package com.example.ejercicio_indiv_18_m_5

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ejercicio_indiv_18_m_5.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPreferences = AppPreferences(this)

        binding.textViewStoredValue3.text =
            getString(R.string.stored_value_label3, appPreferences.getStoredValue())


        val storedIntegerValue =
            appPreferences.getIntegerValue("storedIntegerValue", R.string.stored_value_label)
        binding.textViewStoredValue.text =
            getString(R.string.stored_value_label, storedIntegerValue)


        val storedStringValue2 =
            appPreferences.getStringValue("storedStringValue", R.string.stored_value_label2.toString())


        val storedBooleanValue = appPreferences.getBooleanValue("storedBooleanValue", false)
        if (storedBooleanValue) {
            binding.radioButtonTrue.isChecked = true
        } else {
            binding.radioButtonFalse.isChecked = true
        }


        binding.textViewStoredValue2.text =
            getString(R.string.stored_value_label2, storedStringValue2)


        binding.buttonSave.setOnClickListener {
            saveNumber()
            saveValue()
            saveDecimal()
            val selectedOption = getSelectedOption()
            if (selectedOption != null) {
                appPreferences.saveBooleanValue("storedBooleanValue", selectedOption)

            } else {
                Toast.makeText(this, "Debe seleccionar True o False", Toast.LENGTH_SHORT).show()
            }
        }


        binding.buttonClear.setOnClickListener {
            clearValues()
        }
    }


    private fun saveNumber() {
        val input = binding.editTextNum.text.toString()
        val number = input.toIntOrNull()
        if (number != null) {
            appPreferences.saveIntegerValue("storedIntegerValue", number)
            binding.textViewStoredValue.text = getString(R.string.stored_value_label, number)
            binding.editTextNum.setText("")
        } else {
            Toast.makeText(this, R.string.invalid_number, Toast.LENGTH_SHORT).show()
        }
    }


    private fun saveValue() {
        val input2 = binding.editText.text.toString()
        val regex = Regex("^[A-Za-z .,!?-@]+\$")
        if (input2.matches(regex)) {
            appPreferences.saveStringValue("storedStringValue", input2)
            binding.textViewStoredValue2.text = getString(R.string.stored_value_label2, input2)
            binding.editText.setText("")
        } else {
            Toast.makeText(this, R.string.invalid_text, Toast.LENGTH_SHORT).show()
        }
    }


    private fun getSelectedOption(): Boolean? {
        return when (binding.radioGroup.checkedRadioButtonId) {
            binding.radioButtonTrue.id -> true
            binding.radioButtonFalse.id -> false
            else -> {
                Toast.makeText(this, "Debe seleccionar True o False", Toast.LENGTH_SHORT).show()
                null
            }
        }
    }

    private fun saveDecimal() {
        val inputValue = binding.editTextNumDecimal.text.toString().trim()
        if (isValidDecimal(inputValue)) {
            appPreferences.setStoredValue(inputValue)
            binding.textViewStoredValue3.text =
                getString(R.string.stored_value_label3, inputValue)
            binding.editTextNumDecimal.setText("")
        } else {
            Toast.makeText(this, "Ingresa un número con 2 decimales válidos.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun isValidDecimal(input: String): Boolean {

        return input.matches("^\\d+\\.\\d{2}$".toRegex())
    }


    private fun clearValues() {
        appPreferences.saveIntegerValue("storedIntegerValue", 0)
        appPreferences.saveStringValue("storedStringValue", "")
        appPreferences.saveBooleanValue("storedBooleanValue", false)
        appPreferences.setStoredValue("0")

        binding.textViewStoredValue.text = getString(R.string.stored_value_label, 0)
        binding.textViewStoredValue2.text = getString(R.string.stored_value_label2, "")
        binding.radioButtonFalse.isChecked = true
        binding.textViewStoredValue3.text = getString(R.string.stored_value_label3, "0")
        binding.radioGroup.clearCheck()
    }
}
