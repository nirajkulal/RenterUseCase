package com.renter.fleetdashbaord.application.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.renter.fleetdashbaord.ui.theme.FleetDashbaordTheme

class MainActivity : ComponentActivity() {

    val viewModel: FleetViewmodel by lazy {
        FleetViewmodel()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FleetDashbaordTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier
                            .padding(innerPadding),
                        onLogin = { username ->
                            viewModel.setEvent(FleetViewmodel.FleetEvent.FetchUserData(username))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLogin: (String) -> Unit
) {
    // Simple UI for login
    Text(text = "Login Screen", modifier = modifier)
    // Call onLogin with a sample username for demonstration
    onLogin("sampleUser")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FleetDashbaordTheme {
        Greeting("Android")
    }
}