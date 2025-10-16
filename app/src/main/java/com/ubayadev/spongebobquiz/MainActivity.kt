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
import com.squareup.picasso.Picasso
import com.ubayadev.spongebobquiz.databinding.ActivityMainBinding

// 1. Cek status perubahan yang ada
// git status

// 2. Menambahkan semua perubahan (termasuk file baru dan yang diubah)
// git add .

// Atau, jika ingin menambahkan file tertentu:
// git add path/to/your/file

// 3. Commit perubahan dengan pesan yang jelas
// git commit -m "Pesan commit Anda di sini"

// 4. Push perubahan ke branch master di remote GitHub
// git push origin main

// Jika sudah pernah melakukan push sebelumnya dan sudah mengatur tracking branch, cukup gunakan:
// git push

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
    var banks = ArrayList<Question>()



    //fungsi yang dijalankan pertama kali dan hanya dijalankan sekali saja
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)

        banks = QuestionData.questions.filter { it.isAvailable }.toList() as ArrayList<Question>

        val name = this.intent.getStringExtra(IntroActivity.PLAYER_NAME_KEY)//key tidak boleh salah ketik atau typo
        //dan kalau string tidak perlu default value dan kalau berbau angka perlu default nilainya
        binding.txtWelcome.text = "Welcome, $name"

        displayQuestion()

        binding.btnTrue.setOnClickListener { //lambda function yang menerima 1 parameter
            if(banks[currentQuestion].answer){ //jika jawabannya true
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
            if(!banks[currentQuestion].answer){ //jika jawabannya false
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
        binding.txtQuestion.text = banks[currentQuestion].question
        if(banks[currentQuestion].url.isNotEmpty()){
            //picasso
            val url = banks[currentQuestion].url
            val builder = Picasso.Builder(this)
            builder.listener { picasso, uri, exception ->
                exception.printStackTrace()
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
            builder.build().load(url).into(binding.imgQuestion)
        } else {
            //drawable
            binding.imgQuestion.setImageResource(banks[currentQuestion].imageId)
        }
    }

    fun displayScore(){ binding.txtScore.text = "Score: $score" }

    //penyebab error bisa cek di nyancat aka logcat
    fun nextQuestion(){
        currentQuestion++
        if (currentQuestion >= banks.size){
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