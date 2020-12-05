package io.minoro75.miniweatherapp.utils

import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment

fun Fragment.checkSelfPermissionFragment(permission: String) =
    context?.let {
        PermissionChecker.checkSelfPermission(it, permission)
    }


fun Fragment.shouldShowRequestPermissionRationaleFragment(permission: String) =
    context?.let {
        shouldShowRequestPermissionRationale(permission)
    }

fun Fragment.requestPermissionsFragment(permissions: Array<String>, requestCode: Int) {
    context?.let {
        requestPermissions(permissions, requestCode)
    }
}

