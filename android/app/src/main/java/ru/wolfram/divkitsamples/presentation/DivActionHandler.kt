package ru.wolfram.divkitsamples.presentation

import android.util.Log
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction

class DivActionHandler: DivActionHandler() {
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        Log.e("DivKit", action.logId.rawValue.toString())
        return super.handleAction(action, view, resolver)
    }
}