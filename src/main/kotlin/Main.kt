// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Cell.ALIVE
import Cell.DEAD
import ViewModel.Companion.GRID_SIZE
import ViewModel.Companion.GRID_WIDTH
import ViewModel.Companion.LIFE_GENERATOR
import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

private val CELL_SIZE_DP = (GRID_WIDTH / GRID_SIZE).dp

fun initialGrid() =
    List(GRID_SIZE) {
        List(GRID_SIZE) {
            if (LIFE_GENERATOR.invoke()) ALIVE else DEAD
        }
    }

val viewModel = ViewModel()

@Composable
@Preview
fun App() {
    var buttonText by remember { mutableStateOf("Start") }

    val grid by viewModel.gameOfLife.collectAsState(emptyList())

    DesktopMaterialTheme {
        Column {
            Button(onClick = {
                buttonText = "Restart"
                viewModel.startTapped()
            }) {
                Text(buttonText)
            }
            GameGrid(grid)
        }
    }
}

@Composable
fun GameGrid(grid: List<List<Cell>>) {
    Column {
        grid.map { row ->
            Row {
                row.map { cell ->
                    Column {
                        Box(
                            modifier = Modifier
                                .size(CELL_SIZE_DP)
                                .clip(RectangleShape)
                                .background(if (cell == ALIVE) Color.Black else Color.White)
                        )
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}