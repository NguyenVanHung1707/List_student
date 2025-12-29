package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val studentId = viewModel.studentId.value?.trim() ?: ""
            val name = viewModel.name.value?.trim() ?: ""
            val phone = viewModel.phone.value?.trim() ?: ""
            val address = viewModel.address.value?.trim() ?: ""

            if (studentId.isEmpty() || name.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập MSSV và Họ tên", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(name = name, studentId = studentId, phone = phone, address = address)
            val success = viewModel.addStudent(student)
            
            if (success) {
                Toast.makeText(requireContext(), "Đã thêm sinh viên", Toast.LENGTH_SHORT).show()
                viewModel.clearForm()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "MSSV đã tồn tại", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
