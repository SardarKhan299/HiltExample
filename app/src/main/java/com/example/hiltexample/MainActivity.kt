package com.example.hiltexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Field Injection Example..
    @Inject
    lateinit var myClasss : SomeClass

    @Inject
    lateinit var myActivity: myActivity

    // this dependency injection won't works because its a lower order dependency than activity.
    // Fragment scope is less than activity scope.
    // Lower scope cannot be injected in to higher scope in tier system.
//    @Inject
//    lateinit var myFragment1: myFragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomething()}")
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomeOtherThing()} ")
        Log.d(MainActivity::class.simpleName, "onCreate: ${myActivity.doSomethingOther()}")
    }
}

// Constructor Injection...
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
@Singleton // make this dependency live as long as application is alive..
class SomeOtherClass @Inject constructor() {
    fun doSomethingOther():String{
        return "sardar khan"
    }
}

@ActivityScoped // make this dependency live as long as Activity is alive..
class myActivity @Inject constructor() {
    fun doSomethingOther():String{
        return "My Activity is running"
    }
}

@FragmentScoped // make this dependency live as long as Fragment is alive..
class myFragment1 @Inject constructor() {
    fun doSomethingOther():String{
        return "My Activity is running"
    }
}

@AndroidEntryPoint
class myFragment: Fragment(){
    // Field Injection
    @Inject lateinit var someClass: SomeClass

}

