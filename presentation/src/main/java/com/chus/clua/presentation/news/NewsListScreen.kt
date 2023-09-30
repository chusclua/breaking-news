package com.chus.clua.presentation.news

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.rememberLazyListState
import com.chus.clua.presentation.compose.shareAsPlainText
import com.chus.clua.presentation.models.Category
import com.chus.clua.presentation.models.NewsUI

@Composable
fun NewsListScreenRoute(
    onNewsClick: (String) -> Unit,
) {
    val viewModel: NewsViewModel = hiltViewModel()
    val news = viewModel.newsFlow.collectAsLazyPagingItems()
    val viewState by viewModel.viewStateFlow.collectAsStateWithLifecycle()

    NewsListScreen(
        bottomBarIndex = viewState.bottomBarIndex,
        selectedCategory = viewState.selectedCategory,
        news = news,
        watchList = viewState.watchList,
        categories = viewState.categories,
        onNewsClick = onNewsClick,
        onCategoryClick = viewModel::onCategoryClick,
        toggleOnWatchList = viewModel::toggleOnWatchList,
        onClearAll = viewModel::clearAllWatchList,
        onIndexSelected = viewModel::onIndexSelected
    )
}

private const val LIST = 0
private const val watch_LIST = 1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsListScreen(
    bottomBarIndex: Int,
    selectedCategory: Category,
    news: LazyPagingItems<NewsUI>,
    watchList: List<NewsUI>,
    categories: List<Category>,
    onNewsClick: (String) -> Unit,
    onCategoryClick: (Category) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit,
    onClearAll: () -> Unit,
    onIndexSelected: (Int) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text(
                        text = stringResource(id = if (bottomBarIndex == LIST) R.string.app_name else R.string.watch_list),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    if (bottomBarIndex == watch_LIST) {
                        IconButton(onClick = onClearAll) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                repeat(2) { index ->
                    NavigationBarItem(
                        selected = bottomBarIndex == index,
                        onClick = { onIndexSelected(index) },
                        icon = {
                            Icon(
                                if (index == 0) Icons.Filled.List else ImageVector.vectorResource(id = R.drawable.ic_bookmark_filled),
                                "",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }

            }
        }
    ) { innerPadding ->

        val listState = news.rememberLazyListState()
        val categoryListState = rememberLazyListState()
        val watchListState = rememberLazyListState()

        LaunchedEffect(selectedCategory) {
            listState.animateScrollToItem(0)
        }

        if (bottomBarIndex == LIST) {
            NewsList(
                news = news,
                categories = categories,
                selectedCategory = selectedCategory,
                onNewsClick = onNewsClick,
                onCategoryClick = onCategoryClick,
                toggleOnWatchList = toggleOnWatchList,
                innerPadding = innerPadding,
                newsState = listState,
                categoriesState = categoryListState
            )
        } else {
            WatchList(
                news = watchList,
                onNewsClick = onNewsClick,
                toggleOnWatchList = toggleOnWatchList,
                innerPadding = innerPadding,
                lazyState = watchListState
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsList(
    news: LazyPagingItems<NewsUI>,
    categories: List<Category>,
    selectedCategory: Category,
    onNewsClick: (String) -> Unit,
    onCategoryClick: (Category) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit,
    innerPadding: PaddingValues,
    newsState: LazyListState,
    categoriesState: LazyListState
) {

    Column(
        modifier = Modifier
            .padding(innerPadding)
    ) {
        LazyRow(
            state = categoriesState,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            itemsIndexed(items = categories) { index, item ->
                FilterChip(
                    onClick = { onCategoryClick(item) },
                    label = {
                        Text(stringResource(id = item.nameResource))
                    },
                    selected = selectedCategory.position == index,
                    leadingIcon = if (selectedCategory.position == index) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Filter",
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }

        if (news.loadState.refresh is LoadState.Loading) {
            Loading()
        } else {
            val context = LocalContext.current
            LazyColumn(state = newsState) {
                items(news.itemCount) { index ->
                    news[index]?.let { item ->
                        if (index % 3 == 0 && index != 0) {
                            SmallItemNews(news = item, context, onNewsClick, toggleOnWatchList)
                        } else {
                            ItemNews(news = item, context, onNewsClick, toggleOnWatchList)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WatchList(
    news: List<NewsUI>,
    onNewsClick: (String) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit,
    innerPadding: PaddingValues,
    lazyState: LazyListState
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (news.isEmpty()) {
            EmptywatchList()
        } else {
            LazyColumn(state = lazyState) {
                items(news) { item ->
                    ItemNews(news = item, context, onNewsClick, toggleOnWatchList)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ItemNews(
    news: NewsUI,
    context: Context,
    onNewsClick: (String) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier
            .padding(16.dp)
            .clickable { onNewsClick(news.url) }
        ) {
            GlideImage(
                model = news.imageUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "NewsImage",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(10.dp)),
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = news.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${news.source.orEmpty()} - ${news.publishedAt}",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleSmall,
                )
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .clickable { context.shareAsPlainText(news.title, news.url) },
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                val iconId =
                    if (news.isOnWatchList) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outlined
                Icon(
                    imageVector = ImageVector.vectorResource(id = iconId),
                    contentDescription = "BookMark",
                    modifier = Modifier
                        .clickable { toggleOnWatchList(news) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
        Divider(color = MaterialTheme.colorScheme.primary)
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SmallItemNews(
    news: NewsUI,
    context: Context,
    onNewsClick: (String) -> Unit,
    toggleOnWatchList: (NewsUI) -> Unit
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Column(modifier = Modifier
            .padding(16.dp)
            .clickable { onNewsClick(news.url) }
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = news.title,
                    modifier = Modifier.weight(1F),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 5,
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.padding(start = 16.dp))

                GlideImage(
                    model = news.imageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "BookMark",
                    modifier = Modifier
                        .size(width = 100.dp, height = 60.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
            }

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "${news.source.orEmpty()} - ${news.publishedAt}",
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleSmall,
                )
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .clickable { context.shareAsPlainText(news.title, news.url) },
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                val iconId =
                    if (news.isOnWatchList) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_outlined
                Icon(
                    imageVector = ImageVector.vectorResource(id = iconId),
                    contentDescription = "BookMark",
                    modifier = Modifier
                        .clickable { toggleOnWatchList(news) },
                    tint = MaterialTheme.colorScheme.primary
                )
            }

        }
        Divider(color = MaterialTheme.colorScheme.primary)
    }

}

@Composable
private fun EmptywatchList() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark_filled),
            contentDescription = "BookMark",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = stringResource(id = R.string.empty_watchlist_message),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(id = R.string.loading),
            color = MaterialTheme.colorScheme.primary
        )
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Preview
@Composable
private fun PreviewwatchList() {
    WatchList(
        news = listOf(NewsItemList, NewsItemList),
        onNewsClick = {},
        toggleOnWatchList = {},
        innerPadding = PaddingValues(),
        lazyState = LazyListState()
    )
}

@Preview
@Composable
private fun PreviewItem() {
    ItemNews(
        news = NewsItemList,
        context = LocalContext.current,
        onNewsClick = {},
        toggleOnWatchList = {}
    )
}

@Preview
@Composable
private fun PreviewItemOdd() {
    SmallItemNews(
        news = NewsItemList,
        context = LocalContext.current,
        onNewsClick = {},
        toggleOnWatchList = {}
    )
}

@Preview
@Composable
private fun PreviewLoading() {
    Loading()
}

private val NewsItemList = NewsUI(
    title = "Everything announced at today's Apple event: iPhone 15, USB-C, Apple Watch Series 9 and more",
    description = "Apple's 2023 iPhone event came and went almost in the blink of an eye. As always, the company had a bunch of new devices to show off during the \\\"Wonderlust\\\" showcase but thanks to long-standing rumors, there weren't too many major surprises. On the phone fron…",
    imageUrl = "\"https://s.yimg.com/os/creatr-uploaded-images/2023-09/de570cb0-51a3-11ee-bb5f-bc58227716e7",
    url = "\"https://lifehacker.com/the-most-interesting-iphone-15-features-most-people-mis-1850835003\"",
    source = "Business Insider",
    publishedAt = "09-13-2023"
)