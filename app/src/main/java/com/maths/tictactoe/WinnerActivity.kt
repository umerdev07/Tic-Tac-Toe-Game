package com.maths.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.maths.tictactoe.databinding.ActivityWinnerBinding

class WinnerActivity : DialogFragment() {

    private val binding: ActivityWinnerBinding by lazy {
        ActivityWinnerBinding.inflate(layoutInflater)
    }
    private var onGameResetListener: OnGameResetListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        // Retrieve arguments
        val player1Score = arguments?.getString("player1Score") ?: "0"
        val player2Score = arguments?.getString("player2Score") ?: "0"
        val winner = arguments?.getString("winner") ?: "No Winner"

        // Set text for views
        binding.player1Score.text = player1Score
        binding.player2Score.text = player2Score
        binding.winnerText.text = winner

        // Set up OK button click listener
        binding.refresh.setOnClickListener {
            onGameResetListener?.onGameReset()
            dismiss()  // Close the dialog
        }
    }

    fun setOnGameResetListener(listener: OnGameResetListener) {
        onGameResetListener = listener
    }

    interface OnGameResetListener {
        fun onGameReset()
    }
}
