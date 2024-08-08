package com.maths.tictactoe


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.maths.tictactoe.databinding.ActivityHomeBinding

class homeActivity : AppCompatActivity() { // Capitalize class name to follow conventions
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)

            binding.startGame.setOnClickListener {
                val player1 = binding.playerXname.text.toString()
                val player2 = binding.playerOname.text.toString()
                val round = binding.round.text.toString()
                if(player1.isNotEmpty() && player2.isNotEmpty() && round.isNotEmpty()){
                     val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("Player 1", binding.playerOname.text.toString())
                        putExtra("Player 2", binding.playerXname.text.toString())
                         putExtra("Round" , binding.round.text.toString())
                }
                          startActivity(intent)
                }else{
                    dialogBox()
                }
        }

    }
    private fun dialogBox(){
        AlertDialog.Builder(this)
            .setTitle("Tic Tac Toe")
            .setIcon(R.drawable.logo)
            .setMessage("Enter Both names of Player")
            .setCancelable(false)
            .setPositiveButton("Ok"){dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }
}
