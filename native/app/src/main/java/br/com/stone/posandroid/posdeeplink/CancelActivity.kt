package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast

class CancelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel)
    }

    fun btnCancel(view: View) {
        val deepLinkReturnScheme = "deeplinktest"
        val amount = findViewById<EditText>(R.id.editAmount).text.toString()
        val atk = findViewById<EditText>(R.id.editAtk).text.toString()
        val isEditableAmount = findViewById<Switch>(R.id.isEditableAmount).isChecked

        val uriBuilder = Uri.Builder()
        uriBuilder.authority("cancel")
        uriBuilder.scheme("cancel-app")
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_RETURN_SCHEME, deepLinkReturnScheme)

        if (atk.isBlank().not()) {
            uriBuilder.appendQueryParameter(BUNDLE_EXTRA_ATK, atk)
        }

        if (amount.isBlank().not()) {
            uriBuilder.appendQueryParameter(BUNDLE_EXTRA_AMOUNT, amount)
        }

        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_EDITABLE_AMOUNT, isEditableAmount.toString())

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        try {
            Log.i("onNewIntent", intent?.data.toString())
            if (intent?.data != null) {
                Toast.makeText(this, intent.data.toString(), Toast.LENGTH_LONG).show()
                Log.i("DeeplinkCancel Response", intent.data.toString())
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("Deeplink error", e.toString())
        }
    }

    companion object {
        const val BUNDLE_EXTRA_RETURN_SCHEME = "returnscheme"
        const val BUNDLE_EXTRA_ATK = "atk"
        const val BUNDLE_EXTRA_AMOUNT = "amount"
        const val BUNDLE_EXTRA_EDITABLE_AMOUNT = "editable_amount"
    }
}