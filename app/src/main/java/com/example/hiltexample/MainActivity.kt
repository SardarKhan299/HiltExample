package com.example.hiltexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Field Injection Example..
    @Inject
    lateinit var myClasss : SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomething()}")
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomeOtherThing()} ")
    }
}

// @Inject make it available at compile time.
class SomeClass @Inject constructor(private val someOtherClass: SomeOtherClass) {
        fun doSomething():String{
            return "sardar"
        }
    fun doSomeOtherThing():String{
        return someOtherClass.doSomethingOther()
    }
}

// First Dagger Build this instance and pass that instance to the SomeClass and then create instance of someClass and inject it to mainActivity .
class SomeOtherClass @Inject constructor() {
    fun doSomethingOther():String{
        return "sardar khan"
    }
}