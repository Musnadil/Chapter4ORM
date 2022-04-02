package com.musnadil.chapter4orm

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.musnadil.chapter4orm.databinding.StudentItemBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class StudentAdapter(private val listStudent: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    companion object {
        const val STUDENT = "STUDENT"
    }

    class ViewHolder(val binding: StudentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentAdapter.ViewHolder {
        val binding = StudentItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentAdapter.ViewHolder, position: Int) {
        with(holder.binding) {
            tvId.text = listStudent[position].id.toString()
            tvNama.text = listStudent[position].nama
            tvEmail.text = listStudent[position].email

            btnUpdate.setOnClickListener {
                val intent = Intent(it.context, EditActivity::class.java)
                intent.putExtra("student", listStudent[position])
                it.context.startActivity(intent)
            }

            btnDelete.setOnClickListener {
                AlertDialog.Builder(it.context)
                    .setPositiveButton("Ya") { p0, p1 ->
                        val mDb = StudentDatabase.getInstance(holder.itemView.context)

                        GlobalScope.async {
                            val result = mDb?.studentDao()?.deleteStudent(listStudent[position])

                            (holder.itemView.context as MainActivity).runOnUiThread {
                                if (result != 0) {
                                    Toast.makeText(
                                        it.context,
                                        "Data ${listStudent[position].nama} berhasil dihapus",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        it.context,
                                        "Data ${listStudent[position].nama} gagal dihapus",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            (holder.itemView.context as MainActivity).fetchData()
                        }
                    }
                    .setNegativeButton("Tidak") { p0, p1 ->
                        p0.dismiss()

                    }
                    .setMessage("Apakah anda yakin ingin menghapus data ${listStudent[position].nama}?")
                    .setTitle("Konfirmasi Hapus")
                    .create()
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = listStudent.size
}