package com.stripe.android.paymentsheet.state

import android.os.Parcelable
import com.stripe.android.model.PaymentMethod
import com.stripe.android.model.StripeIntent
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.model.ClientSecret
import com.stripe.android.paymentsheet.model.PaymentSelection
import com.stripe.android.paymentsheet.model.SavedSelection
import kotlinx.parcelize.Parcelize

internal sealed interface PaymentSheetState : Parcelable {

    @Parcelize
    object Loading : PaymentSheetState

    @Parcelize
    data class Full(
        val config: PaymentSheet.Configuration?,
        val clientSecret: ClientSecret,
        val stripeIntent: StripeIntent,
        val customerPaymentMethods: List<PaymentMethod> = emptyList(),
        val savedSelection: SavedSelection = SavedSelection.None,
        val isGooglePayReady: Boolean = false,
        val isLinkEnabled: Boolean = false,
        val newPaymentSelection: PaymentSelection.New? = null,
    ) : PaymentSheetState {

        val hasPaymentOptions: Boolean
            get() = isGooglePayReady || isLinkEnabled || customerPaymentMethods.isNotEmpty()

        val initialPaymentSelection: PaymentSelection?
            get() = when (savedSelection) {
                is SavedSelection.GooglePay -> PaymentSelection.GooglePay
                is SavedSelection.Link -> PaymentSelection.Link
                is SavedSelection.PaymentMethod -> {
                    val paymentMethod = customerPaymentMethods.firstOrNull {
                        it.id == savedSelection.id
                    }

                    paymentMethod?.let {
                        PaymentSelection.Saved(it)
                    }
                }
                else -> null
            }
    }
}
