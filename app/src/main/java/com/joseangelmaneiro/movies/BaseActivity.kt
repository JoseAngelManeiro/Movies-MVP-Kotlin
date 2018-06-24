package com.joseangelmaneiro.movies

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun showErrorMessage() {
        val builder = AlertDialog.Builder(this,
                R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setMessage(getString(R.string.error_has_ocurred))
        builder.setPositiveButton(android.R.string.ok){ dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}
