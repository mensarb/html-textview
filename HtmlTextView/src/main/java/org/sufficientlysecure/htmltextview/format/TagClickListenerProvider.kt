package org.sufficientlysecure.htmltextview.format

import org.sufficientlysecure.htmltextview.OnClickATagListener

/**
 * @author dkoller
 * @since 18.03.2024
 */
interface TagClickListenerProvider {
    fun provideTagClickListener(): OnClickATagListener
}