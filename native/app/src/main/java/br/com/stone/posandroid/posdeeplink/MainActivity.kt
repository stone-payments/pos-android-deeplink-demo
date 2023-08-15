package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        deepLink.setOnClickListener {
            startPayment()
        }
    }

    private fun startPayment() {
        val uriBuilder = Uri.Builder()
        uriBuilder.authority("pay")
        uriBuilder.scheme("payment-app")
        uriBuilder.appendQueryParameter(RETURN_SCHEME, "deeplinktest")
        uriBuilder.appendQueryParameter(THIRD_PARTY_THEME_ENABLED, "true")
        uriBuilder.appendQueryParameter(THIRD_PARTY_LOGO, "ic_activation")

        if (editTextAmount.text.toString().isNotBlank()) {
            val amount = editTextAmount.text.toString()
            uriBuilder.appendQueryParameter(AMOUNT, amount)
        }

        val editableAmount = if (editableAmountCheckbox.isChecked) "1" else "0"
        uriBuilder.appendQueryParameter(EDITABLE_AMOUNT, editableAmount)

        val transactionType: String? = when (spinnerTransactionType.selectedItemPosition) {
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

        val installmentType: String? = when (spinnerInstallmentType.selectedItemPosition) {
            0 -> "MERCHANT"
            1 -> "ISSUER"
            2 -> "NONE"
            else -> null
        }
        if (installmentType != null) {
            uriBuilder.appendQueryParameter(INSTALLMENT_TYPE, installmentType)
        }

        if (editTextInstallmentCount.text.toString().isNotBlank()) {
            val installmentCount = editTextInstallmentCount.text.toString()
            uriBuilder.appendQueryParameter(INSTALLMENT_COUNT, installmentCount)
        }

        if (editTextOrderId.text.toString().isNotBlank()) {
            val orderId = editTextOrderId.text.toString()
            uriBuilder.appendQueryParameter(ORDER_ID, orderId)
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)

        Log.v(TAG, "toUri(scheme = ${intent.data})")
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
        val uriBuilder = Uri.Builder()
        uriBuilder.authority("cancel")
        uriBuilder.scheme("cancel-app")
        uriBuilder.appendQueryParameter(RETURN_SCHEME, "deeplinktest")
        uriBuilder.appendQueryParameter(THIRD_PARTY_THEME_ENABLED, "true")
        uriBuilder.appendQueryParameter(THIRD_PARTY_LOGO, "ic_activation")

        //val intent = Intent(this, CancelActivity::class.java)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()

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
        private const val THIRD_PARTY_THEME_ENABLED = "third_party_theme_enabled"
        private const val THIRD_PARTY_LOGO = "third_party_logo"
        const val TAG = "SendDeeplinkPayment"
    }
}
