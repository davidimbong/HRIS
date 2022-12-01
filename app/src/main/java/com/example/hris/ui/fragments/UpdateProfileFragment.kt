package com.example.hris.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.hris.R
import com.example.hris.databinding.FragmentProfileBinding
import com.example.hris.databinding.FragmentUpdateProfileBinding


class UpdateProfileFragment : Fragment() {

    lateinit var binding: FragmentUpdateProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdate.setOnClickListener {
            Toast.makeText(requireContext(), "Successfully Updated Profile", Toast.LENGTH_SHORT).show()
        }
    }

}