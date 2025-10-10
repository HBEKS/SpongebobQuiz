package com.ubayadev.spongebobquiz

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.ubayadev.spongebobquiz.databinding.ActivityNewQuestionBinding

class EditQuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewQuestionBinding
    val items = arrayOf("karen", "sponge", "squid", "mrkrab", "mermaid")

    fun usingTemplate(){
        with(binding){
            lblImageURL.visibility = View.GONE
            txtURL.visibility = View.GONE
            lblChooseImage.visibility = View.VISIBLE
            spinnerImage.visibility = View.VISIBLE
        }
    }

    fun usingExternal(){
        with(binding){
            lblImageURL.visibility = View.VISIBLE
            txtURL.visibility = View.VISIBLE
            lblChooseImage.visibility = View.GONE
            spinnerImage.visibility = View.GONE
        }
    }

    fun loadQuestion(){

        val pos = intent.getIntExtra("question_index", -1)
        with(QuestionData.questions[pos]){
            binding.txtNewQuestion.setText(question)
            binding.txtURL.setText(url)
            binding.checkAvailable.isChecked = isAvailable

            if(answer){
                binding.rdoTrue.isChecked = true
            } else {
                binding.rdoFalse.isChecked = true
            }

            //convert frpm int (img id) to string (file name)
            val imageName = resources.getResourceEntryName(imageId)
            val spinnerIndex = items.indexOf(imageName)
            binding.spinnerImage.setSelection(spinnerIndex)

            //handle rdoExternal and rdoTemplate
            if(url.isNotEmpty()){
                binding.rdoExternal.isChecked = true
                usingExternal()

                //load picasso
                val url = binding.txtURL.text.toString()
                val builder = Picasso.Builder(applicationContext)
                builder.listener { picasso, uri, exception ->
                    exception.printStackTrace()
                    Toast.makeText(applicationContext, "Invalid URL", Toast.LENGTH_SHORT).show()
                }
                builder.build().load(url).into(binding.imgPreview)

            } else {
                binding.rdoTemplate.isChecked = true
                usingTemplate()
                binding.imgPreview.setImageResource(imageId)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadQuestion()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle radio image
        binding.lblImageURL.visibility = View.GONE
        binding.txtURL.visibility = View.GONE

        binding.rdoTemplate.setOnClickListener {
            usingTemplate()
        }

        binding.rdoExternal.setOnClickListener {
            usingExternal()
        }

        //handle spinner

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        // R.layout ==> mengakses layout res | klo didepan ada androidnya mengakses layout OS nya
        // val items = arrayOf("karen", "sponge", "squid", "mrkrab", "mermaid")
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

            val posi = intent.getIntExtra("question_index", -1)
            QuestionData.questions[posi] = q

            //val listQuestion = QuestionData.questions.toMutableList()
            //listQuestion.add(q)
            //QuestionData.questions = listQuestion.toTypedArray() // delete array yang lama dan mereplace dengan array yang telah di update

            Toast.makeText(this, "Question Saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}