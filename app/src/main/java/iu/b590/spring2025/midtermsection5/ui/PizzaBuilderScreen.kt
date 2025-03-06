package iu.b590.spring2025.midtermsection5.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.tooling.preview.Preview
import iu.b590.spring2025.midtermsection5.R
import iu.b590.spring2025.midtermsection5.model.Topping
import iu.b590.spring2025.midtermsection5.model.ToppingPlacement
import androidx.compose.material3.Text
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        ToppingsList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )
        OrderButton(
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
    }
}



@Composable
private fun ToppingsList(
    modifier: Modifier = Modifier
) {
//    ToppingCell(
//        topping = Topping.Pepperoni,
//        placement = ToppingPlacement.Left,
//        onClickTopping = {},
//        modifier = modifier
//    )

    LazyColumn (
        modifier = modifier
    ){
        items (Topping.values()) { topping ->
            ToppingCell(
                topping = topping,
                placement = ToppingPlacement.Left,
                onClickTopping = {

                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {

        }
    ) {
        Text(
            text = stringResource(R.string.place_order_button)
                .uppercase()
        )
    }
}