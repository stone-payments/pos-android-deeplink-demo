package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val RESPONSE_CODE_FIELD = "responsecode"
        private const val REASON_FIELD = "reason"
        private const val ACTION_CODE_FIELD = "actioncode"
        private const val INSTALLMENT_TYPE_FIELD = "installmenttype"
        private const val INSTALLMENT_NUMBER_FIELD = "installmentnumber"
        private const val PAYMENT_TYPE_FIELD = "paymenttype"
        private const val CARD_BRAND_NAME = "cardbrandname"
        private const val ACQUIRER_NAME_FIELD = "acquirername"
        private const val ATK_FIELD = "atk"
        private const val ORDER_ID_FIELD = "orderid"
        private const val ITK_FIELD = "itk"
        private const val AUTHORIZATION_CODE_FIELD = "authorizationcode"
        private const val SUCCESS_FIELD = "success"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        onNewIntent(intent)
        handleAppLink()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        textViewResult.text = intent.toString()
    }

    private fun handleAppLink() {
        intent.data?.apply {
            val itk = getString(ITK_FIELD, this)
            val atk = getString(ATK_FIELD, this)
            val orderId = getString(ORDER_ID_FIELD, this)
            val installmentType = getString(INSTALLMENT_TYPE_FIELD, this)
            val installmentNumber = getString(INSTALLMENT_NUMBER_FIELD, this)
            val paymentType = getString(PAYMENT_TYPE_FIELD, this)
            val authorizationCode = getString(AUTHORIZATION_CODE_FIELD, this)
            val actionCode = getString(ACTION_CODE_FIELD, this)
            val responseCode = getString(RESPONSE_CODE_FIELD, this)
            val reason = getString(REASON_FIELD, this)
            val success = getString(SUCCESS_FIELD, this)
            val cardBrandName = getString(CARD_BRAND_NAME, this)
            val acquirerName = getString(ACQUIRER_NAME_FIELD, this)

            val builder = AlertDialog.Builder(this@ResultActivity)
            builder.setTitle("Response from payment deep link")
                    .setMessage("ITK: ".plus(itk).plus("\n")
                            .plus("ATK = ").plus(atk).plus("\n")
                            .plus("ORDER ID = ").plus(orderId).plus("\n")
                            .plus("INSTALLMENT TYPE = ").plus(installmentType).plus("\n")
                            .plus("INSTALLMENT NUMBER = ").plus(installmentNumber).plus("\n")
                            .plus("PAYMENT TYPE = ").plus(paymentType).plus("\n")
                            .plus("AUTHORIZATION CODE = ").plus(authorizationCode).plus("\n")
                            .plus("ACTION CODE = ").plus(actionCode).plus("\n")
                            .plus("RESPONSE CODE = ").plus(responseCode).plus("\n")
                            .plus("REASON = ").plus(reason).plus("\n")
                            .plus("CARD BRAND NAME = ").plus(cardBrandName).plus("\n")
                            .plus("ACQUIRER NAME = ").plus(acquirerName).plus("\n")
                            .plus("SUCCESS = ").plus(success).plus("\n")
                    ).setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun getString(key: String, uri: Uri?): String? {
        return uri?.getQueryParameter(key)
    }
}
