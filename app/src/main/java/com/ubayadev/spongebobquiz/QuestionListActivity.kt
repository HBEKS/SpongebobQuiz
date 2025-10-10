package com.ubayadev.spongebobquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubayadev.spongebobquiz.databinding.ActivityQuestionListBinding

class QuestionListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recViewQuestion){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = QuestionAdapter()
        }

        binding.recViewQuestion.layoutManager = LinearLayoutManager(this)
        binding.recViewQuestion.setHasFixedSize(true)
        binding.recViewQuestion.adapter = QuestionAdapter()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, NewQuestionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.recViewQuestion.layoutManager = LinearLayoutManager(this)
        binding.recViewQuestion.setHasFixedSize(true)
        binding.recViewQuestion.adapter = QuestionAdapter()
    }
}