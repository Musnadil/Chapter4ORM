package com.musnadil.chapter4orm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.musnadil.chapter4orm.databinding.ActivityAddBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddBinding
    var mDB: StudentDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mDB = StudentDatabase.getInstance(this)

        binding.btnSave.setOnClickListener {
            val objectStudent = Student(
                null,
                binding.etNama.text.toString(),
                binding.etEmail.text.toString()
            )
            GlobalScope.async {
                val result = mDB?.studentDao()?.insertStudent(objectStudent)
                runOnUiThread {
                    if (result != 0.toLong()){
                        Toast.makeText(this@AddActivity, "${objectStudent.nama} sukses ditambahkan", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@AddActivity, "${objectStudent.nama} gagal ditambahkan", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        }
    }
}