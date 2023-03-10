package com.tama.syarah.change_language

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tama.domain.usecases.languague_uscase.GetLanguageUseCase
import com.tama.domain.usecases.languague_uscase.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeLanguageViewModel @Inject constructor(
    val setLanguageUsecase: SetLanguageUseCase,
    getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {
    private val _arabicSelection = MutableLiveData(true)
    private val _englishSelection = MutableLiveData(false)
    val englishSelection = MediatorLiveData<Boolean>()
    val arabicSelection = MediatorLiveData<Boolean>()

    init {
        englishSelection.addSource(_englishSelection) {
            englishSelection.value = it
        }
        arabicSelection.addSource(_arabicSelection) {
            arabicSelection.value = it
        }
        when (getLanguageUseCase.invoke()) {
            "en" -> {
                _englishSelection.value = true
                _arabicSelection.value = false
            }
            else -> {
                _arabicSelection.value = true
                _englishSelection.value = false
            }
        }
    }


    fun onClickSelectionLanguage(languageType: LanguageType) {
        if (languageType == LanguageType.ENGLISH) {
            _englishSelection.value = true
            _arabicSelection.value = false
            setLanguageUsecase.invoke("en")
        } else if (languageType == LanguageType.ARABIC) {
            _englishSelection.value = false
            _arabicSelection.value = true
            setLanguageUsecase.invoke("ar")
        }
    }
}

enum class LanguageType() {
    ENGLISH, ARABIC
}