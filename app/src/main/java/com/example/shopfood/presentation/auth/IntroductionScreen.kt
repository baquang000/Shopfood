package com.example.shopfood.presentation.auth

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopfood.R
import com.example.shopfood.presentation.component.ButtonCustom
import com.example.shopfood.presentation.component.ScaffoldWithNoSafeArea
import com.example.shopfood.presentation.component.TextCustom
import com.example.shopfood.ui.theme.ShopfoodTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroductionScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabContents.size })
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp


    ScaffoldWithNoSafeArea(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = screenHeight * 0.5f, max = screenHeight * 0.75f)
                ) { page ->
                    val currentContent = tabContents[page]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(screenHeight * 0.6f)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainerLowest
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = currentContent.imageRes),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(screenHeight * 0.5f)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(currentContent.title),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.W900
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = stringResource(currentContent.description),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.outline
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tabContents.forEachIndexed { index, _ ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(
                                    if (pagerState.currentPage == index)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                                )
                                .clickable {
                                    scope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                }
                        )
                    }
                }

                ButtonCustom(
                    modifier = Modifier,
                    text = if (pagerState.currentPage == 2) R.string.btn_get_started else R.string.btn_next,
                    onClick = {}
                )

                TextCustom(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    text = R.string.skip,
                )

            }
        }
    )
}


data class TabContent(
    val imageRes: Int,
    val title: Int,
    val description: Int
)

val tabContents = listOf(
    TabContent(
        imageRes = R.drawable.healthy_options_bro,
        title = R.string.title_introduction_1,
        description = R.string.description_introduction_1
    ),
    TabContent(
        imageRes = R.drawable.pizza_maker_pana,
        title = R.string.title_introduction_2,
        description = R.string.description_introduction_2
    ),
    TabContent(
        imageRes = R.drawable.take_away_pana,
        title = R.string.title_introduction_3,
        description = R.string.description_introduction_3
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun IntroductionScreenPreview() {
    ShopfoodTheme {
        IntroductionScreen()
    }
}