package iu.b590.spring2025.midtermsection5.model

import androidx.annotation.StringRes
import iu.b590.spring2025.midtermsection5.R


enum class Topping(
    @StringRes val toppingName: Int
) {

    Basil (
    toppingName = R.string.topping_basil
    ),
    Mushroom (
    toppingName = R.string.topping_mushroom
    ),
    Olive (
    toppingName = R.string.topping_olive
    ),
    Peppers(
    toppingName = R.string.topping_peppers
    ),
    Pepperoni (
    toppingName = R.string.topping_pepperoni
    ),
    Pineapple(
    toppingName = R.string.topping_pineapple
    )
}