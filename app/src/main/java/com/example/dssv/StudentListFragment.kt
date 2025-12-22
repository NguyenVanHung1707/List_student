package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentListFragment : Fragment() {

    private val viewModel: StudentViewModel by activityViewModels()
    private lateinit var adapter: StudentRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewStudents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAddStudent)

        adapter = StudentRecyclerAdapter(
            students = viewModel.students.value ?: emptyList(),
            onItemClick = { student ->
                viewModel.selectStudent(student)
                findNavController().navigate(R.id.action_list_to_edit)
            },
            onDeleteClick = { student ->
                showDeleteConfirmation(student)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Observe students list changes
        viewModel.students.observe(viewLifecycleOwner) { students ->
            adapter.updateStudents(students)
        }

        fabAdd.setOnClickListener {
            viewModel.clearForm()
            findNavController().navigate(R.id.action_list_to_add)
        }
    }

    private fun showDeleteConfirmation(student: Student) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.name}?")
            .setPositiveButton("Xóa") { _, _ ->
                viewModel.deleteStudent(student)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
}
