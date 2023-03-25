package com.example.bookfinder.util

import com.example.bookfinder.model.Book
import com.example.bookfinder.model.ImageLinks
import com.example.bookfinder.model.SearchInfo
import com.example.bookfinder.model.VolumeInfo
import com.example.bookfinder.screens.common.BookItem
val harryPotter = createVolumeInfo("https://m.media-amazon.com/images/I/51mFoFmu0EL._SX335_BO1,204,203,200_.jpg")
val mathBook = createVolumeInfo("http://books.google.com/books/content?id=kYYJLhL2arwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
val mockItems =

    mutableListOf<Book>(
        Book("","1","kind1", SearchInfo("searchInfo1"),null, mathBook, false),
        Book("","2","kind2", SearchInfo("searchInfo2"),null, harryPotter,true),
        Book("","3","kind3", SearchInfo("searchInfo3"),null, mathBook),
        Book("","4","kind4", SearchInfo("searchInfo4"),null, mathBook,true),
        Book("","5","kind5", SearchInfo("searchInfo5"),null, harryPotter),
        Book("","6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
        Book("","1","kind1", SearchInfo("searchInfo1"),null, harryPotter,false),
        Book("","2","kind2", SearchInfo("searchInfo2"),null, mathBook,true),
        Book("","3","kind3", SearchInfo("searchInfo3"),null, mathBook),
        Book("","4","kind4", SearchInfo("searchInfo4"),null, harryPotter,true),
        Book("","5","kind5", SearchInfo("searchInfo5"),null, mathBook),
        Book("","6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
    )

fun createVolumeInfo(link: String): VolumeInfo {
    return VolumeInfo(listOf("",""),listOf("",""),"Description of the Book Description of the Book Description of the Book Description of the Book Description of the Book Description of the Book Description of the Book Description of the Book", ImageLinks("",link),"","",0,"","","","","Title of the Book")
}

