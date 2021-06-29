package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class CancelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel)
    }

    fun btnCancel(view: View) {
        val amount = findViewById<EditText>(R.id.editAmount).text.toString()
        val atk = findViewById<EditText>(R.id.editAtk).text.toString()

        val deepLinkReturnScheme = "deeplinktest"
        val uriBuilder = Uri.Builder()
        uriBuilder.authority("cancel")
        uriBuilder.scheme("cancel-app")
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_RETURN_SCHEME, deepLinkReturnScheme)
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_ATK, atk)
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_AMOUNT, amount)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("TESTE", intent?.data.toString())
        Toast.makeText(this, "OK", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val BUNDLE_EXTRA_RETURN_SCHEME = "returnscheme"
        const val BUNDLE_EXTRA_ATK = "atk"
        const val BUNDLE_EXTRA_AMOUNT = "amount"
    }
}