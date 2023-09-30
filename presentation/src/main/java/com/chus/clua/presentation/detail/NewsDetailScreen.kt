package com.chus.clua.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.shareAsPlainText
import com.chus.clua.presentation.models.NewsUI


@Composable
fun NewsDetailScreenRoute(
    onBackClick: () -> Unit,
    navigateToWebView: (String) -> Unit
) {

    val viewModel: NewsDetailViewModel = hiltViewModel()
    val state by viewModel.newsFlow.collectAsStateWithLifecycle()

    NewsDetailScreen(
        state = state,
        onBackClick = onBackClick,
        navigateToWebView = navigateToWebView,
        toggleOnWatchList = viewModel::toggleOnWatchList
    )

}

@Composable
private fun NewsDetailScreen(
    state: DetailViewState,
    onBackClick: () -> Unit,
    navigateToWebView: (String) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit
) {
    val errorMessage = stringResource(id = R.string.error)
    val snackBarHostState = remember { SnackbarHostState() }

    if (state.error) {
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(errorMessage)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState),
        ) {

            Header(
                news = state.news,
                onBackClick = onBackClick
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Content(
                news = state.news,
                toggleOnWatchList = toggleOnWatchList,
                navigateToWebView = navigateToWebView
            )

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun Header(
    news: NewsUI?,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        GlideImage(
            model = news?.imageUrl.orEmpty(),
            contentDescription = "NewsImage",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Crop,
        )
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun Content(
    news: NewsUI?,
    toggleOnWatchList: (NewsUI) -> Unit,
    navigateToWebView: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "${news?.source.orEmpty()} - ${news?.publishedAt}",
                modifier = Modifier.weight(1F),
                style = MaterialTheme.typography.titleSmall,
            )
            val context = LocalContext.current
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier
                    .clickable { context.shareAsPlainText(news?.title, news?.url.orEmpty()) },
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(horizontal = 4.dp))
            val iconId =
                if (news?.isOnWatchList == true) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outlined
            Icon(
                imageVector = ImageVector.vectorResource(id = iconId),
                contentDescription = "BookMark",
                modifier = Modifier
                    .clickable { news?.let { toggleOnWatchList(it) } },
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Text(
            text = news?.title.orEmpty(),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = news?.description.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(top = 24.dp))

        IconButton(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .align(Alignment.CenterHorizontally),
            onClick = { navigateToWebView(news?.url.orEmpty()) }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Navigate to webView",
                tint = Color.White
            )
        }
    }

}

@Preview
@Composable
private fun PreviewNewsDetailScreen() {
    NewsDetailScreen(
        state = DetailViewState(news = NewsUI),
        onBackClick = { },
        navigateToWebView = { },
        toggleOnWatchList = {}
    )
}

private val NewsUI = NewsUI(
    title = "Everything announced at today's Apple event: iPhone 15, USB-C, Apple Watch Series 9 and more",
    description = "Apple's 2023 iPhone event came and went almost in the blink of an eye. As always, the company had a bunch of new devices to show off during the \\\"Wonderlust\\\" showcase but thanks to long-standing rumors, there weren't too many major surprises. On the phone fron…",
    url = "\"https://lifehacker.com/the-most-interesting-iphone-15-features-most-people-mis-1850835003\"",
    imageUrl = "\"https://s.yimg.com/os/creatr-uploaded-images/2023-09/de570cb0-51a3-11ee-bb5f-bc58227716e7",
    source = "Business Insider",
    publishedAt = "09-13-2023"
)
