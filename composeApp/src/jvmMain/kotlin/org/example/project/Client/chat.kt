package org.example.project.Client

import Connection.IOHandler
import Connection.Message
import Connection.User
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import java.net.Socket
import kotlinx.coroutines.*

@Composable
@Preview
fun chat(socket: Socket){
    val handler = remember(socket) { IOHandler(socket) }
    val scope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
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
                Button(onClick = { if (userName.isNotBlank()) {isLoggedIn = true; handler.writeUserName(userName)}}) {
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
                                Row { Text(m.toString()) }
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
                                val msg = Message(message, User(userName))
                                //messages.add(msg)

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

    LaunchedEffect(handler) {
        withContext(Dispatchers.IO) {
            while(isActive) {
                try {
                    val incoming = handler.read()
                    if (incoming != null) {
                        withContext(Dispatchers.Main){
                            messages.add(Message(incoming))
                            println(messages.last())
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }
            }
        }

    }


}
