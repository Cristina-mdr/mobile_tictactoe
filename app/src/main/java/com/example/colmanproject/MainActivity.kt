package com.example.colmanproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var board: Array<Array<Button>>
    private lateinit var playerTurnTextView: TextView
    private lateinit var winnerMsgTextView: TextView
    private lateinit var resetButton: Button

    private var currentPlayer = "X"
    private var movesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerTurnTextView = findViewById<TextView>(R.id.playerTurn)
        playerTurnTextView.text = "התור של $currentPlayer"

        winnerMsgTextView = findViewById<TextView>(R.id.winnerMsg)
        resetButton = findViewById<Button>(R.id.resetGame)

        resetButton.isEnabled = false
        resetButton.setOnClickListener { resetBoard() }

        board = Array(3) { row ->
            Array(3) { col ->
                val buttonId = resources.getIdentifier("button_$row$col", "id", packageName)
                findViewById<Button>(buttonId).apply {
                    setOnClickListener { onCellClicked(this, row, col) }
                }
            }
        }
    }

    private fun onCellClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) return

        button.text = currentPlayer
        movesCount++

        if (checkWinner()) {
            winnerMsgTextView.text = "$currentPlayer ניצח!!! "
            resetButton.isEnabled = true
        } else if (movesCount == 9) {
            winnerMsgTextView.text = "יש תיקו!"
            resetButton.isEnabled = true
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            playerTurnTextView.text = "התור של $currentPlayer"
        }
    }


    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            if (board[i][0].text == currentPlayer &&
                board[i][1].text == currentPlayer &&
                board[i][2].text == currentPlayer
            ) return true
        }

        for (i in 0..2) {
            if (board[0][i].text == currentPlayer &&
                board[1][i].text == currentPlayer &&
                board[2][i].text == currentPlayer
            ) return true
        }

        if (board[0][0].text == currentPlayer &&
            board[1][1].text == currentPlayer &&
            board[2][2].text == currentPlayer
        ) return true

        if (board[0][2].text == currentPlayer &&
            board[1][1].text == currentPlayer &&
            board[2][0].text == currentPlayer
        ) return true

        return false
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j].text = ""
            }
        }

        currentPlayer = "X"
        playerTurnTextView.text = "התור של $currentPlayer"
        winnerMsgTextView.text = ""
        resetButton.isEnabled = false
        movesCount = 0
    }
}