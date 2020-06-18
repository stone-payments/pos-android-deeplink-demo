package br.com.stone.posandroid.posdeeplink.cancel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.posandroid.posdeeplink.R
import kotlinx.android.synthetic.main.fragment_cancel.*

class CancelFragment: Fragment() {

    companion object {
        private const val ATK_FIELD = "atk"
        private const val AMOUNT_FIELD = "amount"
        private const val RETURN_SCHEME_FIELD = "returnscheme"
        private const val RETURN_SCHEME_VALUE = "cancelapplink"

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cancel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchAmount.setOnCheckedChangeListener { _, checked ->
            editTextAmount.visibility = if (checked) View.VISIBLE else View.GONE
        }

        switchAtk.setOnCheckedChangeListener { _, checked ->
            editTextAtk.visibility = if (checked) View.VISIBLE else View.GONE
        }

        buttonStartDeepLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val uriBuilder = Uri.parse("cancel-app://cancel")
                    .buildUpon()
            if (switchReturnScheme.isChecked){
                uriBuilder.appendQueryParameter(RETURN_SCHEME_FIELD, RETURN_SCHEME_VALUE)
            }

            if (switchAtk.isChecked){
                uriBuilder.appendQueryParameter(ATK_FIELD, editTextAtk.text.toString())
            }

            if (switchAmount.isChecked){
                uriBuilder.appendQueryParameter(AMOUNT_FIELD, editTextAmount.text.toString())
            }

            intent.data = uriBuilder.build()
            startActivity(intent)
        }
    }
}