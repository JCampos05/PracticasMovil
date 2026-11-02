package com.simposio.mini_proyecto_3.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.simposio.mini_proyecto_3.R
import com.simposio.mini_proyecto_3.components.DateSelector
import com.simposio.mini_proyecto_3.components.PhotoSelector
import com.simposio.mini_proyecto_3.data.Post
import com.simposio.mini_proyecto_3.util.PhotoUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    post: Post? = null,
    onSave: (title: String, description: String, photoUri: String?, date: Long) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current

    var title by rememberSaveable { mutableStateOf(post?.title ?: "") }
    var titleTouched by rememberSaveable { mutableStateOf(false) }
    var description by rememberSaveable { mutableStateOf(post?.description ?: "") }
    var date by rememberSaveable { mutableStateOf(post?.date ?: System.currentTimeMillis()) }
    var photoUri by rememberSaveable { mutableStateOf(post?.photoUri) }

    fun cancel() {
        photoUri?.takeIf { it != post?.photoUri }?.let { PhotoUtils.delete(context, it.toUri()) }
        onCancel()
    }

    fun save() {
        post?.photoUri?.takeIf { it != photoUri }?.let { PhotoUtils.delete(context, it.toUri()) }
        onSave(title.trim(), description.trim(), photoUri, date)
    }

    BackHandler { cancel() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(if (post == null) R.string.new_post else R.string.edit_post)) },
                navigationIcon = {
                    IconButton(onClick = { cancel() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    titleTouched = true
                },
                label = { Text(stringResource(R.string.field_title)) },
                singleLine = true,
                isError = titleTouched && title.isBlank(),
                supportingText = {
                    if (titleTouched && title.isBlank()) Text(stringResource(R.string.field_title_required))
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.field_description)) },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            DateSelector(date = date, onDateChange = { date = it })

            Spacer(modifier = Modifier.height(16.dp))

            PhotoSelector(photoUri = photoUri, onPhotoChange = { photoUri = it })

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { save() },
                enabled = title.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
}