package com.example.bankapp.model

class UserModel(
    private var name: String,
    private var account_number: String,
    private var phone_number: String,
    private var ifsc_code: String,
    private var balance: Int,
    private var email: String
    ){

       fun getName() : String{
              return name
       }

       fun setName(name : String){
              this.name = name
       }

       fun getAccountNumber() : String{
              return account_number
       }

       fun setAccountNumber(account_number: String){
              this.account_number = account_number
       }

       fun getPhoneNumber(): String{
              return phone_number
       }

       fun setPhoneNumber(phone_number: String){
              this.phone_number = phone_number
       }

       fun getIfscCode(): String{
              return ifsc_code
       }

       fun setIfscCode(ifsc_code: String){
              this.ifsc_code = ifsc_code
       }

       fun getBalance(): Int{
              return balance
       }

       fun setBalance(balance: Int){
              this.balance = balance
       }

       fun getEmail(): String{
              return email
       }

       fun setEmail(email: String){
              this.email = email
       }
}