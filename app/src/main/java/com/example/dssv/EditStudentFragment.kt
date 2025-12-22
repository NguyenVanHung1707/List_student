package com.example.dssv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.dssv.databinding.FragmentEditStudentBinding

class EditStudentFragment : Fragment() {

    private var _binding: FragmentEditStudentBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check if a student is selected
        if (viewModel.selectedStudent.value == null) {
            Toast.makeText(requireContext(), "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        binding.buttonUpdate.setOnClickListener {
            val name = viewModel.name.value?.trim() ?: ""
            
            if (name.isEmpty()) {
                Toast.makeText(requireContext(), "Họ tên không được trống", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = viewModel.updateStudent()
            
            if (success) {
                Toast.makeText(requireContext(), "Đã cập nhật sinh viên", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Lỗi cập nhật", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
