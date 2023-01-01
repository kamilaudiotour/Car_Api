package com.example.carapi.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

fun nameValidation(name: EditText): Boolean {
    name.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }


        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val tempName = name.text.toString().trim()
            if (tempName.isEmpty()) {
                name.error = "Nie może być pusto"
            } else {
                for (i in name.text) {
                    if (i == ' ') {
                        name.error = null
                        break
                    } else {
                        name.error = "Nie poprawne dane"
                    }
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    })

    return if (name.text.toString().isNotEmpty()) {
        name.error == null
    } else false
}

fun phoneValidation(phone: EditText): Boolean {


    phone.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val tempPhone = phone.text.toString().trim()
            if (tempPhone.isEmpty()) {
                phone.error = "Nie może być pusto"
            } else {
                if (tempPhone.length > 8) {
                    phone.error = null
                } else {
                    phone.error = "Numer jest za krótki"
                }
            }
        }
    })
    return if (phone.text.toString().isNotEmpty()) {
        phone.error == null
    } else false

}

fun radioGroupValidation(
    radioGroup: RadioGroup,
    variants: TextView,
    ready: Int,
    remarksEt: EditText
): Boolean {
    radioGroup.setOnCheckedChangeListener { group, checkedId ->
        run {
            if (radioGroup.checkedRadioButtonId != -1) {
                if (checkedId == ready) {
                    if (remarksEt.text.isNotEmpty()) {
                        variants.error = null
                        remarksEt.error = null
                    } else {
                        remarksEt.error = "Nie wpisano numeru"
                    }
                } else {
                    variants.error = null
                    remarksEt.error = null
                }

            } else {
                variants.error = "Nic nie zostało zaznaczone"
            }
        }
    }
    return variants.error == null
}