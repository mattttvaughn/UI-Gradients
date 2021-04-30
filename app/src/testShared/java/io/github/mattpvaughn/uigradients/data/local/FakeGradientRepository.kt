package io.github.mattpvaughn.uigradients.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.mattpvaughn.uigradients.data.local.model.Gradient

class FakeGradientRepository(private val throwsError: Boolean) : IGradientRepo {

    var fakeGradientsLiveData = MutableLiveData(emptyList<Gradient>())

    private fun generateFakeGradient(): Gradient {
        return Gradient(name = "Some gradient", colors = listOf("#FFFFFF", "#000000"))
    }

    override suspend fun updateData() {
        if (throwsError) {
            throw Exception()
        }
        fakeGradientsLiveData.postValue((0..100).map { generateFakeGradient() })
    }

    override fun getAllModels(): LiveData<List<Gradient>> {
        return fakeGradientsLiveData
    }

}