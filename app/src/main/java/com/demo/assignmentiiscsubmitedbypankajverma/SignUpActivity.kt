package com.demo.assignmentiiscsubmitedbypankajverma

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.demo.assignmentiiscsubmitedbypankajverma.databinding.ActivitySignUpBinding
import com.google.common.base.Verify
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()

        binding.alredyRegisterText.setOnClickListener{
            val intent = Intent(this@SignUpActivity,SignInActivity::class.java)
            startActivity(intent)
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, OtpActivity::class.java))
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext, Verify::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }

            binding.SignUpBtn.setOnClickListener(View.OnClickListener {
                val name = binding.signupName.text.toString()
                val email = binding.signUpEmail.text.toString()
                val password = binding.signupPassword.text.toString()
                val confirmpassword = binding.signUpconfirmPassword.text.toString()
                if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()) {
                    if (password == confirmpassword) {
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                            if(it.isSuccessful) {
                                Toast.makeText(this@SignUpActivity, "SignUp Successful", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@SignUpActivity, OtpActivity::class.java)
                                startActivity(intent)
                            }
                            else {
                                Toast.makeText(this@SignUpActivity, "Already Register", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this@SignUpActivity, "Wrong Email or Password", Toast.LENGTH_LONG).show()
                    }
                }
                else {
                    Toast.makeText(this@SignUpActivity,"Empty field are not allowed", Toast.LENGTH_LONG).show()
                }
            })




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun login() {
        val mobileNumber=findViewById<EditText>(R.id.signUpPhone)
        var number=mobileNumber.text.toString().trim()

        if(!number.isEmpty()){
            number="+91"+number
            sendVerificationcode (number)
        }else{
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}