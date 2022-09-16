package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_reprinter.customerTypeSpinner
import kotlinx.android.synthetic.main.activity_reprinter.reprintAtkEditText
import kotlinx.android.synthetic.main.activity_reprinter.reprintWithScreenButton
import kotlinx.android.synthetic.main.activity_reprinter.showFeedbackScreenCheckbox

class ReprinterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reprinter)

        reprintWithScreenButton.setOnClickListener {
            reprint()
        }
    }

    private fun reprint() {
        val deeplinkReturnScheme = "reprintDeeplinkScheme"

        val uriBuilder = Uri.Builder()
        uriBuilder.authority("reprint")
        uriBuilder.scheme("reprinter-app")
        uriBuilder.appendQueryParameter("SCHEME_RETURN", deeplinkReturnScheme)

        uriBuilder.appendQueryParameter(
            "SHOW_FEEDBACK_SCREEN",
            showFeedbackScreenCheckbox.isChecked.toString()
        )

        if (!reprintAtkEditText.text.isNullOrEmpty()) {
            uriBuilder.appendQueryParameter("ATK", reprintAtkEditText.text.toString())
        }

        val customerType: String? = when (customerTypeSpinner.selectedItemPosition) {
            0 -> "MERCHANT"
            1 -> "CLIENT"
            else -> null
        }
        if (customerType != null) {
            uriBuilder.appendQueryParameter("TYPE_CUSTOMER", customerType)
        }

        startMyActivity(uriBuilder)
    }

    private fun startMyActivity(uriBuilder: Uri.Builder) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.data != null) {
            Log.d("TESTE", intent.data.toString())
            Toast.makeText(this, intent.data.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
