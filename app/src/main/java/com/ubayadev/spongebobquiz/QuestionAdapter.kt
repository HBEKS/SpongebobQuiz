package com.ubayadev.spongebobquiz

import android.content.Intent
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubayadev.spongebobquiz.databinding.QuestionCardBinding

class QuestionAdapter(): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    //inner class itu clas yang ada di class lain, bukan inheritence --> class yang bertanggung jawab mengatur layout di question_card.xml
    class QuestionViewHolder(var binding: QuestionCardBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionViewHolder {
        var binding = QuestionCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: QuestionViewHolder,
        position: Int
    ) {
        //function yang digunakan untuk render per item
        //render question dan image
        holder.binding.txtQuestion.text = QuestionData.questions[position].question
        //load gambar dari drawable or url
        if(QuestionData.questions[position].url.isNotEmpty()){
            //load picasso
            val url = QuestionData.questions[position].url
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception ->
                exception.printStackTrace()
                Toast.makeText(holder.itemView.context, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
            builder.build().load(url).into(holder.binding.imgQuestion)
        } else {
            holder.binding.imgQuestion.setImageResource(QuestionData.questions[position].imageId)
        }

        //handle not available with grayscale
        if(!QuestionData.questions[position].isAvailable){
            val colorMatrix = ColorMatrix().apply { setSaturation(0f) }
            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            holder.binding.imgQuestion.colorFilter = colorFilter
        }

        //handle button edit
        holder.binding.btnDetail.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditQuestionActivity::class.java)
            intent.putExtra("question_index", position)
            holder.itemView.context.startActivity(intent)
        }

        //handle button delete
        holder.binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Alert")
            builder.setMessage("Are you sure you want to delete this item?")
            builder.setPositiveButton("Yep"){
                dialog, _ ->
                val list = QuestionData.questions.toMutableList()
                list.removeAt(position)
                QuestionData.questions = list.toTypedArray()
                notifyDataSetChanged()
                dialog.dismiss()
            }

            builder.setNegativeButton("Nein"){ dialog, _ ->
                dialog.dismiss()
            }

            builder.create().show()
        }
    }

    override fun getItemCount() = QuestionData.questions.size


}