package com.example.mp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import com.subscribe.snpa.SNPA.models.CustomerDTO
import com.subscribe.snpa.SNPA.models.VendorDTO

@Dao
interface DatabaseDao {

    @Insert
    fun customerRegistered(customerDTO: CustomerDTO): LiveData<Boolean>

    fun getCustomer(email: String): CustomerDTO?

}

@Database(entities = [CustomerDTO::class, VendorDTO::class], version = 1)
abstract class Database {

    abstract fun getDatabaseDao(): DatabaseDao
}