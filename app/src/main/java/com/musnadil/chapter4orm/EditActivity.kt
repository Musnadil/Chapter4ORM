package com.musnadil.chapter4orm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.musnadil.chapter4orm.databinding.ActivityEditBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    var mDB: StudentDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDB = StudentDatabase.getInstance(this)

        val objectStudent = intent.getParcelableExtra<Student>("student")

        binding.etNama.setText(objectStudent?.nama.toString())
        binding.etEmail.setText(objectStudent?.email.toString())

        binding.btnUpdate.setOnClickListener {
            objectStudent?.nama = binding.etNama.text.toString()
            objectStudent?.email = binding.etEmail.text.toString()
            GlobalScope.async {
                val result = mDB?.studentDao()?.updateStudent(objectStudent!!)
//                bagusnya pakai runBlocking {  }
                runOnUiThread {
                    if (result != 0){
                        Toast.makeText(this@EditActivity, "Sukses mengubah data menjadi ${objectStudent?.nama}", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@EditActivity, "Gagal mengubah data", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }
        }
    }
}