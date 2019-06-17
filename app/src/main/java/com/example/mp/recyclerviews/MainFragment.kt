package com.example.mp.recyclerviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*

data class Groceries(val title: String, val rs: String)

class MainFragment : Fragment() {

    private val groceries = listOf(
        Groceries("Wheat Atta", "Rs.163.00"),
        Groceries("Grapes", "Rs.90.00"),
        Groceries("Orange", "Rs.70.00"),
        Groceries("Banana", "Rs.40.00"),
        Groceries("Apple", "Rs.200.00"),
        Groceries("Mustard Oil", "Rs.190.00"),
        Groceries("Everest Masala", "Rs.50.00"),
        Groceries("Cow Milk - 500ml", "Rs.22.00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ListAdapter(getContext(),groceries)
        }
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}