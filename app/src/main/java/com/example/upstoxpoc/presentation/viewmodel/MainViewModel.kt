package com.example.upstoxpoc.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.upstoxpoc.data.model.CryptoItem
import com.example.upstoxpoc.domain.repository.CryptoCoinRepository
import com.example.upstoxpoc.domain.utils.ApiState
import com.example.upstoxpoc.domain.repository.OldCryptoCoinRepository
import com.example.upstoxpoc.domain.utils.Converter
import com.example.upstoxpoc.domain.utils.FilterType
import com.example.upstoxpoc.domain.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject constructor(private val mainRepository: CryptoCoinRepository) : ViewModel() {

    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)
    private val _viewState = MutableStateFlow(ViewState())
    val viewState: StateFlow<ViewState> = _viewState.asStateFlow()
    private var _originalList = listOf<CryptoItem>()

    fun callCryptoApi() = viewModelScope.launch {
        mainRepository.getCryptoCoins()
            .onStart {
                response.value = ApiState.Loading
            }.catch {
                response.value = ApiState.Failure(it)
            }.collect { success ->
                response.value = ApiState.Success(success)

                success.body()?.let { list ->
                    _viewState.update {
                        _originalList = list
                        it.copy(
                            filterList = list
                        )
                    }
                }
            }
    }

    fun getFilterListByName(list: List<CryptoItem>, searchItem: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val filteredItems = Converter.filterCryptoItemsByNamePrefix(list, searchItem)

                _viewState.update {
                    it.copy(
                        filterList = filteredItems
                    )
                }
            }
        }
    }

    fun getFilterListByOption(selectedFilters: List<FilterType>) {

        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val filteredItems = Converter.filterCryptoItems(_originalList, selectedFilters)

                _viewState.update {
                    it.copy(
                        filterList = filteredItems
                    )
                }
            }
        }
    }
}

