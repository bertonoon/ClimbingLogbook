package com.bf.climbinglogbook.other

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object Permissions {

    fun hasReadImagePermission(context: Context) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            EasyPermissions.hasPermissions(context, Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            EasyPermissions.hasPermissions(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    fun hasLocationPermission(context: Context) = EasyPermissions.hasPermissions(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


}