package com.raj.notes.utils

import android.content.Context
import android.widget.Toast

class AppUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}