package com.ubayadev.spongebobquiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubayadev.spongebobquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //setup view binding
    private lateinit var binding: ActivityMainBinding //ActivityMainBinding: class yang telah dibuatkan otomatis oleh android studio
    //lateinit --> keyword untuk kasi tau kotlin yang nilainya itu nanti2 bukan sekarang
    //klo punya new project dan new activity wajib pakai viewBinding
    companion object{
        val PLAYER_SCORE = "player_score"
    }
    var currentQuestion = 0
    var score = 0



    //fungsi yang dijalankan pertama kali dan hanya dijalankan sekali saja
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)

        val name = this.intent.getStringExtra(IntroActivity.PLAYER_NAME_KEY)//key tidak boleh salah ketik atau typo
        //dan kalau string tidak perlu default value dan kalau berbau angka perlu default nilainya
        binding.txtWelcome.text = "Welcome, $name"

        displayQuestion()

        binding.btnTrue.setOnClickListener { //lambda function yang menerima 1 parameter
            if(QuestionData.questions[currentQuestion].answer){ //jika jawabannya true
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                score +=5
            } else{
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                score -=3
            }
            displayScore()
            nextQuestion()

        }//event listener

        binding.btnFalse.setOnClickListener {
            if(!QuestionData.questions[currentQuestion].answer){ //jika jawabannya false
                Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show()
                score +=5
            } else{
                Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show()
                score -=3
            }
            displayScore()
            nextQuestion()
        }//event listener
    }

    /*override fun onStart() {
        super.onStart()
        Log.d("state", "Start state...")
    }

    override fun onStop() { //key nya state
        super.onStop()
        Log.d("state", "Stop state...")
    }*/
    fun displayQuestion(){
        binding.txtQuestion.text = QuestionData.questions[currentQuestion].question
    }

    fun displayScore(){ binding.txtScore.text = "Score: $score" }

    //penyebab error bisa cek di nyancat aka logcat
    fun nextQuestion(){
        currentQuestion++
        if (currentQuestion >= 5){
            //redirect user to result
            val intent = Intent(this,ResultActivity::class.java)
            intent.putExtra(PLAYER_SCORE, score)
            startActivity(intent)
            finish()

            //currentQuestion=0
            binding.txtQuestion.text = "Congratulations, you've completed all questions"
            binding.btnTrue.visibility = View.INVISIBLE //button hilang
            binding.btnFalse.visibility = View.INVISIBLE //button hilang

            return//kembali ke pemanggilnya jadinya void
        }
        displayQuestion()
    }
}