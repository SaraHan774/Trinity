package com.august.trinity.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestApp5() {

}


interface FileReaderScope {
    fun onFileOpen()
    fun onFileClosed()
    fun onLineRead(line: String)
}

object Scope: FileReaderScope {
    override fun onFileOpen() {
        TODO("Not yet implemented")
    }

    override fun onFileClosed() {
        TODO("Not yet implemented")
    }

    override fun onLineRead(line: String) {
        TODO("Not yet implemented")
    }

}

@Composable
fun FileReader(path: String, content: @Composable FileReaderScope.(path: String) -> Unit) {
    Scope.content(path = path)
}