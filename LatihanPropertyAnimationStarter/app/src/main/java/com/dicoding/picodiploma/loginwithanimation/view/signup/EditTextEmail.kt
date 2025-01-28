package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class EditTextEmail @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {

    init {
        addTextChangedListener(object : TextWatcher {
            // SEBELUM DITULIS
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            // KETIKA DITULIS
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                    error = null
                } else {
                    setError("Email tidak ditulis dengan benar", null)
                }
            }

            // KETIKA SUDAH DITULIS
            override fun afterTextChanged(p0: Editable?) {}

        })
    }
}