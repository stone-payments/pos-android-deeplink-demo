package br.com.stone.posandroid.posdeeplink.activation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.stone.posandroid.posdeeplink.R
import kotlinx.android.synthetic.main.fragment_activation.*

class ActivationFragment: Fragment() {

    companion object {
        private const val STONE_CODE_FILED = "stonecode"
        private const val RETURN_SCHEME_FIELD = "returnscheme"
        private const val RETURN_SCHEME_VALUE = "activationapplink"

    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_activation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        switchStoneCode.setOnCheckedChangeListener { _, checked ->
            editTextStoneCode.visibility = if (checked) View.VISIBLE else View.GONE
        }

        buttonStartDeepLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val uriBuilder = Uri.parse("activate-app://activate")
                    .buildUpon()
            if (switchReturnScheme.isChecked){
                uriBuilder.appendQueryParameter(RETURN_SCHEME_FIELD, RETURN_SCHEME_VALUE)
            }

            if (switchStoneCode.isChecked){
                uriBuilder.appendQueryParameter(STONE_CODE_FILED, editTextStoneCode.text.toString())
            }

            intent.data = uriBuilder.build()
            startActivity(intent)
        }
    }
}