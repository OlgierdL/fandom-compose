package org.example.project.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.components.ImagesList
import org.example.project.ui.components.TitlesList
import org.example.project.ui.viewmodel.ArticlesViewModel
import org.example.project.ui.viewmodel.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: ArticlesViewModel = ArticlesViewModel() ) {
    val state by viewModel.uiState.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Titles", "Images")
    val currentTitle = if (selectedTabIndex == 0) {tabTitles[0]} else {tabTitles[1]}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentTitle) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Titles") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Images") }
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (val currentState = state) {
                    is UIState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is UIState.Error -> {
                        Text(
                            text = "Error: ${currentState.message}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    is UIState.Success -> {
                        when (selectedTabIndex) {
                            0 -> TitlesList(
                                articles = currentState.articles,
                                modifier = Modifier.padding(16.dp)
                            )
                            1 -> ImagesList(
                                articles = currentState.articles.filter{ it.imageUrl != null },
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}