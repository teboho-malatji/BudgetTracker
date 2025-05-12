package com.example.budgettracker

data class Category(val name: String,
                    val amount: Double,
                    val transactions: List<Transaction>,
                    var isExpanded: Boolean = false){

}
