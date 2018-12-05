package ru.otus

import ru.otus.models.Answer

/* вопросом помечаем, что может прийти null */
fun someFunction(someNullableParam:Answer?) {
    if(someNullableParam != null) {
        // smart cast. Компилятор видит, что передаваемое
        // значение не null и разрешает его передать в функцию
        anotherFunction(someNullableParam)
    }
}

/* здесь же уже null не пройдет, в попытке передать
 * null или nullable значение компилятор выдаст ошибку */
fun anotherFunction(someParam:Answer) {
    // делаем что-то без опаски, что переданное значение null
}

interface Vector2 {
    val x:Float // это не поле, а ридонли свойство (property)
    val y:Float // в Java были бы методы getX() и getY()
}

fun main(args: Array<String>) {
    val scope = "world"
    println("Hello, $scope!")

    val answer = Answer()
    // answer.answer = "Ok"
    answer.score = 13

    println("answer = ${answer}")
}