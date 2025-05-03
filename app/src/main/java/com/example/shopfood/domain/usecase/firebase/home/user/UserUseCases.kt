package com.example.shopfood.domain.usecase.firebase.home.user

data class UserUseCases(
    val getUser: GetUserUseCase,
    val updateUser: UpdateUserUseCase,
    val addAddress: AddAddressUseCase,
    val getAddresses: GetAddressesUseCase,
    val deleteAddress: DeleteAddress
)
