package com.simposio.mini_proyecto_3.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.simposio.mini_proyecto_3.R
import kotlinx.coroutines.launch


@Composable
fun CreateMainScreen(viewModel: PostViewModel = viewModel()) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val posts by viewModel.posts.collectAsState()

    var showForm by rememberSaveable { mutableStateOf(false) }
    var selectedId by rememberSaveable { mutableStateOf<Int?>(null) }
    val selected = posts.find { it.id == selectedId }

    fun notify(messageId: Int) {
        coroutineScope.launch { snackbarHostState.showSnackbar(context.getString(messageId)) }
    }

    if (showForm) {
        FormScreen(
            post = selected,
            onSave = { title, description, photoUri, date ->
                if (selected == null) {
                    viewModel.save(title, description, photoUri, date)
                    notify(R.string.msg_saved)
                } else {
                    viewModel.update(
                        selected.copy(
                            title = title,
                            description = description,
                            photoUri = photoUri,
                            date = date
                        )
                    )
                    notify(R.string.msg_updated)
                }
                selectedId = null
                showForm = false
            },
            onCancel = { showForm = false }
        )
    } else {
        ListScreen(
            posts = posts,
            snackbarHostState = snackbarHostState,
            onOpen = { selectedId = it.id },
            onNew = { showForm = true }
        )

        if (selected != null) {
            DetailDialog(
                post = selected,
                onEdit = { showForm = true },
                onDelete = {
                    viewModel.delete(selected)
                    selectedId = null
                    notify(R.string.msg_deleted)
                },
                onDismiss = { selectedId = null }
            )
        }
    }
}
