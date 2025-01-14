package com.stripe.android.paymentsheet.analytics

import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.model.PaymentSelection

internal interface EventReporter {
    fun onInit(configuration: PaymentSheet.Configuration?)

    fun onDismiss()

    fun onShowExistingPaymentOptions(linkEnabled: Boolean, activeLinkSession: Boolean)

    fun onShowNewPaymentOptionForm(linkEnabled: Boolean, activeLinkSession: Boolean)

    fun onSelectPaymentOption(paymentSelection: PaymentSelection)

    fun onPaymentSuccess(paymentSelection: PaymentSelection?)

    fun onPaymentFailure(paymentSelection: PaymentSelection?)

    fun onLpmSpecFailure()

    enum class Mode(val code: String) {
        Complete("complete"),
        Custom("custom");

        override fun toString(): String = code
    }
}
