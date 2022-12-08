package br.com.stone.deeplinkflutter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class MainActivity : FlutterActivity() {
    private val CHANNEL = "mainDeeplinkChannel"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                if (call.method == "sendDeeplink") {
                    sendDeeplink(
                        call.argument<Int>("amount"),
                        call.argument<Boolean>("editableAmount"),
                        call.argument<String>("transactionType"),
                        call.argument<Int>("installmentCount"),
                        call.argument<String>("installmentType"),
                        call.argument<Int>("orderId"),
                        call.argument<String>("returnScheme")
                    )
                    result.success(true)
                }
            }
    }

    private fun sendDeeplink(
        amount: Int?,
        editableAmount: Boolean?,
        transactionType: String?,
        installmentCount: Int?,
        installmentType: String?,
        orderId: Int?,
        returnScheme: String?
    ) {

        val uriBuilder = Uri.Builder()
        uriBuilder.authority("pay")
        uriBuilder.scheme("payment-app")
        uriBuilder.appendQueryParameter(RETURN_SCHEME, "flutterdeeplinkdemo")
        uriBuilder.appendQueryParameter(EDITABLE_AMOUNT, if (editableAmount == true) "1" else "0")

        if (amount != null) {
            uriBuilder.appendQueryParameter(AMOUNT, amount.toLong().toString())
        }

        if (transactionType != null) {
            uriBuilder.appendQueryParameter(TRANSACTION_TYPE, transactionType)
        }

        if (installmentType != null) {
            uriBuilder.appendQueryParameter(INSTALLMENT_TYPE, installmentType)
        }

        if (installmentCount != null) {
            uriBuilder.appendQueryParameter(INSTALLMENT_COUNT, installmentCount.toString())
        }

        if (orderId != null) {
            uriBuilder.appendQueryParameter(ORDER_ID, orderId.toLong().toString())
        }

        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)

        Log.v(TAG, "toUri(scheme = ${intent.data})")
    }

    private fun handleDeepLinkResponse(intent: Intent) {
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action === Intent.ACTION_VIEW) {
            handleDeepLinkResponse(intent)
        }
    }

    companion object {
        private const val AMOUNT = "amount"
        private const val ORDER_ID = "order_id"
        private const val EDITABLE_AMOUNT = "editable_amount"
        private const val TRANSACTION_TYPE = "transaction_type"
        private const val INSTALLMENT_TYPE = "installment_type"
        private const val INSTALLMENT_COUNT = "installment_count"
        private const val RETURN_SCHEME = "return_scheme"
        private const val TAG = "SendDeeplinkPayment"
    }
}
