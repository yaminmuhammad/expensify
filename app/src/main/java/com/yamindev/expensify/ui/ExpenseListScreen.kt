package com.yamindev.expensify.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yamindev.expensify.data.Expense
import com.yamindev.expensify.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseListScreen(navController: NavController, viewModel: ExpenseViewModel) {
    val expenses by viewModel.allExpenses.collectAsState(initial = emptyList())
    val totalExpenses by viewModel.totalExpenses.collectAsState(initial = 0.0)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Expensify") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_expense") }) {
                Icon(Icons.Filled.Add, "Add new expense")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Total Pengeluaran:",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Rp ${String.format("%.2f", totalExpenses ?: 0.0)}",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (expenses.isEmpty()) {
                Text("Belum ada pengeluaran. Tambahkan yang baru!")
            } else {
                LazyColumn {
                    items(expenses) { expense ->
                        ExpenseItem(expense = expense, onDeleteClick = { viewModel.deleteExpense(expense) })
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onDeleteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = expense.title, style = MaterialTheme.typography.titleMedium)
                Text(text = expense.category, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Text(text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(expense.date)), style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Text(text = "Rp ${String.format("%.2f", expense.amount)}", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Filled.Delete, "Delete expense", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}