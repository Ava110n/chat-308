package org.example.project.Client

import Connection.IOHandler
import Connection.Message
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateListOf
import java.net.Socket


@Composable
@Preview
fun chat(socket: Socket) {
    val scrollState = rememberScrollState()
    val handler = IOHandler(socket)

    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }

    var userName by remember { mutableStateOf("") }
    var isLoggedIn by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {

        if (!isLoggedIn) {
            Row(modifier = Modifier.weight(1f).fillMaxSize()) {
                TextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Введите ваше имя") },
                    modifier = Modifier.weight(1f)
                )
                Button(onClick = { if (userName.isNotBlank()) isLoggedIn = true }) {
                    Text("Войти")
                }
            }
        } else {
            Row(modifier = Modifier.weight(10f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(5f)
                        .verticalScroll(scrollState)
                ) {
                    SelectionContainer {
                        Column {
                            Text("Вы вошли как: $userName")
                            for (m in messages) {
                                Row { (m.toString()) }
                            }
                        }
                    }
                }
            }



            Row(modifier = Modifier.weight(1f).fillMaxSize()) {
                Column(modifier = Modifier.weight(5f)) {
                    TextField(
                        value = message,
                        onValueChange = { message = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    TextButton(
                        modifier = Modifier.fillMaxSize(),
                        onClick = {
                            if (message.isNotBlank()) {
                                val msg = Message(message,userName)
                                messages.add(msg)

                                handler.write(msg)

                                message = ""
                            }
                        }
                    ) {
                        Text("Отправить")
                    }
                }
            }
        }
    }
}
