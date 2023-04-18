package com.basic.broker

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.basic.broker.components.MainScreen
import com.basic.broker.model.Stock
import com.basic.broker.ui.theme.BrokerTheme
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

private val months = listOf(
    "Ene",
    "Feb",
    "Mar",
    "Abr",
    "May",
    "Jun",
    "Jul",
    "Ago",
    "Sep",
    "Oct",
    "Nov",
    "Dic"
)

private val stock = listOf(
    Stock(
        company = "Dribbble",
        icon = R.drawable.dribbble,
        color = Color.Magenta,
        price = 66.43,
        date = System.currentTimeMillis()
    ),
    Stock(
        company = "Skype",
        icon = R.drawable.skype,
        color = Color.Blue,
        price = -32.30,
        date = System.currentTimeMillis()
    ),
    Stock(
        company = "YouTube",
        icon = R.drawable.youtube,
        color = Color.Red,
        price = -12.00,
        date = System.currentTimeMillis()
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val points = parse(this)
        val randomStartPoint = points.shuffled().first()

        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.ROOT)
        val date = dateFormat.format(time).capitalize(Locale.ROOT)

        setContent {
            BrokerTheme {
                MainScreen(
                    randomStartPoint = randomStartPoint,
                    points = points,
                    months = months,
                    stockList = stock,
                    date = date
                )
            }
        }
    }
}

private fun parse(context: Context): List<Offset> {
    val json = context.assets.open("coordenadas.json").bufferedReader().use{it.readLine()}
    val array = JSONArray(json)
    val points = mutableListOf<Offset>()

    for (i in 0 until array.length()) {
        val coordinate = array.optJSONArray(i)
        val x = coordinate.optDouble(0).toFloat()
        val y = coordinate.optDouble(1).toFloat()
        points += Offset(x, y)
    }
    return points
}