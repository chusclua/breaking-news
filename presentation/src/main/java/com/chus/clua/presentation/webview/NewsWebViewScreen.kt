package com.chus.clua.presentation.webview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun NewsWebViewScreen(
    url: String?,
    onCloseClick: () -> Unit
) {

    Column {
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = onCloseClick
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        val webViewState = rememberWebViewState(url = url.orEmpty())
        WebView(
            modifier = Modifier
                .fillMaxSize(),
            state = webViewState,
            captureBackPresses = false,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
        )
    }

}

@Preview
@Composable
private fun PreviewNewsWebViewScreen() {
    NewsWebViewScreen(url = "", onCloseClick = {})
}