package com.example.hiringlistdemo.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hiringlistdemo.Models.Item
import com.example.hiringlistdemo.Services.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ItemListViewModel : ViewModel() {
    private val apiClient = ApiClient()

    private val _fetchedItems = MutableStateFlow<Map<Int, List<Item>>>(emptyMap())
    val fetchedItems: StateFlow<Map<Int, List<Item>>> get() = _fetchedItems

    fun queryAllItems() {
        viewModelScope.launch {
            try {
                val items = apiClient.queryAllItems()
                val filteredSortedItems = items
                    .filter { !it.name.isNullOrBlank() }
                    // Name is actually String, sort by Integer inside or just String?
                    .sortedWith(compareBy({ it.listId }, { it.name } ))
                    .groupBy { it.listId }

                _fetchedItems.value = filteredSortedItems
                println(fetchedItems)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}