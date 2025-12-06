import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class ExpiryDateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        var trimmed = if (text.text.length >= 4) text.text.substring(0..3) else text.text
        var out = ""

        if (trimmed.isNotEmpty()) {
            val month = trimmed.take(minOf(2, trimmed.length))
            if (month.length == 2 && month.toInt() > 12) {
                trimmed = "0" + month.first() + trimmed.drop(2)
            }
        }

        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += "/"
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 1) return offset
                if (offset <= 4) return offset + 1
                return 5
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 2) return offset
                if (offset <= 5) return offset - 1
                return 4
            }
        }

        return TransformedText(AnnotatedString(out), offsetTranslator)
    }
}
