package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.stone.posandroid.posdeeplink.databinding.ActivityReprinterBinding

class ReprinterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReprinterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReprinterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reprintWithScreenButton.setOnClickListener {
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
            binding.showFeedbackScreenCheckbox.isChecked.toString()
        )

        if (!binding.reprintAtkEditText.text.isNullOrEmpty()) {
            uriBuilder.appendQueryParameter("ATK", binding.reprintAtkEditText.text.toString())
        }

        val customerType: String? = when (binding.customerTypeSpinner.selectedItemPosition) {
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
