package com.yamindev.expensify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.yamindev.expensify.data.AppDatabase
import com.yamindev.expensify.ui.AddExpenseScreen
import com.yamindev.expensify.ui.ExpenseListScreen
import com.yamindev.expensify.ui.theme.ExpensifyTheme
import com.yamindev.expensify.viewmodel.ExpenseViewModel
import com.yamindev.expensify.viewmodel.ExpenseViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "expense-database"
        ).build()
        val expenseDao = db.expenseDao()
        val viewModel = ExpenseViewModel(expenseDao)

        setContent {
            ExpensifyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun ExpenseApp(viewModel: ExpenseViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "expense_list") {
        composable("expense_list") {
            ExpenseListScreen(navController = navController, viewModel = viewModel)
        }
        composable("add_expense") {
            AddExpenseScreen(navController = navController, viewModel = viewModel)
        }
    }
}