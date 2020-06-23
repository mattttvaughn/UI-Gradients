package io.github.mattpvaughn.uigradients.util

/**
 * Creates a [FloatArray] containing [steps] number of values equally distributed between [this] to
 * [endValue].
 *
 * Assumption: [this] <= [endValue]
 *
 * examples:
 *    0.rangeTo(endFloat = 1, steps = 2) == [0, 1]
 *    0.rangeTo(1, 3) = [0, 0.5, 1]
 */
fun Float.rangeTo(endValue: Float, steps: Int): FloatArray {
    require(this < endValue)
    val step: Float = (endValue - this) / (steps - 1)
    val list = mutableListOf<Float>()
    for (x in 0 until steps) {
        list.add(x * step)
    }
    return list.toFloatArray()
}
