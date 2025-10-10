package com.ubayadev.spongebobquiz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubayadev.spongebobquiz.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra(MainActivity.PLAYER_SCORE, 0)
        binding.txtScore.text = score.toString()

        //handle high score

        val sharedFile = packageName
        var shared = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var highscore = shared.getInt(MainActivity.PLAYER_SCORE,0)

        if (score > highscore){
            var editor = shared.edit()
            editor.putInt(MainActivity.PLAYER_SCORE, score)
            editor.apply()
            binding.txtHighScore.text = score.toString()
        } else{
            binding.txtHighScore.text = highscore.toString()
        }

        binding.btnPlayAgain.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}