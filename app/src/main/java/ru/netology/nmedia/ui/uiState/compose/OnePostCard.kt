package ru.netology.nmedia.ui.uiState.compose

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddComment
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.SyncStatus
import java.time.Clock


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OnePostCard(post: Post) {


    var isLike by remember {
        mutableStateOf(post.likedByMe)
    }

    var likes by remember {
        mutableStateOf(post.likes)
    }

    var comments by remember {
        mutableStateOf(12)
    }

    var views by remember {
        mutableStateOf(post.countViews)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            GlideImage(
                model = post.authorAvatar,
                contentDescription = "Аватарка пользователя",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f),
            ) {

                Text(text = post.author, modifier = Modifier.padding(bottom = 5.dp))
                Text(text = post.published.toString())

            }

            IconButton(
                onClick = { },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Меню"
                )
            }
        }

        Text(
            text = post.content,
            modifier = Modifier.padding(6.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick ={ },
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Поставить лайк",
                )
            }

            Text(
                text = "${likes}",
                modifier = Modifier.padding(end = 8.dp)
            )

            IconButton(
                onClick ={},
            ) {
                Icon(
                    imageVector = Icons.Default.AddComment,
                    contentDescription = "Написать комментарий"
                )
            }

            Text(
                text = "${comments}"
            )

            Row(
                modifier = Modifier.weight(1f)
            ){

            }


            IconButton(
                onClick = {},
            ) {
                Icon(
                    imageVector = Icons.Default.RemoveRedEye,
                    contentDescription = "Кол-во просмотров"
                )
            }

            Text(
                text = "${views}"
            )


        }


    }


}


@Preview(showBackground = true)
@Composable
fun OnePostCardPrewie() {
    OnePostCard(
        Post(
            author = "Михаил Стажер Тбанка",
            likes = 1487,
            published = kotlin.time.Clock.System.now(),
            countShare = 12,
            countViews = 1700,
            id = 12,
            authorAvatar = "",
            attachment = null,
            content = "Всем привет я устроился работать в Тбанк",
            likedByMe = false,
            video = null,
            syncStatus = SyncStatus.SYNCED,
        )
    )
}