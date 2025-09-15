package ru.wolfram.divkitsamples.presentation

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.yandex.div.core.Div2Context
import com.yandex.div.core.DivConfiguration
import com.yandex.div.picasso.PicassoDivImageLoader
import org.json.JSONObject
import ru.wolfram.divkitsamples.R
import ru.wolfram.divkitsamples.presentation.player.ExoDivPlayerFactory

class MainActivity : AppCompatActivity() {
    private lateinit var mainModel: MainModel

    fun setMainModel(mainModel: MainModel) {
        this.mainModel = mainModel
    }

    private val mainRoot: ViewGroup by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.main_root)
    }

    private val component by lazy(LazyThreadSafetyMode.NONE) {
        (application as App).component.addOnMessage { json ->
            val divJson = JSONObject(json)
            val templatesJson = divJson.optJSONObject("templates")
            val cardJson = divJson.getJSONObject("card")

            val divContext = Div2Context(
                baseContext = this,
                configuration = createDivConfiguration(),
                lifecycleOwner = this
            )

            runOnUiThread {
                val divView =
                    Div2ViewFactory(divContext, templatesJson).createView(cardJson).apply {
                        layoutParams =
                            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
                        gravity = Gravity.CENTER
                    }
                mainRoot.release()
                mainRoot.removeAllViews()
                mainRoot.addView(divView)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main)

        component.inject(this)

        mainModel.connect()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainModel.disconnect()
    }

    private fun createDivConfiguration(): DivConfiguration {
        return DivConfiguration.Builder(PicassoDivImageLoader(this))
            .actionHandler(DivActionHandler())
            .divPlayerFactory(ExoDivPlayerFactory(this))
            .visualErrorsEnabled(true)
            .build()
    }
}