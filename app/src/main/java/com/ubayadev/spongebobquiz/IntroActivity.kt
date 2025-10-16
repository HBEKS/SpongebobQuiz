package com.ubayadev.spongebobquiz

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubayadev.spongebobquiz.databinding.ActivityIntroBinding
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    companion object{
        val PLAYER_NAME_KEY = "player_name"
    }

    private fun showDatePickerDialog(){
        // set default date
        val today = Calendar.getInstance()
        val year = today.get(Calendar.YEAR)
        val month = today.get(Calendar.MONTH)
        val day = today.get(Calendar.DAY_OF_MONTH)

        //set date picker dialog
        val dp = DatePickerDialog(
            this,
            { view, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val formatDate = SimpleDateFormat("dd/MM/yyyy")
                binding.txtBOD.setText(formatDate.format(selectedDate.time))
                val formatDateSQL = SimpleDateFormat("yyyy-MM-dd")
                binding.txtBOD.tag = formatDateSQL.format(selectedDate.time)
            },
            year,
            month,
            day
        )

        dp.show()
    }

    private fun loadMovie(url: String){
        binding.webView.settings.javaScriptEnabled = true

        //anonymous object --> tanpa nama
        binding.webView.webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
        binding.webView.loadUrl(url)
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

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, QuestionListActivity::class.java)
            startActivity(intent)
        }

        binding.txtBOD.setOnClickListener { showDatePickerDialog() }

        loadMovie("https://www.youtube.com/watch?v=qikEnX5pKBQ")
    }
}