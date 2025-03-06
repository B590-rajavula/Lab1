package iu.b590.spring2025.midtermsection5.ui

import android.icu.text.NumberFormat
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import iu.b590.spring2025.midtermsection5.model.Pizza
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue


@Preview
@Composable
fun PizzaBuilderScreen(
    modifier: Modifier = Modifier
) {
    var pizza by rememberSaveable { mutableStateOf(Pizza()) }
    Column(
        modifier = modifier
    ) {
        ToppingsList(
            pizza = pizza,
            onEditPizza = {pizza = it},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
        )
        OrderButton(
            pizza = pizza,
            modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        )
    }
}

//private var pizza =
//    Pizza(
//        toppings = mapOf(
//            Topping.Pepperoni to ToppingPlacement.All,
//            Topping.Pineapple to ToppingPlacement.All
//        )
//    )
//    set(value) {
//        Log.d("PizzaBuilderScreen", "Reassigned pizza to $value")
//        field = value
//    }

//private var pizza by mutableStateOf(Pizza())


@Composable
private fun ToppingsList(
    pizza: Pizza,
    onEditPizza: (Pizza) -> Unit,
    modifier: Modifier = Modifier
) {
//    ToppingCell(
//        topping = Topping.Pepperoni,
//        placement = ToppingPlacement.Left,
//        onClickTopping = {},
//        modifier = modifier
//    )
//    var pizza by remember { mutableStateOf(Pizza()) }

//    var showToppingPlacementDialog by rememberSaveable { mutableStateOf(false) }
var toppingBeingAdded by rememberSaveable { mutableStateOf<Topping?>(null) }
//    if(showToppingPlacementDialog) {
    toppingBeingAdded?.let{topping ->
        ToppingPlacementDialog (
            topping = topping,
            onSetToppingPlacement = {placement ->
                onEditPizza(pizza.withTopping(topping, placement))
            },
            onDismissRequest = {
//                showToppingPlacementDialog = false
                toppingBeingAdded = null
            }
        )
    }

    LazyColumn (
        modifier = modifier
    ){
        items (Topping.values()) { topping ->
            ToppingCell(
                topping = topping,
//                placement = ToppingPlacement.Left,
                placement = pizza.toppings[topping],
                onClickTopping = {
//                    val isOnPizza = pizza.toppings[topping] != null
//                    pizza =
//                        onEditPizza(pizza.withTopping(
//                        topping = topping,
//                        placement = if (isOnPizza) {
//                            null
//                        } else {
//                            ToppingPlacement.All
//                        }
//                    ))
//                    showToppingPlacementDialog = true
                    toppingBeingAdded = topping
                }
            )
        }
    }
}

@Composable
private fun OrderButton(
    pizza: Pizza,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {

        }
    ) {
        val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
        val price = currencyFormatter.format(pizza.price)
        Text(
            text = stringResource(R.string.place_order_button, price)
                .uppercase()
        )
    }
}