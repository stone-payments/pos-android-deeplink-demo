package br.com.stone.posandroid.posdeeplink

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.stone.posandroid.paymentapp.deeplink.PaymentDeeplink
import br.com.stone.posandroid.paymentapp.deeplink.exception.PaymentException
import br.com.stone.posandroid.paymentapp.deeplink.model.InstallmentType
import br.com.stone.posandroid.paymentapp.deeplink.model.PaymentInfo
import br.com.stone.posandroid.paymentapp.deeplink.model.TransactionType
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val paymentDeeplink by lazy { PaymentDeeplink(applicationContext) }
    val longSafeMaxValue = 18
    var validOrderID = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val response = paymentDeeplink.receiveDeeplinkResponse(intent)
            if (response != null) {
                Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            Log.i("Deeplink Response", response.toString())}
        } catch (e: PaymentException) {

            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("Deeplink Repsonse error", e.toString())
        }

        deepLink.setOnClickListener {

            var amount = if (editTextAmount.text.trim().isNotEmpty() && editTextAmount.text.trim().isNotBlank()) {
                editTextAmount.text.toString().toLong()
            } else -1L

            var editableAmount = editableAmountCheckbox.isChecked

            var transactionType: TransactionType? = when (spinnerTransactionType.selectedItemPosition) {
                0 -> TransactionType.DEBIT
                1 -> TransactionType.CREDIT
                2 -> TransactionType.VOUCHER
                3 -> TransactionType.INSTANT_PAYMENT
                4 -> TransactionType.PIX
                5 -> TransactionType.INVALID
                else -> null
            }

            var installmentType: InstallmentType? = when (spinnerInstallmentType.selectedItemPosition) {
                0 -> InstallmentType.MERCHANT
                1 -> InstallmentType.ISSUER
                2 -> InstallmentType.NONE
                3 -> InstallmentType.INVALID
                else -> null
            }

            var installmentCount = editTextInstallmentCount.text.toString().toIntOrNull()

            var orderId: Long? = -1L
            try {
                orderId = if (editTextOrderId.text.toString().isNotBlank()) {
                    editTextOrderId.text.toString().toLong()
                } else {
                    null
                }
                validOrderID = true
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Valor de order id acima do limite do tipo long", Toast.LENGTH_SHORT).show()
                validOrderID = false
            }

            var returnScheme = editTextReturnScheme.text.takeIf { it.isNotBlank() }?.toString()

            if (validOrderID) {
                paymentDeeplink.sendDeepLink(PaymentInfo(
                        amount = amount,
                        editableAmount = editableAmount,
                        transactionType = transactionType,
                        installmentCount = installmentCount,
                        installmentType = installmentType,
                        orderId = orderId,
                        returnScheme = returnScheme
                ))
            }
        }
    }

}