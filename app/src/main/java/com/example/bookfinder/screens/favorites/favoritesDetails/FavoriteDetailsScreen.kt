package com.example.bookfinder.screens.favorites.favoritesDetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.RoundedCornersTransformation
import com.example.bookfinder.R
import com.example.bookfinder.data.model.room.Note
import com.example.bookfinder.screens.common.CustomCheckboxGroup
import com.example.bookfinder.screens.common.FavoriteIcon
import com.example.bookfinder.ui.theme.BookFinderTheme
import com.example.bookfinder.ui.theme.Dimen.bottomNavigationHeight
import com.example.bookfinder.ui.theme.Dimen.circleIconPadding
import com.example.bookfinder.util.categoryColorsLongValue
import com.example.bookfinder.util.getCurrentDateAsString

@Composable
fun FavoriteDetailsScreen(
    bookId: String,
    viewModel: FavoriteDetailsViewModel,
    navController: NavController
) {
    FavoriteDetailItem(bookId, viewModel, navController)
}

@Composable
fun FavoriteDetailItem(
    bookId: String,
    viewModel: FavoriteDetailsViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    viewModel.getFavoriteBookById(bookId)
    viewModel.getNotesByBookId(bookId)
    val book = viewModel.book.collectAsState()
    val notes = viewModel.notes.collectAsState()
    val isFavorite = viewModel.isFavorite.collectAsState()
    val currentNote = remember {
        mutableStateOf<Note?>(null)
    }

    val showDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {

        fun onAddNoteButtonClicked() {
            showDialog.value = true
        }

        if (showDialog.value) {
            AddOrChangeNote(
                note = currentNote.value,
                bookId = bookId,
                onCancel = {
                    currentNote.value = null
                    showDialog.value = false
                },
                onNoteUpdated = {
                    viewModel.addNoteToBook(it)
                    currentNote.value = null
                    showDialog.value = false
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = bottomNavigationHeight)
            ) {
                // Top - Images + Icons
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context)
                                    .data(
                                        if (book.value?.thumbnail.isNullOrEmpty())
                                            R.drawable.placeholder
                                        else
                                            book.value?.thumbnail
                                    )
                                    .crossfade(true)
                                    .placeholder(
                                        R.drawable.placeholder
                                    ).transformations(RoundedCornersTransformation(1f))
                                    .build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .blur(7.dp)
                        )

                        Column( //iconlar ve image
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(Color.Black, CircleShape)
                                        .size(36.dp)
                                        .shadow(2.dp, CircleShape)

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_arrow_back),
                                        contentDescription = stringResource(R.string.back),
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable {
                                                navController.popBackStack()
                                            }
                                            .padding(circleIconPadding)
                                            .align(Alignment.Center),
                                        tint = Color.White
                                    )
                                }
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(context)
                                            .data(
                                                if (book.value?.thumbnail.isNullOrEmpty())
                                                    R.drawable.placeholder
                                                else
                                                    book.value?.thumbnail
                                            )
                                            .crossfade(true)
                                            .placeholder(R.drawable.placeholder)
                                            .transformations(RoundedCornersTransformation(1f))
                                            .size(Size.ORIGINAL)
                                            .build()
                                    ),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .fillMaxHeight(0.5f)
                                        .aspectRatio(1f)
                                        .width(IntrinsicSize.Min)
                                )
                                Box(
                                    modifier = Modifier
                                        .background(Color.Black, CircleShape)
                                        .size(36.dp)
                                        .shadow(2.dp, CircleShape)

                                ) {
                                    FavoriteIcon(
                                        isFavorite.value,
                                        modifier = Modifier
                                            .size(36.dp)
                                            .align(Alignment.Center),
                                        onClick = { isFavorite ->

                                            if (isFavorite) {
                                                viewModel.saveFavoriteBook(book = book.value)
                                            } else {
                                                viewModel.deleteFavoriteBookById(
                                                    book.value?.bookId ?: ""
                                                )
                                            }
                                            viewModel.setIsFavorite(isFavorite)
                                        }
                                    )

                                }
                            }

                            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                            Box(
                                //Kitap Başlığı ve Yazarı
                                Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight()
                                    .shadow(
                                        4.dp,
                                        RoundedCornerShape(16.dp),
                                        spotColor = Color.DarkGray
                                    )
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .padding(horizontal = 32.dp, vertical = 4.dp),
                            ) {
                                Column(
                                    modifier = Modifier.wrapContentSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = book.value?.title
                                            ?: stringResource(id = R.string.book_title_not_found),
                                        modifier = Modifier
                                            .wrapContentSize(),
                                        textAlign = TextAlign.Center,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 2,
                                        color = Color.Black
                                    )
                                    if (!book.value?.authors.isNullOrEmpty()) {
                                        Text(
                                            text = book.value?.authors?.get(0)
                                                ?: stringResource(id = R.string.book_author_not_found),
                                            color = Color.Black
                                        )
                                    }

                                }

                            }

                        }

                    }
                }

                //Checkboxes
                item {

                    val selectedBox by viewModel.selectedBox.collectAsState()

                    CustomCheckboxGroup(
                        selectedBox = selectedBox,
                        onBoxSelected = { index ->
                            viewModel.setSelectedBox(index)
                            viewModel.updateBook()
                        },
                        options = listOf(
                            stringResource(R.string.to_be_read),
                            stringResource(R.string.reading),
                            stringResource(R.string.read)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )


                }

                // Notes //Notlarım
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(R.string.my_notes),
                                fontFamily = FontFamily.Cursive,
                                fontSize = 32.sp,
                                color = MaterialTheme.colors.onSurface
                            )
                            IconButton(onClick = { onAddNoteButtonClicked() }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onSurface
                                )
                            }
                        }

                    }
                }
                // Notes //Notlarım
                items(notes.value.size, key = {
                    notes.value[it].noteId
                }) {
                    NoteItem(notes.value[it]){
                        currentNote.value = notes.value[it]
                        showDialog.value = true
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

            }

        }

    }

}


@Composable
fun NoteList(noteList: List<Note>) {
    if (noteList.isNullOrEmpty()) {
        "Henüz not girilmedi."
    } else {
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()) {
            items(noteList.size, key = {
                noteList[it].noteId
            }) {
                NoteItem(noteList.get(it)){

                }
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onNoteClicked: () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .background(
                color = Color(note.noteColor),
                shape = RoundedCornerShape(15.dp)
            )
            .padding(8.dp)
            .clickable {
                onNoteClicked()
            }

    ) {
        Text(
            text = note.noteText,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun AddOrChangeNote(
    bookId: String,
    note: Note? = null,
    onNoteUpdated: (note: Note) -> Unit,
    onCancel: () -> Unit
){

    val inputValue = remember { mutableStateOf(TextFieldValue(note?.noteText ?: "")) }
    val backgroundColor = remember {
        mutableStateOf(note?.noteColor?: 0xFFFF00FF)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onBackground,
                cursorColor = MaterialTheme.colors.onBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color(backgroundColor.value)
            ),
            maxLines = 10
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = { onCancel() }) {
                Text(text = "İPTAL", color = MaterialTheme.colors.onSurface)
            }
            TextButton(onClick = {
                if (note==null){
                    onNoteUpdated(Note(bookId = bookId, noteText = inputValue.value.text, noteTime = getCurrentDateAsString(), noteColor = backgroundColor.value ))
                }else{
                    onNoteUpdated(note.copy(noteText = inputValue.value.text, noteColor = backgroundColor.value))
                }
                inputValue.value =  TextFieldValue()
            }) {
                Text(text = "EKLE", color = MaterialTheme.colors.onSurface)
            }
        }

        var sliderValue by remember {
            mutableStateOf(findIndexOfCategoryColor(backgroundColor.value).toFloat())
        }
        Slider(value = sliderValue, colors = SliderDefaults.colors(
            thumbColor =    Color.White,
            activeTrackColor = Color.White,
            inactiveTrackColor = Color.White),
            valueRange = 0f..categoryColorsLongValue.size.toFloat()-1,
            onValueChange = {
                sliderValue = it
                backgroundColor.value = categoryColorsLongValue.get(it.toInt())
        })

    }
}
fun findIndexOfCategoryColor(value: Long): Int{
    return categoryColorsLongValue.indexOf(value)
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BookFinderTheme {
        NoteList(
            listOf(
                Note(1, "Note1", "time1time1time1time1time1time1time1time1time1", 0xFFFF00FF, "A"),
                Note(
                    1,
                    "Note2",
                    "time2time2time2time2time2time2time2time2time2time2time2time2time2time2time2",
                    0xFFFF00FF,
                    "A"
                )
            )
        )
    }
}
