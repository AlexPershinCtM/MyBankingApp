package com.alexpershin.core.mapper

interface BaseModelMapper <S,T> {
    fun map(source : S) : T
}
