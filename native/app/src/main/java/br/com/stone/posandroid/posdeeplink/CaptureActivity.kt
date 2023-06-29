package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.stone.posandroid.posdeeplink.databinding.ActivityCaptureBinding

class CaptureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCaptureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCaptureBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        binding.buttonCapture.setOnClickListener {
            launchCapture()
        }
    }

    private fun launchCapture() {

        val returnScheme = getString(R.string.scheme_return_capture)

        val amount = binding.editAmount.text.toString()
        val atk = binding.editAtk.text.toString()
        val amountEditable = if(binding.isEditableAmount.isChecked){
            "1"
        }else
            "0"

        val isPreAuthorization = binding.isPreAuthorization.isChecked

        val uriBuilder = Uri.Builder()
        uriBuilder.authority("capture")
        uriBuilder.scheme("capture-app")

        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_RETURN_SCHEME, returnScheme)

        if(amount.isEmpty()){
            return
        }

        if(atk.isEmpty()){
            return
        }

        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_AMOUNT, amount)
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_ATK, atk)
        uriBuilder.appendQueryParameter(BUNDLE_EXTRA_EDITABLE_AMOUNT, amountEditable)
        if(isPreAuthorization){
            uriBuilder.appendQueryParameter(BUNDLE_EXTRA_MANUAL_CAPTURE, "1")
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        try {
            Log.d("CaptureActivity", "deeplink: ${intent?.data.toString()}")
            if (intent?.data != null) {
                Toast.makeText(this, intent.data.toString(), Toast.LENGTH_LONG).show()
                Log.d("CaptureActivity", "deeplink response: ${intent.data.toString()}")
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("CaptureActivity", "error $e")
        }
    }

    companion object {
        const val BUNDLE_EXTRA_RETURN_SCHEME = "return_scheme"
        const val BUNDLE_EXTRA_ATK = "atk"
        const val BUNDLE_EXTRA_AMOUNT = "amount"
        const val BUNDLE_EXTRA_EDITABLE_AMOUNT = "editable_amount"
        const val BUNDLE_EXTRA_MANUAL_CAPTURE = "manual_capture"
    }
}