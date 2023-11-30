package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tictactoe.ui.theme.TicTacToeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicTacToeGame()
                }
            }
        }
    }
}

@Composable
fun TicTacToeGame() {
    var squares by remember { mutableStateOf(List(9) { "" }) }
    var isPlayerXTurn by remember { mutableStateOf(true) }
    var titleText by remember { mutableStateOf("") }

    fun resetGame() {
        squares = List(9) { "" }
        isPlayerXTurn = true
        titleText = ""
    }


    fun checkWin(): Boolean {
        // Implement the logic to check if a player has won
        val winningCombos = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Rows
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columns
            listOf(0, 4, 8), listOf(2, 4, 6) // Diagonals
        )

        for (combo in winningCombos) {
            val symbols = combo.map { squares[it] }
            if (symbols.all { it == "X" } || symbols.all { it == "O" }) {
                return true // Found a winning combination
            }
        }
        return false // No winning combination found
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = if (titleText.isNotEmpty()) titleText else if (isPlayerXTurn) "Player X's Turn" else "Player O's Turn",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        )

        LazyVerticalGrid( columns = GridCells.Fixed(3),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            items((0..8).toList()) { index ->
                Button(
                    onClick = {
                        if (squares[index].isEmpty()) {
                            squares = squares.toMutableList().also {
                                it[index] = if (isPlayerXTurn) "X" else "O"
                            }
                            if (checkWin()) {
                                titleText = "Player ${if (isPlayerXTurn) "X" else "O"} wins!"
                            } else {
                                isPlayerXTurn = !isPlayerXTurn
                            }
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray)
                        .padding(4.dp),
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = squares[index],
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Button(
            onClick = { resetGame() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Reset Game")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTicTacToeGame() {
    TicTacToeTheme {
        TicTacToeGame()
    }
}
