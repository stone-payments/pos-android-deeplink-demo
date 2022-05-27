package br.com.stone.posandroid.posdeeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PrinterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_printer)

        val printAllWithScreenButton: Button = findViewById(R.id.printAllWithScreenButton)
        val printTextWithScreenButton: Button = findViewById(R.id.printTextWithScreenButton)
        val printImageWithScreenButton: Button = findViewById(R.id.printImageWithScreenButton)

        val printAllWithoutScreenButton: Button = findViewById(R.id.printAllWithoutScreenButton)
        val printTextWithoutScreenButton: Button = findViewById(R.id.printTextWithoutScreenButton)
        val printImageWithoutScreenButton: Button = findViewById(R.id.printImageWithoutScreenButton)

        printAllWithScreenButton.setOnClickListener {
            print("true", fromResource(R.raw.printable_all))
        }

        printTextWithScreenButton.setOnClickListener {
            print("true", fromResource(R.raw.printable_text))
        }

        printImageWithScreenButton.setOnClickListener {
            print("true", fromResource(R.raw.printable_image))
        }

        printAllWithoutScreenButton.setOnClickListener {
            print("false", fromResource(R.raw.printable_all))
        }

        printTextWithoutScreenButton.setOnClickListener {
            print("false", fromResource(R.raw.printable_text))
        }

        printImageWithoutScreenButton.setOnClickListener {
            print("false", fromResource(R.raw.printable_image))
        }
    }

    private fun print(showFeedback: String, stringRaw: String) {
        val deeplinkReturnScheme = getString(R.string.scheme_return)

        val uriBuilder = Uri.Builder()
        uriBuilder.authority("print")
        uriBuilder.scheme("printer-app")
        uriBuilder.appendQueryParameter("SHOW_FEEDBACK_SCREEN", showFeedback)
        uriBuilder.appendQueryParameter("SCHEME_RETURN", deeplinkReturnScheme)
        uriBuilder.appendQueryParameter("PRINTABLE_CONTENT", stringRaw)
        startMyActivity(uriBuilder)
    }

    private fun startMyActivity(uriBuilder: Uri.Builder) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = uriBuilder.build()
        startActivity(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.data != null) {
            Log.d("TESTE", intent.data.toString())
            Toast.makeText(this, intent.data.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
