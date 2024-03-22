package com.august.trinity.interop

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.august.trinity.ui.theme.TrinityTheme


@Composable
fun DropdownDemo() {
    val dropdownUIM = DropdownUIM(
        selectedItem = DropdownData(title = "Title", data = "Data"),
        itemList = listOf(
            DropdownData(title = "Title1", data = "Data"),
            DropdownData(title = "Title2", data = "Data"),
            DropdownData(title = "Title3", data = "Data"),
        ),
        isExpanded = false,
    )


    MyDropdownMenu(
        modifier = Modifier,
        dropdownUIM = dropdownUIM,
        onItemSelected = {
            // viewModel ...
            Log.d("===", "DropdownDemo: $it")
        }
    )
}

@Composable
fun <T> MyDropdownMenu(
    modifier: Modifier = Modifier,
    dropdownUIM: DropdownUIM<T>,
    onItemSelected: (DropdownData<T>) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Column {
        Button(onClick = { isExpanded = true }) {
            Text(text = "Dropdown")
        }

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier.background(Color.Transparent),
        ) {
            dropdownUIM.itemList.onEach { item: DropdownData<T> ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(color = Color.Blue)
                        .clip(RoundedCornerShape(8.dp))
                        .border(width = 1.dp, color = Color.Red),
                    onClick = {
                        isExpanded = false
                        onItemSelected(item)
                    }, text = {
                        Text(text = item.title)
                    }
                )
            }
        }
    }
}

data class DropdownUIM<T>(
    val selectedItem: DropdownData<T>,
    val itemList: List<DropdownData<T>>,
    val isExpanded: Boolean,
)

data class DropdownData<T>(
    val title: String,
    val data: T? = null,
)

@Preview
@Composable
fun MyDropdownMenuPreview() {
    TrinityTheme {
//        MyDropdownMenu()
    }
}