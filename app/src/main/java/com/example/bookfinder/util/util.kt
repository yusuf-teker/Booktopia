package com.example.bookfinder.util

import com.example.bookfinder.model.Book
import com.example.bookfinder.model.ImageLinks
import com.example.bookfinder.model.SearchInfo
import com.example.bookfinder.model.VolumeInfo
import com.example.bookfinder.screens.common.BookItem
val harryPotter = createVolumeInfo("https://m.media-amazon.com/images/I/51mFoFmu0EL._SX335_BO1,204,203,200_.jpg","Harry Potter and the Chamber of Secrets")
val mathBook = createVolumeInfo("http://books.google.com/books/content?id=kYYJLhL2arwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
val mockItems =

    mutableListOf<Book>(
        Book("1","kind1", SearchInfo("searchInfo1"),null, mathBook, false),
        Book("2","kind2", SearchInfo("searchInfo2"),null, harryPotter,true),
        Book("3","kind3", SearchInfo("searchInfo3"),null, null),
        Book("4","kind4", SearchInfo("searchInfo4"),null, mathBook,true),
        Book("5","kind5", SearchInfo("searchInfo5"),null, harryPotter),
        Book("6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
        Book("1","kind1", SearchInfo("searchInfo1"),null, harryPotter,false),
        Book("2","kind2", SearchInfo("searchInfo2"),null, mathBook,true),
        Book("3","kind3", SearchInfo("searchInfo3"),null, mathBook),
        Book("4","kind4", SearchInfo("searchInfo4"),null, harryPotter,true),
        Book("5","kind5", SearchInfo("searchInfo5"),null, mathBook),
        Book("6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
    )

fun createVolumeInfo(link: String, title: String= "Tilte of the book"): VolumeInfo {
    return VolumeInfo(listOf("Name Surname","Name1 Surname1"),listOf("",""),"Mathematics plays a key role in computer science, some researchers would consider computers as nothing but the physical embodiment of mathematical systems. And whether you are designing a digital circuit, a computer program or a new programming language, you need mathematics to be able to reason about the design -- its correctness, robustness and dependability. This book covers the foundational mathematics necessary for courses in computer science. The common approach to presenting mathematical concepts and operators is to define them in terms of properties they satisfy, and then based on these definitions develop ways of computing the result of applying the operators and prove them correct. This book is mainly written for computer science students, so here the author takes a different approach: he starts by defining ways of calculating the results of applying the operators and then proves that they satisfy various properties. After justifying his underlying approach the author offers detailed chapters covering propositional logic, predicate calculus, sets, relations, discrete structures, structured types, numbers, and reasoning about programs. The book contains chapter and section summaries, detailed proofs and many end-of-section exercises -- key to the learning process. The book is suitable for undergraduate and graduate students, and although the treatment focuses on areas with frequent applications in computer science, the book is also suitable for students of mathematics and engineering.", ImageLinks("",link),"","",0,"","","","",title)
}

