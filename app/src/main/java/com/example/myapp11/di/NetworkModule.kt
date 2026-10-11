package com.example.myapp11.di

import com.example.myapp11.data.remote.api.IceAndFireApi
import com.example.myapp11.data.repository.BookRepositoryImpl
import com.example.myapp11.data.repository.CharacterRepositoryImpl
import com.example.myapp11.data.repository.HouseRepositoryImpl
import com.example.myapp11.domain.repository.BookRepository
import com.example.myapp11.domain.repository.CharacterRepository
import com.example.myapp11.domain.repository.HouseRepository
import com.example.myapp11.domain.usecase.GetBookByIdUseCase
import com.example.myapp11.domain.usecase.GetBooksUseCase
import com.example.myapp11.domain.usecase.GetCharacterByIdUseCase
import com.example.myapp11.domain.usecase.GetCharactersUseCase
import com.example.myapp11.domain.usecase.GetHouseByIdUseCase
import com.example.myapp11.domain.usecase.GetHousesUseCase
import com.example.myapp11.presentation.books.BookDetailViewModel
import com.example.myapp11.presentation.books.BookViewModel
import com.example.myapp11.presentation.characters.CharacterViewModel
import com.example.myapp11.presentation.details.CharacterDetailViewModel
import com.example.myapp11.presentation.houses.HouseDetailViewModel
import com.example.myapp11.presentation.houses.HouseViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://anapioficeandfire.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<IceAndFireApi> {
        get<Retrofit>().create(IceAndFireApi::class.java)
    }

    single<CharacterRepository> { CharacterRepositoryImpl(get()) }
    single<BookRepository> { BookRepositoryImpl(get()) }
    single<HouseRepository> { HouseRepositoryImpl(get()) }

    factory { GetCharactersUseCase(get()) }
    factory { GetCharacterByIdUseCase(get()) }
    factory { GetBooksUseCase(get()) }
    factory { GetBookByIdUseCase(get()) }
    factory { GetHousesUseCase(get()) }
    factory { GetHouseByIdUseCase(get()) }

    viewModelOf(::CharacterViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::BookViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::HouseViewModel)
    viewModelOf(::HouseDetailViewModel)
}
