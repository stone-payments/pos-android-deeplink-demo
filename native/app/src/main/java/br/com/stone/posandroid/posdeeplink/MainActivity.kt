package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.stone.posandroid.posdeeplink.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deepLink.setOnClickListener { startPayment() }
        binding.deeplinkCapture.setOnClickListener { startCapture() }
    }

    private fun startPayment() {
        val uriBuilder = Uri.Builder()
        uriBuilder.authority("pay")
        uriBuilder.scheme("payment-app")
        uriBuilder.appendQueryParameter(RETURN_SCHEME, "deeplinktest")

        if (binding.editTextAmount.text.toString().isNotBlank()) {
            val amount = binding.editTextAmount.text.toString()
            uriBuilder.appendQueryParameter(AMOUNT, amount)
        }

        val editableAmount = if (binding.editableAmountCheckbox.isChecked) "1" else "0"
        uriBuilder.appendQueryParameter(EDITABLE_AMOUNT, editableAmount)

        val transactionType: String? = when (binding.spinnerTransactionType.selectedItemPosition) {
            0 -> "DEBIT"
            1 -> "CREDIT"
            2 -> "VOUCHER"
            3 -> "INSTANT_PAYMENT"
            4 -> "PIX"
            else -> null
        }
        if (transactionType != null) {
            uriBuilder.appendQueryParameter(TRANSACTION_TYPE, transactionType)
        }

        val installmentType: String? = when (binding.spinnerInstallmentType.selectedItemPosition) {
            0 -> "MERCHANT"
            1 -> "ISSUER"
            2 -> "NONE"
            else -> null
        }
        if (installmentType != null) {
            uriBuilder.appendQueryParameter(INSTALLMENT_TYPE, installmentType)
        }

        if (binding.editTextInstallmentCount.text.toString().isNotBlank()) {
            val installmentCount = binding.editTextInstallmentCount.text.toString()
            uriBuilder.appendQueryParameter(INSTALLMENT_COUNT, installmentCount)
        }

        if (binding.editTextOrderId.text.toString().isNotBlank()) {
            val orderId = binding.editTextOrderId.text.toString()
            uriBuilder.appendQueryParameter(ORDER_ID, orderId)
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)

        Log.v(TAG, "toUri(scheme = ${intent.data})")
    }

    private fun startCapture() {
        val intent = Intent(this, CaptureActivity::class.java)
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        try {
            Log.i("onNewIntent", intent?.data.toString())
            if (intent?.data != null) {
                Toast.makeText(this, intent.data.toString(), Toast.LENGTH_LONG).show()
                Log.i("DeeplinkPay Response", intent.data.toString())
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("Deeplink error", e.toString())
        }
    }

    fun btnDeepLinkCancel(view: View) {
        val intent = Intent(this, CancelActivity::class.java)
        startActivity(intent)
    }

    fun btnDeeplinkPrinter(view: View) {
        val intent = Intent(this, PrinterActivity::class.java)
        startActivity(intent)
    }

    fun btnDeeplinkReprinter(view: View) {
        val intent = Intent(this, ReprinterActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val AMOUNT = "amount"
        private const val ORDER_ID = "order_id"
        private const val EDITABLE_AMOUNT = "editable_amount"
        private const val TRANSACTION_TYPE = "transaction_type"
        private const val INSTALLMENT_TYPE = "installment_type"
        private const val INSTALLMENT_COUNT = "installment_count"
        private const val RETURN_SCHEME = "return_scheme"
        const val TAG = "SendDeeplinkPayment"
    }
}
