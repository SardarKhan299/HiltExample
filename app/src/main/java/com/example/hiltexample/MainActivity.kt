package com.example.hiltexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import javax.inject.Qualifier
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
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomeThingFromInterface()}")
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomeThing1()}")
        Log.d(MainActivity::class.simpleName, "onCreate: ${myClasss.doSomeThing2()}")
    }
}

// Constructor Injection...
// @Inject make it available at compile time.
// issue of Adding Interface in constructor injection...
// solve this using @Bind or @Provides...
class SomeClass @Inject constructor(private val someOtherClass: SomeOtherClass,
                                    private val someInterfaceImpl: someInterface,
                                    @Impl1 private val someInterfaceImpl1: someInterface,
                                    @Impl2 private val someInterfaceImpl2: someInterface) {

        fun doSomething():String{
            return "sardar"
        }
    fun doSomeOtherThing():String{
        return someOtherClass.doSomethingOther()
    }
    fun doSomeThingFromInterface():String{
        return someInterfaceImpl.getAThing()
    }

    fun doSomeThing1():String{
        return someInterfaceImpl1.getAThing()
    }
    fun doSomeThing2():String{
        return someInterfaceImpl2.getAThing()
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

interface someInterface{
    fun getAThing():String
}

class someInterfaceImpl @Inject constructor() :someInterface{
    override fun getAThing(): String  =  "Return Some thing from Interface"
}

class someInterfaceImpl1 @Inject constructor() :someInterface{
    override fun getAThing(): String  =  "Return Some thing1 from Interface"
}

class someInterfaceImpl2 @Inject constructor() :someInterface{
    override fun getAThing(): String  =  "Return Some thing2 from Interface"
}

//// Use of @Bind..
//@InstallIn(ApplicationComponent::class) // Application Level Scope of this module...
//@Module
//abstract class myModuleClass{
//    @Singleton
//    @Binds
//    abstract fun bindSomeDependency(someInterfaceImpl: someInterfaceImpl):someInterface
//
//    // This is the issue of Bind it cannot cover all the scenarios...///
////    @Singleton
////    @Binds
////    abstract fun bindGson(gson: Gson):Gson
//
//}



// Use of Provides.
@InstallIn(ApplicationComponent::class) // Application Level Scope of this module...
@Module
class myModuleClassProvides{

    @Singleton
    @Provides
    fun provideSomeInterface():someInterface{
        return someInterfaceImpl()
    }

    @Impl1
    @Singleton
    @Provides
    fun provideSomeInterface1():someInterface{
        return someInterfaceImpl1()
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomeInterface2():someInterface{
        return someInterfaceImpl2()
    }

    @Singleton
    @Provides
    fun provideGson():Gson{
        return Gson()
    }

}



// These qualifiers use to identify the Implementation of The Same Interface.
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2

