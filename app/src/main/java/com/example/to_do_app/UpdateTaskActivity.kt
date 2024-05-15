package com.example.to_do_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.to_do_app.databinding.ActivityUpdateBinding
import java.text.SimpleDateFormat
import java.util.*

class UpdateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var db: TasksDatabaseHelper
    private var taskId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TasksDatabaseHelper(this)

        taskId = intent.getIntExtra("task_id", -1)
        if (taskId == -1) {
            finish()
            return
        }

        val task = db.getTaskByID(taskId)
        binding.updateTaskEditText.setText(task?.title)
        binding.updateContentEditText.setText(task?.content)

        // Check if task date is null or empty
        if (!task?.date.isNullOrEmpty()) {
            // Set the initial date for the DatePicker
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(task?.date)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: Date()
            binding.datePicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTaskEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()
            val newDate = getDateFromDatePicker() // Get the updated date from DatePicker
            val updatedTask = Task(taskId, newTitle, newContent, newDate)
            db.updateTask(updatedTask)
            finish()
            Toast.makeText(this, "Change Saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDateFromDatePicker(): String {
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month + 1 // Month starts from 0
        val year = binding.datePicker.year

        // Format the date as "yyyy-MM-dd"
        return "$year-$month-$day"
    }
}
