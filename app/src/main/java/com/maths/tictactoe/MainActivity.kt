package com.maths.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.maths.tictactoe.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Variables initialization
    private var currentPlayer = "X"  // Current player (X or O)
    private var player_x = 0  // Score for player X
    private var player_o = 0  // Score for player O
    private var round = 0  // Current round
    private var winner = ""  // Winner message
    private var initialRound = 0  // Initial number of rounds

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hide Action Bar and set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)

        // Initialize grid adapter
        val adapter = GridAdapter(this) { position ->
            handleItemClick(position)
        }

        binding.gridView.adapter = adapter

        // Retrieve player names and round from Intent
        val player1 = intent.getStringExtra("Player 1")
        val player2 = intent.getStringExtra("Player 2")
        round = intent.getStringExtra("Round")?.toIntOrNull() ?: 0
        binding.player1.text = player1 ?: "Player 1"
        binding.player2.text = player2 ?: "Player 2"
        binding.round.text = "Round: $round"
        initialRound = round
    }

    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    private fun handleItemClick(position: Int) {
        val adapter = binding.gridView.adapter as GridAdapter

        // Check if the selected position is empty and game is not won yet
        if (adapter.buttonStates[position] == "" && !winningCheck()) {
            adapter.buttonStates[position] = currentPlayer

            if (winningCheck()) {
                // Handle win condition
                updateScore()
                if(player_x > player_o || player_o > player_x) {
                    winner = "Congratulations $currentPlayer won"
                    disableButton()
                }
                if (round > 1) {
                    dialogBox("Player $currentPlayer won round $round")
                    round--
                    binding.round.text = "Round: $round"
                }
                else if (round == 1) {
                    if (player_x == player_o) {
                        winner = "It's a Tie"
                        showWinnerDialog()
                    }
                    else{
                        showWinnerDialog()
                        round--
                        binding.round.text = "Round: $round"
                    }
                } else {
                    // When no rounds left, ensure to show dialog
                    showWinnerDialog()
                }
            } else if (isBoardFull()) {
                // Handle tie condition when board is full
                winner = "It's a Tie"
                showWinnerDialog()
            } else {
                // Switch players if no win or tie
                currentPlayer = if (currentPlayer == "X") "O" else "X"
                updateStroke()
                adapter.notifyDataSetChanged()
            }
        } else if (winningCheck()) {
            // Show message if the game is already finished
            Toast.makeText(this, "Game Already Finished", Toast.LENGTH_SHORT).show()
        } else {
            // Show message if the move is invalid
            Toast.makeText(this, "Invalid Move", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isBoardFull(): Boolean {
        val adapter = binding.gridView.adapter as GridAdapter
        // Check if there are no empty spaces left on the board
        return !adapter.buttonStates.contains("")
    }

    private fun showWinnerDialog() {
        val winnerDialog = WinnerActivity().apply {
            arguments = Bundle().apply {
                putString("player1Score", player_x.toString())
                putString("player2Score", player_o.toString())
                putString("winner", winner)
            }
            setOnGameResetListener(object : WinnerActivity.OnGameResetListener {
                override fun onGameReset() {
                    refreshGame()
                }
            })
        }
        winnerDialog.show(supportFragmentManager, "WinnerDialog")
    }

    private fun disableButton() {
        val adapter = binding.gridView.adapter as GridAdapter

        // Disable all empty buttons
        for (i in adapter.buttonStates.indices) {
            if (adapter.buttonStates[i] == "") {
                adapter.buttonStates[i] = "#"  // Consider a different way to represent an inactive cell
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun updateStroke() {
        val player1Stroke = ContextCompat.getColor(this, R.color.blunishPurple)
        val player2Stroke = ContextCompat.getColor(this, R.color.blunishPurple)
        val defaultStrokeColor = Color.TRANSPARENT

        // Update player 1 card
        val player1Drawable = ContextCompat.getDrawable(this, R.drawable.player_stroke)
        if (currentPlayer == "X") {
            player1Drawable?.setColorFilter(player1Stroke, PorterDuff.Mode.SRC_IN)
        } else {
            player1Drawable?.setColorFilter(defaultStrokeColor, PorterDuff.Mode.SRC_IN)
        }
        binding.player1Card.background = player1Drawable

        // Update player 2 card
        val player2Drawable = ContextCompat.getDrawable(this, R.drawable.player_stroke)
        if (currentPlayer == "O") {
            player2Drawable?.setColorFilter(player2Stroke, PorterDuff.Mode.SRC_IN)
        } else {
            player2Drawable?.setColorFilter(defaultStrokeColor, PorterDuff.Mode.SRC_IN)
        }
        binding.player2Card.background = player2Drawable

        binding.xTurn.visibility = if(currentPlayer == "X") View.VISIBLE else View.INVISIBLE
        binding.oTurn.visibility = if(currentPlayer == "O") View.VISIBLE else View.INVISIBLE
    }

    private fun winningCheck(): Boolean {
        val adapter = binding.gridView.adapter as GridAdapter
        val buttonStates = adapter.buttonStates
        val board = Array(3) { Array(3) { "" } }
        for (i in 0..2) {
            for (j in 0..2) {
                board[i][j] = buttonStates[i * 3 + j]
            }
        }

        // Check for a winner
        // For Rows
        for (i in 0..2) {
            if (board[i][0] != "" && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true
            }
        }
        // For Columns
        for (i in 0..2) {
            if (board[0][i] != "" && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true
            }
        }
        // For Diagonals
        if (board[0][0] != "" && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true
        }
        return board[0][2] != "" && board[0][2] == board[1][1] && board[1][1] == board[2][0]
    }

    @SuppressLint("SetTextI18n")
    private fun updateScore() {
        // Update score based on the current player
        if (currentPlayer == "X") {
            player_x++
        } else {
            player_o++
        }
    }

    @SuppressLint("SetTextI18n")
    private fun refreshGame() {
        val adapter = binding.gridView.adapter as GridAdapter
        // Clear all button states
        for (i in adapter.buttonStates.indices) {
            adapter.buttonStates[i] = ""
        }

        // Reset background for player cards
        if (currentPlayer == "X" || currentPlayer == "O") {
            binding.player1Card.background = ContextCompat.getDrawable(this, R.drawable.player_turn_layout)
            binding.player2Card.background = ContextCompat.getDrawable(this, R.drawable.player_turn_layout)
        }
        binding.xTurn.visibility = if(currentPlayer == "X") View.VISIBLE else View.VISIBLE
        binding.oTurn.visibility = if(currentPlayer == "Y") View.VISIBLE else View.VISIBLE
        // Reset round and scores if necessary
        if (round == 0) {
            binding.round.text = "Round: $initialRound"  // Ensure round is updated
            round = initialRound
            player_o = 0
            player_x = 0

        } else {
            binding.round.text = "Round: $round"
        }
        adapter.notifyDataSetChanged()
    }
    @SuppressLint("MissingInflatedId")
    private fun dialogBox(message: String) {
        // Inflate the custom layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_box_design, null)
        val messageTextView = dialogView.findViewById<TextView>(R.id.result)
        val positiveButton = dialogView.findViewById<Button>(R.id.ok)

        // Set the message
        messageTextView.text = message

        // Create and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        positiveButton.setOnClickListener {
            // Refresh the game and dismiss the dialog
            refreshGame()
            dialog.dismiss()
        }

        dialog.show()
    }

}
