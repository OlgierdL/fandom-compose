package org.example.project.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.project.data.model.Article

@Composable
fun TitlesList(articles: List<Article>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(articles, key = { it.id }) { item ->
            ListItem(
                headlineContent = { Text(item.title) },
                supportingContent = { Text(item.communityName) }
            )
            HorizontalDivider()
        }
    }
}