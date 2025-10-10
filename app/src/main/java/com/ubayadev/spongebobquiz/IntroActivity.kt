package com.ubayadev.spongebobquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubayadev.spongebobquiz.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    companion object{
        val PLAYER_NAME_KEY = "player_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root) // harus pakai binding.root

        binding.btnStart.setOnClickListener {
            val name = binding.txtName.text.toString() //textview nya itu bersifat mutable dan biar ga null kita perlu panggil toString()
            val intent = Intent(this, MainActivity::class.java) //explicit intent
            intent.putExtra(PLAYER_NAME_KEY, name) //key-value pair --> key harus unique identifier dan harus string
            startActivity(intent)
            finish()
        }

    }
}