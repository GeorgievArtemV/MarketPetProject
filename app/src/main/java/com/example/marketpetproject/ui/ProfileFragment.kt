package com.example.marketpetproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.room.Room
import com.example.marketpetproject.databinding.FragmentProfileBinding
import com.example.marketpetproject.di.modules.db.AppDb
import com.example.marketpetproject.ui.utility.ProfileAdapter
import dagger.hilt.android.AndroidEntryPoint

//Экран "Заказы". Показывает товары, которые пользователь "оплатил"

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private val vm: ProfileViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        val db = Room.databaseBuilder(requireContext(), AppDb::class.java, "Profile").build()
        vm.setData(db.appDAO())
        vm.setLiveData.observe(viewLifecycleOwner) {
            binding.recyclerView.adapter = ProfileAdapter(it)
        }

        return binding.root
    }
}