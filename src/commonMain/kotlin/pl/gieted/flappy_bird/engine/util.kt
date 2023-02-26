package pl.gieted.flappy_bird.engine

fun <T : Comparable<T>> limit(value: T, min: T? = null, max: T? = null) = when {
    max != null && value > max -> max
    min != null && value < min -> min
    else -> value
}

fun Image.scale(value: Double) {
    resize((width * value).toInt(), (height * value).toInt())
}

fun rgb(r: Int, g: Int, b: Int) = Color(r, g, b)

