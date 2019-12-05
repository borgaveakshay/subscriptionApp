package com.example.mp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mp.models.LocationDTO
import com.example.mp.recyclerviews.AreaAdapter
import com.example.mp.recyclerviews.BaseRecyclerAdapter
import com.example.mp.recyclerviews.NewsPaperAdapter
import com.example.mp.recyclerviews.R
import com.example.mp.recyclerviews.databinding.FragmentRegistrationBinding
import com.example.mp.retrofit.NetworkApi
import com.subscribe.snpa.SNPA.models.AreaDTO
import com.subscribe.snpa.SNPA.models.CustomerDTO
import com.subscribe.snpa.SNPA.models.NewsPaperDTO
import com.subscribe.snpa.SNPA.models.VendorDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class Registration : LocationFragment(), BaseRecyclerAdapter.RecycleMultiSelectListener<NewsPaperDTO>,
    BaseRecyclerAdapter.RecycleSingleSelectListener<AreaDTO> {

    private lateinit var mFragmentRegistrationBinding: FragmentRegistrationBinding

    private val mCustomerDTO: CustomerDTO = CustomerDTO()
    private val mVendorDTO: VendorDTO = VendorDTO()

    private var isVendorRegistration = false

    private lateinit var mNetworkApi: NetworkApi

    private lateinit var mRadioGroup: RadioGroup

    private lateinit var mVendorRadio: RadioButton

    private lateinit var mRegister: Button

    private lateinit var mNewsRecyclerView: RecyclerView

    private lateinit var mAreaRecyclerView: RecyclerView

    private val mNewsPaperAdapter: NewsPaperAdapter = NewsPaperAdapter()

    private val mAreaAdapter: AreaAdapter = AreaAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mFragmentRegistrationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registration, container, false)

        mFragmentRegistrationBinding.customer = mCustomerDTO
        mFragmentRegistrationBinding.vendor = mVendorDTO
        mFragmentRegistrationBinding.isVendor = isVendorRegistration
        mView = mFragmentRegistrationBinding.root
        initUI()

        mNetworkApi = NetworkApi.getService()

        setUserTypeSelectionListener()
        setRegisterClickListener()

        getAreas()

        return mView
    }

    private fun initUI() {

        mRegister = mView.findViewById(R.id.register)
        mRadioGroup = mView.findViewById(R.id.radioGroup1)
        mVendorRadio = mView.findViewById(R.id.vendorRegistration)

        setupNewsRecyclerView()
        setupAreaRecyclerView()
    }

    private fun setupNewsRecyclerView() {
        mNewsRecyclerView = mView.findViewById(R.id.newsPaperRecycleView)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mNewsRecyclerView.layoutManager = layoutManager
        mNewsRecyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        mNewsRecyclerView.adapter = mNewsPaperAdapter
        mNewsRecyclerView.isNestedScrollingEnabled = false
        mNewsPaperAdapter.mRecycleMultiSelectListener = this
    }


    private fun setupAreaRecyclerView() {

        mAreaRecyclerView = mView.findViewById(R.id.areaRecyclerView)
        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAreaRecyclerView.layoutManager = layoutManager
        mAreaRecyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        mAreaRecyclerView.adapter = mAreaAdapter
        mAreaRecyclerView.isNestedScrollingEnabled = false
        mAreaAdapter.mRecycleSingleSelectListener = this
    }


    private fun setUserTypeSelectionListener() {
        mRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            isVendorRegistration = checkedId == mVendorRadio.id
            mFragmentRegistrationBinding.isVendor = isVendorRegistration
            if (isVendorRegistration) {
                getNewsPaperList()
            }
        }
    }


    private fun setRegisterClickListener() {
        mRegister.setOnClickListener { _ ->

            if (isVendorRegistration)
                registerVendor()
            else
                registerCustomer()
        }

    }

    private fun getNewsPaperList() {

        mCompositeDisposable.add(
            mNetworkApi.getNewsPapers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it?.let { response ->
                        response.result?.let { list ->
                            mNewsPaperAdapter.updateItems(list)
                        }

                    }

                }, { error ->

                    println("Error: ${error.message}")
                    // Keep it blank

                })
        )
    }

    private fun registerVendor() {
        showProgress(true)
        mCompositeDisposable.add(
            mNetworkApi.registerVendor(mVendorDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        showProgress(false)
                        if (it.statusCode == 200)
                            view?.let { view ->
                                Navigation.findNavController(view).navigate(R.id.action_registration_to_loginFragment)
                            }
                        else
                            Toast.makeText(
                                context,
                                "Something went wrong!",
                                Toast.LENGTH_LONG
                            ).show()
                    }

                },
                    { error ->
                        showProgress(false)
                        Toast.makeText(context, " Exception: ${error.message}", Toast.LENGTH_LONG)
                            .show()

                    })
        )

    }


    private fun registerCustomer() {
        showProgress(true)
        mCompositeDisposable.add(
            mNetworkApi.registerCustomer(mCustomerDTO)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    response?.let {
                        showProgress(false)
                        if (it.statusCode == 200)
                            view?.let { view ->
                                Navigation.findNavController(view).navigate(R.id.action_registration_to_loginFragment)
                            }
                        else
                            Toast.makeText(
                                context,
                                "Something went wrong!",
                                Toast.LENGTH_LONG
                            ).show()
                    }

                },
                    { error ->
                        showProgress(false)
                        Toast.makeText(context, " Exception: ${error.message}", Toast.LENGTH_LONG)
                            .show()

                    })
        )
    }


    private fun getAreas() {

        mCompositeDisposable.add(
            mNetworkApi.locateArea()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->

                    response?.result?.let { list ->

                        mAreaAdapter.updateItems(list)

                    }

                },
                    {
                        println("Error :${it.message}")

                    })
        )
    }

    override fun locationAvailable(locationDTO: LocationDTO) {
        mCustomerDTO.address = locationDTO.address
        mCustomerDTO.latitude = locationDTO.lat
        mCustomerDTO.longitude = locationDTO.long
    }


    override fun onMultipleSelect(selectedList: List<NewsPaperDTO>) {
        mVendorDTO.newsPapers = selectedList.toSet()
    }

    override fun onSingleSelect(item: AreaDTO) {
        if (isVendorRegistration) {

            mVendorDTO.areaDTO = item
        } else {
            mCustomerDTO.areaDTO = item
        }
    }
}
