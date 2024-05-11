package com.example.to_do_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_app.databinding.ActivityAddTaskBinding
import com.example.to_do_app.databinding.ActivityMainBinding

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: TasksDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val title = binding.taskEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val task = Task(0, title, content)
            db.insertTask(task)
            finish()
            Toast.makeText(this, "Task saved", Toast.LENGTH_SHORT).show()
        }
    }
}