package com.example.marketpetproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.marketpetproject.R
import com.example.marketpetproject.databinding.FragmentCoreBinding
import com.example.marketpetproject.ui.utility.CoreAdapter
import com.example.marketpetproject.ui.utility.onItemClick
import dagger.hilt.android.AndroidEntryPoint

//Экран "Основной". Обработка/бизнес логика в CoreViewModel

@AndroidEntryPoint
class CoreFragment : Fragment(), onItemClick {
    private lateinit var binding: FragmentCoreBinding
    private val vm: CoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoreBinding.inflate(inflater, container, false)
        binding.searchView.onActionViewExpanded()
        binding.searchView.clearFocus()

        vm.listLiveData.observe(viewLifecycleOwner) {
            binding.recycler.layoutManager = GridLayoutManager(context, 2)
            binding.recycler.adapter = CoreAdapter(it, click = this)
        }
        vm.textLiveData.observe(viewLifecycleOwner) {
            binding.editText.setText(it)
        }
        //EditText "Адресс ПВЗ" Пользователь может ввести свое значение
        binding.editText.addTextChangedListener {
            vm.insertPref(it.toString())
        }
        return binding.root
    }

    //Реализация клика на RecyclerView, передача bundle в новый фрагмент
    override fun onItemClick(item: Int) {
        vm.listLiveData.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putParcelable("Id", it[item])
            val details = GoodsFragment()
            details.setArguments(bundle)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.placeHolder, details).addToBackStack("first").commit()
        }
    }
}