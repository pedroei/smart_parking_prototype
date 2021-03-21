package pt.ipvc.smartparkingprototype

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(), TodoAdapter.OnItemClickListener {

    private lateinit var todoAdapter: TodoAdapter// lateint is a promise to kotlin that this variable will be initialized later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)

        setContentView(R.layout.activity_main)

        val message = intent.getStringExtra(EXTRA_MESSAGE)

        todoAdapter = TodoAdapter(mutableListOf(), this)

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)

        if (message != null) todoAdapter.addTodo(Todo(message))

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }

        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked!", LENGTH_SHORT).show()
        todoAdapter.deleteSingleTodo(position)
    }
}