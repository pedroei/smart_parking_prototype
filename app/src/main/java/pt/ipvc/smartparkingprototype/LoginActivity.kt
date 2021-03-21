package pt.ipvc.smartparkingprototype

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide() // hide action bar
        } catch (e: NullPointerException) {}
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener{
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if ( email == "admin" && password == "admin") {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(EXTRA_MESSAGE, email)
                }
                startActivity(intent)
            } else Toast.makeText(this, "User $email with $email does not exists", Toast.LENGTH_SHORT).show()
        }
    }
}