package br.com.stone.posandroid.posdeeplink.payment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import br.com.stone.posandroid.posdeeplink.R
import kotlinx.android.synthetic.main.fragment_payment.*

class PaymentFragment : Fragment() {

    companion object {
        private const val INSTALLMENT_TYPE_FIELD = "installmenttype"
        private const val AMOUNT_FIELD = "amount"
        private const val RETURN_SCHEME_FIELD = "returnscheme"
        private const val INSTALLMENT_NUMBER_FIELD = "installmentnumber"
        private const val PAYMENT_TYPE_FIELD = "paymenttype"
        private const val SCHEME = "payment-integrator"

    }

    var selectedInstallmentType: Int? = null
    var selectedPaymentType: Int? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        val installmentTypeArrayAdapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, R.string.installment_type_list)

//        spinnerInstallmentType.adapter = installmentTypeArrayAdapter

        spinnerInstallmentType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        selectedInstallmentType = 0
                    }
                    1 -> {
                        selectedInstallmentType = 1
                    }
                    2 -> {
                        selectedInstallmentType = 2
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spinnerPaymentType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        selectedPaymentType = 1
                    }
                    1 -> {
                        selectedPaymentType = 2
                    }
                    2 -> {
                        selectedPaymentType = 3
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        switchAmount.setOnCheckedChangeListener { _, checked ->
            editTextAmount.visibility = if (checked) View.VISIBLE else View.GONE
        }

        switchInstallmentType.setOnCheckedChangeListener { _, checked ->
            spinnerInstallmentType.visibility = if (checked) View.VISIBLE else View.GONE
        }

        switchPaymentType.setOnCheckedChangeListener { _, checked ->
            spinnerPaymentType.visibility = if (checked) View.VISIBLE else View.GONE
        }

        switchOrderId.setOnCheckedChangeListener { _, checked ->
            editTextOrderId.visibility = if (checked) View.VISIBLE else View.GONE
        }

        switchInstallmentNumber.setOnCheckedChangeListener { _, checked ->
            editTextInstallmentNumber.visibility = if (checked) View.VISIBLE else View.GONE
        }

        buttonStartDeepLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val uriBuilder = Uri.parse("payment-app://pay")
                    .buildUpon()
            if (switchReturnScheme.isChecked) {
                uriBuilder.appendQueryParameter(RETURN_SCHEME_FIELD, SCHEME)
            }

            if (switchInstallmentType.isChecked) {
                uriBuilder.appendQueryParameter(INSTALLMENT_TYPE_FIELD, selectedInstallmentType.toString())
            }

            if (switchAmount.isChecked) {
                uriBuilder.appendQueryParameter(AMOUNT_FIELD, editTextAmount.text.toString())
            }

            if (switchPaymentType.isChecked) {
                uriBuilder.appendQueryParameter(PAYMENT_TYPE_FIELD, selectedPaymentType.toString())
            }

            if (switchInstallmentNumber.isChecked) {
                uriBuilder.appendQueryParameter(INSTALLMENT_NUMBER_FIELD, editTextInstallmentNumber.text.toString())
            }

            intent.data = uriBuilder.build()
            startActivity(intent)
        }
    }
}