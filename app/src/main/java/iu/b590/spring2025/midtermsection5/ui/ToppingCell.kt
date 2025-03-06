package iu.b590.spring2025.midtermsection5.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import iu.b590.spring2025.midtermsection5.model.Topping
import iu.b590.spring2025.midtermsection5.model.ToppingPlacement

@Preview
@Composable
private fun ToppingCellPreviewNotOnPizza(){
    ToppingCell(
        topping = Topping.Pepperoni,
        placement = null,
        onClickTopping = {}
    )
}

@Preview
@Composable
private fun ToppingCellPreviewOnLeftHalf(){
    ToppingCell(
        topping = Topping.Pepperoni,
        placement = ToppingPlacement.Left,
        onClickTopping = {}
    )
}

@Composable
fun ToppingCell(
    topping : Topping,
    placement: ToppingPlacement?,
    modifier: Modifier = Modifier,
    onClickTopping: () -> Unit
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClickTopping() }
            .padding(vertical = 4.dp, horizontal = 16.dp)

    ){
        Checkbox(
//            checked = true,
            checked = (placement != null),
            onCheckedChange = {onClickTopping()}
        )

        Column (
            modifier = Modifier.weight(1f, fill = true)
                .padding(start = 4.dp)
        ){
            Text(
//                text = "Pineapple"
                text = stringResource(topping.toppingName),
                style = MaterialTheme.typography.bodyMedium
            )
            if(placement != null){
                Text(
//                    text = "Whole pizza"
                    text = stringResource(placement.label),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}