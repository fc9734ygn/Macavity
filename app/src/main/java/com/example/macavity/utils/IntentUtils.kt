package com.example.macavity.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.example.macavity.ui.base.BaseActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


fun sendEmail(context: Context, emailAddress: String, subject: String?, body: String?) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/html"
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
    intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    intent.putExtra(Intent.EXTRA_TEXT, body)

    context.startActivity(Intent.createChooser(intent, "Send Email"))
}

fun callPhoneNumber(activity: Activity, phoneNumber: String) {
    Dexter.withActivity(activity)
        .withPermission(Manifest.permission.CALL_PHONE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                if (activity.checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:${phoneNumber}")
                    activity.startActivity(intent)
                }
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                //do nothing
            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) {
                token?.continuePermissionRequest()
            }
        }).check()
}