package com.harnet.dogbreeds.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.harnet.dogbreeds.R

class MainActivity : AppCompatActivity() {
    // back arrow
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        //for back arrow
//        navController = Navigation.findNavController(this, R.id.fragment)
//        NavigationUI.setupActionBarWithNavController(this, navController)
//    }
//
//    //for back arrow
//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, null)
//    }
    }
}