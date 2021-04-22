package com.example.myapplication.data

import jp.team.eworks.e_core_library.data.BaseSharedPreferences

object SampleSharedPreferences: BaseSharedPreferences() {
    private enum class PrefName(override val nameKey: String): BaseSharedPreferences.PrefName {
        Sample("sample")
    }

    private enum class Pref(override val prefName: PrefName, override val key: String):
        BaseSharedPreferences.Pref<PrefName> {

        SampleInt(PrefName.Sample, "SampleInt"),
    }

    var sampleInt: Int
        get() = getInt(Pref.SampleInt, 0)
        set(value) = setInt(Pref.SampleInt, value)
}
