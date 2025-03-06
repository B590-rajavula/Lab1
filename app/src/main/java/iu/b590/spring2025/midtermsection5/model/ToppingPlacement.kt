package iu.b590.spring2025.midtermsection5.model


import androidx.annotation.StringRes
import iu.b590.spring2025.midtermsection5.R

enum class ToppingPlacement (
@StringRes val label: Int
) {

    Left(R.string.placement_left),
    Right(R.string.placement_right),
    All(R.string.placement_all)
}