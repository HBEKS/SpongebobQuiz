package com.ubayadev.spongebobquiz

//singleton class
object QuestionData {
    var questions = arrayOf(
        Question("Krusty Krab is the favorite burger in Bikini Bottom", false, R.drawable.mrkrab),
        Question("Spongebob lives in a pineapple under the sea", true , R.drawable.sponge),
        Question("Karen is Plankton’s wife", true, R.drawable.karen),
        Question("Mermaid Man was once a superhero of Bikini Bottom", true, R.drawable.mermaid),
        Question("Squidward has four hands", false, R.drawable.squid)
    ) //berisi objek
}