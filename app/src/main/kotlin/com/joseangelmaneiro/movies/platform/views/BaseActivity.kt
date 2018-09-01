package com.joseangelmaneiro.movies.platform.views

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AlertDialog
import com.joseangelmaneiro.movies.R
import com.joseangelmaneiro.movies.ui.BaseView

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
