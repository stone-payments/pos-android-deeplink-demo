package br.com.stone.deeplinkflutter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import br.com.stone.posandroid.paymentapp.deeplink.PaymentDeeplink
import br.com.stone.posandroid.paymentapp.deeplink.exception.PaymentException
import br.com.stone.posandroid.paymentapp.deeplink.model.InstallmentType
import br.com.stone.posandroid.paymentapp.deeplink.model.PaymentInfo
import br.com.stone.posandroid.paymentapp.deeplink.model.TransactionType
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


class MainActivity : FlutterActivity() {
    private val CHANNEL = "mainDeeplinkChannel"
    private val paymentDeeplink by lazy { PaymentDeeplink(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        try {
            val response = paymentDeeplink.receiveDeeplinkResponse(intent)
            if (response != null) Toast.makeText(this, response.toString(), Toast.LENGTH_LONG).show()
            Log.i("Deeplink Response", response.toString())
        } catch (e: PaymentException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.e("Deeplink Response error", e.toString())
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
                .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                    if (call.method == "sendDeeplink") {
                        sendDeeplink(
                                call.argument<Int>("amount")!!.toLong(),
                                call.argument<Boolean>("editableAmount"),
                                call.argument<String>("transactionType")!!,
                                call.argument<Int>("installmentCount"),
                                call.argument<String>("installmentType")!!,
                                call.argument<Int>("orderId"),
                                call.argument<String>("returnScheme")
                        )
                        result.success(true)
                    }
                }
    }

    private fun sendDeeplink(amount: Long,
                             editableAmount: Boolean?,
                             transactionType: String,
                             installmentCount: Int?,
                             installmentType: String,
                             orderId: Int?,
                             returnScheme: String?) {

        val paymentInfo = PaymentInfo(
                amount = amount,
                editableAmount = editableAmount,
                transactionType = TransactionType.valueOf(transactionType),
                installmentCount = installmentCount,
                installmentType = InstallmentType.valueOf(installmentType),
                orderId = orderId,
                returnScheme = returnScheme
        )

        paymentDeeplink.sendDeepLink(paymentInfo)
    }

}
