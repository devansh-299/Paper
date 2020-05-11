package com.devansh.paper.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.devansh.paper.R
import com.devansh.paper.view.fragment.NotesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_holder, NotesFragment())
        fragmentTransaction.commit()
    }

}
