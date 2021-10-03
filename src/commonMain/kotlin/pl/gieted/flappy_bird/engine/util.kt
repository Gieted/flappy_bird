package pl.gieted.flappy_bird.engine

fun <T : Comparable<T>> limit(value: T, lowerBound: T? = null, upperBound: T? = null) = when {
    upperBound != null && value > upperBound -> upperBound
    lowerBound != null && value < lowerBound -> lowerBound
    else -> value
}

fun Image.scale(value: Double) {
    resize((width * value).toInt(), (height * value).toInt())
}

fun rgb(r: Int, g: Int, b: Int) = Color(r, g, b)

