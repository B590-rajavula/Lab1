package iu.b590.spring2025.midtermsection5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import iu.b590.spring2025.midtermsection5.model.Topping
import iu.b590.spring2025.midtermsection5.model.ToppingPlacement
import iu.b590.spring2025.midtermsection5.ui.AppTheme
import iu.b590.spring2025.midtermsection5.ui.PizzaBuilderScreen
import iu.b590.spring2025.midtermsection5.ui.ToppingCell


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ToppingCell(
//                topping = Topping.Pepperoni,
//                placement = ToppingPlacement.Left,
//                onClickTopping = {}
//            )
            AppTheme {
                PizzaBuilderScreen()
            }
        }
    }
}

