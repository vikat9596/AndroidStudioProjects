package com.example.adminfoodexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodexpress.adapter.OrderDetailsAdapter
import com.example.adminfoodexpress.databinding.ActivityOrderDetailsBinding
import com.example.adminfoodexpress.model.OrderDetails

class OrderDetailsActivity : AppCompatActivity() {
    private val binding : ActivityOrderDetailsBinding by lazy {
        ActivityOrderDetailsBinding.inflate(layoutInflater)
    }
    private var userName: String? = null
    private var address: String? = null
    private var phoneNumber: String? = null
    private var totalPrice : String? = null
    private var foodNames : ArrayList<String> = arrayListOf()
    private var foodImages : ArrayList<String> = arrayListOf()
    private var foodQuantity : ArrayList<Int> = arrayListOf()
    private var foodPrices : ArrayList<String> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }

        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        val receiveOrderDetails = intent.getSerializableExtra("UserOrderDetails") as OrderDetails
        receiveOrderDetails?.let { orderDetails ->

                userName = receiveOrderDetails.userName
                foodNames =receiveOrderDetails.foodNames as ArrayList<String>
                foodImages = receiveOrderDetails.foodImages as ArrayList<String>
                foodQuantity =receiveOrderDetails.foodQuantities as ArrayList<Int>
                address = receiveOrderDetails.address
                phoneNumber = receiveOrderDetails.phoneNumber
                foodPrices= receiveOrderDetails.foodPrices as ArrayList<String>
                totalPrice =receiveOrderDetails.totalPrice

                setUserDetails()
                setAdapter()

        }

    }

    private fun setUserDetails() {
        binding.name.text= userName
        binding.address.text= address
        binding.phone.text= phoneNumber
        binding.totalPay.text= totalPrice

    }
    private fun setAdapter() {
        binding.orderDetailsRecyclerView.layoutManager= LinearLayoutManager(this)
        val adapter = OrderDetailsAdapter(this,foodNames,foodImages,foodPrices,foodQuantity)
        binding.orderDetailsRecyclerView.adapter= adapter
    }
}