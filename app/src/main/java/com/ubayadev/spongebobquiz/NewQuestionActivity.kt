package com.ubayadev.spongebobquiz

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.ubayadev.spongebobquiz.databinding.ActivityNewQuestionBinding

class NewQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewQuestionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle radio image
        binding.lblImageURL.visibility = View.GONE
        binding.txtURL.visibility = View.GONE

        binding.rdoTemplate.setOnClickListener {
            with(binding){
                lblImageURL.visibility = View.GONE
                txtURL.visibility = View.GONE
                lblChooseImage.visibility = View.VISIBLE
                spinnerImage.visibility = View.VISIBLE
            }
        }

        binding.rdoExternal.setOnClickListener {
            with(binding){
                lblImageURL.visibility = View.VISIBLE
                txtURL.visibility = View.VISIBLE
                lblChooseImage.visibility = View.GONE
                spinnerImage.visibility = View.GONE
            }
        }

        //handle spinner
        val items = arrayOf("karen", "sponge", "squid", "mrkrab", "mermaid")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        // R.layout ==> mengakses layout res | klo didepan ada androidnya mengakses layout OS nya

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerImage.adapter = adapter

        //convert from "karen" string menjadi image id (int) | getResources() bisa diketik dengan resources
        val imgid = resources.getIdentifier(items[0], "drawable", this.packageName)
        binding.imgPreview.setImageResource(imgid)

        //handle spinner image change
        binding.spinnerImage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val imgid = resources.getIdentifier(items[position], "drawable", packageName)
                binding.imgPreview.setImageResource(imgid)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        //handle txt url
        binding.txtURL.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                val url = binding.txtURL.text.toString()
                val builder = Picasso.Builder(this)
                builder.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                    Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
                }
                builder.build().load(url).into(binding.imgPreview)
                //Picasso
            }
            true
        }


        //handle button save
        binding.btnSave.setOnClickListener {
            val question = binding.txtNewQuestion.text.toString()
            val answer = binding.rdoTrue.isChecked
            //val radio = findViewById<RadioButton>(binding.rdoAnswer.checkedRadioButtonId)
            //val answer = radio.text.toString().lowercase().toBoolean()
            //klo mau tag pakai radio.tag

            val selectedImage = items[binding.spinnerImage.selectedItemPosition]
            val imgid = resources.getIdentifier(selectedImage, "drawable", this.packageName)
            val url = binding.txtURL.text.toString()
            val available = binding.checkAvailable.isChecked

            val q = Question(question, answer, imgid, url, available)
            val listQuestion = QuestionData.questions.toMutableList()
            listQuestion.add(q)
            QuestionData.questions = listQuestion.toTypedArray() // delete array yang lama dan mereplace dengan array yang telah di update

            Toast.makeText(this, "Question Added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}