package com.musnadil.chapter4orm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.musnadil.chapter4orm.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mDB: StudentDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDB = StudentDatabase.getInstance(this)
        binding.rvStudents.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        fetchData()

        binding.btnFab.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }

    fun fetchData() {
        GlobalScope.launch {
            val listStudent = mDB?.studentDao()?.getAllStudent()
            runOnUiThread {
                listStudent?.let {
                    val adapter = StudentAdapter(it as ArrayList<Student>)
                    binding.rvStudents.adapter = adapter
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
    }
}