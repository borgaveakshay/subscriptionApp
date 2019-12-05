package com.example.mp.recyclerviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * Please add an image view with id "selected"
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseRecyclerAdapter<T : BaseSelectModel, E : ViewDataBinding>(private val layoutId: Int) :
    RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder>() {

    protected var mDataList: MutableList<T> = mutableListOf()

    private var mSelectedList: MutableList<T> = mutableListOf()

    lateinit var mRecycleMultiSelectListener: RecycleMultiSelectListener<T>

    lateinit var mRecycleSingleSelectListener: RecycleSingleSelectListener<T>


    interface RecycleMultiSelectListener<T> {

        fun onMultipleSelect(selectedList: List<T>)
    }

    interface RecycleSingleSelectListener<T> {

        fun onSingleSelect(item: T)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false))


    override fun getItemCount(): Int = mDataList.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.tag = position

        holder.itemView.setOnClickListener {

            setMultiSelectListener(holder, position)

            setSingleSelectListener(holder, position)
        }

        onBindData(holder.getBinding() as E, position)

    }

    /**
     *
     */
    private fun setMultiSelectListener(holder: ViewHolder, position: Int) {

        if (::mRecycleMultiSelectListener.isInitialized) {

            if (!mDataList[position].isSelected) {
                mSelectedList.add(mDataList[position])
                mDataList[position].isSelected = true
                onBindData(holder.getBinding() as E, position)
            } else {
                mDataList[position].isSelected = false
                mSelectedList.remove(mDataList[position])
                onBindData(holder.getBinding() as E, position)
            }
            mRecycleMultiSelectListener.onMultipleSelect(mSelectedList)
        }

    }


    private fun setSingleSelectListener(holder: ViewHolder, position: Int) {

        if (::mRecycleSingleSelectListener.isInitialized) {

            if (mDataList[position].isSelected == false) {
                mRecycleSingleSelectListener.onSingleSelect(mDataList[position])
                mDataList[position].isSelected = true
                makeAllNotSelectedExpect(position)
                onBindData(holder.getBinding() as E, position)


            } else {
                mDataList[position].isSelected = false
                onBindData(holder.getBinding() as E, position)

            }

        }
    }

    private fun makeAllNotSelectedExpect(position: Int) {


        for (i in 0 until mDataList.size) {

            if (i != position) {
                mDataList[i].isSelected = false
            }
        }
        notifyDataSetChanged()
    }

    fun updateItems(items: List<T>) {
        mDataList = items.toMutableList()
        notifyDataSetChanged()
    }

    protected abstract fun onBindData(binding: E, position: Int)

    class ViewHolder(private val item: ViewDataBinding) : RecyclerView.ViewHolder(item.root) {

        fun getBinding() = item
    }
}