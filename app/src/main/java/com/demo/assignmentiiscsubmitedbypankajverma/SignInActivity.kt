package com.demo.assignmentiiscsubmitedbypankajverma

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.assignmentiiscsubmitedbypankajverma.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        binding.newRegisterSignIn.setOnClickListener{
            val intent = Intent(this@SignInActivity,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.signInBtn.setOnClickListener(View.OnClickListener {
            val emails = binding.signInEmail.text.toString()
            val passwords = binding.signInPassword.text.toString()
            if (emails.isNotEmpty() && passwords.isNotEmpty()) {
                auth.signInWithEmailAndPassword(emails, passwords).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this@SignInActivity,ResultActivity::class.java)
                        Toast.makeText(applicationContext, "SignIn Successful", Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Wrong Email or Password ${it.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
            else {
                Toast.makeText(applicationContext, "Empty field are not allowed", Toast.LENGTH_LONG).show()
            }
        })


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}