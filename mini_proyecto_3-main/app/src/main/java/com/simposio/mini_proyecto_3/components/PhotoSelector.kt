package com.simposio.mini_proyecto_3.components

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.simposio.mini_proyecto_3.R
import com.simposio.mini_proyecto_3.util.PhotoUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PhotoSelector(
    photoUri: String?,
    onPhotoChange: (String?) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var cameraUri by rememberSaveable { mutableStateOf<String?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        val uri = cameraUri
        if (uri != null) {
            if (success) onPhotoChange(uri) else PhotoUtils.delete(context, uri.toUri())
        }
        cameraUri = null
    }

    fun openCamera() {
        val uri = PhotoUtils.createCameraUri(context)
        cameraUri = uri.toString()
        cameraLauncher.launch(uri)
    }

    val cameraLauncherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { success ->
        if (success) {
            openCamera()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.camera_denied),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            coroutineScope.launch {
                val copy = withContext(Dispatchers.IO) { PhotoUtils.copyFromGallery(context, uri) }

                if (copy != null) {
                    onPhotoChange(copy.toString())
                } else {
                    Toast.makeText(context, context.getString(R.string.image_load_error), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if (photoUri != null) {
        AsyncImage(
            model = photoUri,
            contentDescription = stringResource(R.string.photo_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {
                val hasPermission = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) openCamera()
                else cameraLauncherPermission.launch(Manifest.permission.CAMERA)
            }
        ) {
            Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text(stringResource(R.string.photo_take))
        }

        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            Icon(Icons.Default.PhotoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text(stringResource(R.string.photo_gallery))
        }
    }
}