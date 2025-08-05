package com.example.prjjetpackbuttonsbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prjjetpackbuttonsbasics.ui.theme.PrjJetpackButtonsBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrjJetpackButtonsBasicsTheme {
                LoginApp()
                //EchoChamber()
                //SecretMessage()
                }
            }
        }
    }

@Composable
fun EchoChamber(modifier: Modifier = Modifier) {
    //create state to hold the text
    var text by remember { mutableStateOf("") }
    Column(modifier = modifier.padding(16.dp)) {
        //input field
        TextField(
            value = text, //textfield always displays the current state
            onValueChange = { newText -> //when the user types, this lamda is called
                text = newText //update state to new text
            },
            label = { Text("Type something ...") } //helpful hint for a user
        )

        Spacer(modifier = Modifier.padding(16.dp))

        //text composable simply reads the state
        Text(text = "You are typing: $text")

    }
}

@Composable
fun SecretMessage(modifier: Modifier = Modifier) {
    //state to track visibility
    var showMessage by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Button(onClick = {showMessage = !showMessage}) {
            //we can even make the button text change
            Text(if (showMessage) "Hide Message" else "Show Message")
        }

        Spacer(modifier = Modifier.height(16.dp))

        //CORE LOGIC
        //if showMessage is true, add the text composable to the gui
        //else if it becomes false, Compose removes ir automatically

        if (showMessage) {
            Text(text = "This is a secret message, congratulations goblin")
        }
    }

}

//LOGIN APP BELOW

//we can use an object to define our routes to avoid magic strings
object Screen {
    const val Login = "Login"
    const val Welcome = "Welcome"
}

@Composable
fun LoginApp() {
    //state to track the current screen
    var currentScreen by remember { mutableStateOf(Screen.Login) }
    //state to hold the username after login
    var loggedUsername by remember { mutableStateOf("") }

    //when the block acts as our navigator
    when (currentScreen) {
        Screen.Login -> {
            LoginForm(
                onLoginSuccess = { username ->
                    //when login successful
                    //save the username
                    loggedUsername = username
                    //change the screen to welcome screen
                    currentScreen = Screen.Welcome
                }
            )
        }

        Screen.Welcome -> {
            WelcomeScreen(username = loggedUsername)
        }
    }
}

@Composable
fun LoginForm(onLoginSuccess: (username: String) -> Unit, modifier: Modifier = Modifier) { //event flows up
    //2 state variables to hold input
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val isButtonEnabled = username.isNotEmpty() && password.isNotEmpty() //ensure that fields are filled in

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                //when clicked, callback with username
                onLoginSuccess(username)
            },
            enabled = isButtonEnabled
        ) {
            Text("Login")
        }
    }
}

@Composable
fun WelcomeScreen(username: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome, $username!",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
