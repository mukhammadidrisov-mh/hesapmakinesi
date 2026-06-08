import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.era.hesapmakinesi.MobileAdsInterstitial
import com.era.hesapmakinesi.R
import com.era.hesapmakinesi.active
import com.era.hesapmakinesi.editText
import com.era.hesapmakinesi.editText2
import com.era.hesapmakinesi.editText5
import com.era.hesapmakinesi.history
import com.era.hesapmakinesi.mainActivity.MainActivity
import com.era.hesapmakinesi.numbers
import com.era.hesapmakinesi.operators
import com.era.hesapmakinesi.sayi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.sqrt
import androidx.core.content.edit

class Combinations(context: Context) {

    val sayiGirilmedi = context.getString(R.string.sayigirilmedi)
    val sifiraBolunemez = context.getString(R.string.sifirabolunemez)

    private var percentageCalculated = false

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun clearCombination() {
        val text = editText.text.toString()
        if (text.contains("%")) {
            editText.setText("")
            editText2.setText("")
            numbers.clear()
            operators.clear()
            sayi = ""
            percentageCalculated = false
        } else if (text.isNotEmpty()) {
            val newText = text.dropLast(1)
            editText.setText(newText)
            editText.setSelection(newText.length)

            numbers.clear()
            operators.clear()

            val normalizedText = newText.replace('×', '*').replace('÷', '/').replace(',', '.')

            parseTextToNumbersAndOperators(normalizedText)

            sayi = getLastNumberFromText(normalizedText)

            if (numbers.isNotEmpty() && operators.isNotEmpty() && sayi.isNotEmpty()) {
                updateInstantResult()
            } else {
                showResult(0.0)
            }
        }
    }

    fun parseTextToNumbersAndOperators(text: String) {
        var currentNumber = ""
        for (ch in text) {
            if (ch.isDigit() || ch == '.') {
                currentNumber += ch
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                if (currentNumber.isNotEmpty()) {
                    numbers.add(currentNumber.toDouble())
                    currentNumber = ""
                }
                operators.add(ch)
            }
        }
        if (currentNumber.isNotEmpty()) {
            numbers.add(currentNumber.toDouble())
        }
    }

    fun getLastNumberFromText(text: String): String {
        var lastNumber = ""
        for (i in text.length - 1 downTo 0) {
            val ch = text[i]
            if (ch.isDigit() || ch == '.') {
                lastNumber = ch + lastNumber
            } else {
                break
            }
        }
        return lastNumber
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun numberPressed(digit: String) {
        if (sayi.contains(".") && digit != ".") {
            sayi += digit
            editText.setText(editText.text.toString() + digit)
        } else {
            sayi += digit
            editText.append(digit)
        }

        if (numbers.isNotEmpty() && operators.isNotEmpty() && sayi.isNotEmpty()) {
            updateInstantResult()
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun updateInstantResult() {
        try {
            val currentNumber = sayi.toDouble()
            val previousNumber = numbers.last()
            val instantResult = when (operators.last()) {
                '+' -> previousNumber + currentNumber
                '-' -> previousNumber - currentNumber
                '×' -> previousNumber * currentNumber
                '÷' -> {
                    if (currentNumber == 0.0) return
                    previousNumber / currentNumber
                }
                '%' -> (previousNumber / 100) * currentNumber
                else -> return
            }
            showResult(instantResult)
        } catch (_: Exception) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun divideCombination() {
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            val lastChar = text.last()
            if (lastChar == '×' || lastChar == '-' || lastChar == '+') {
                editText.text.delete(editText.text.length - 1, editText.text.length)
                operators.clear()
                operators.add('/')
                editText.append("÷")
                return
            }
        }

        if (sayi.isNotBlank() || percentageCalculated) {
            if (sayi.isNotBlank()) {
                numbers.add(sayi.toDouble())
                sayi = ""
            }
            percentageCalculated = false

            var i = 0
            while (i < operators.size) {
                when (operators[i]) {
                    '%' -> {
                        val base = numbers[i]
                        val percentage = numbers[i + 1]
                        val result = (base / 100) * percentage
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    '*' -> {
                        val result = numbers[i] * numbers[i + 1]
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    '/' -> {
                        val divisor = numbers[i + 1]
                        if (divisor == 0.0) {
                            Toast.makeText(editText.context, "Sıfıra bölünemez", Toast.LENGTH_SHORT).show()
                            return
                        }
                        val result = numbers[i] / divisor
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    else -> i++
                }
            }

            var result = numbers[0]
            for (j in operators.indices) {
                when (operators[j]) {
                    '+' -> result += numbers[j + 1]
                    '-' -> result -= numbers[j + 1]
                }
            }
            showResult(result)

            numbers.clear()
            numbers.add(result)
            operators.clear()
            operators.add('/')
            editText.append("÷")
        } else {
            Toast.makeText(editText.context, "Sayı girilmedi", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun persentageCombination() {
        if (sayi.isNotBlank()) {
            val currentNumber = sayi.toDouble()
            sayi = ""

            if (numbers.isEmpty() && operators.isEmpty()) {
                val result = currentNumber / 100.0
                numbers.add(result)
                showResult(result)
                percentageCalculated = true
                editText.append("%")
                operators.add('*')
                return
            }

            if (numbers.isNotEmpty() && operators.isNotEmpty()) {
                val percentResultOfCurrentNumber = (currentNumber / 100.0)
                numbers.add(percentResultOfCurrentNumber)
                showResult(percentResultOfCurrentNumber)
                percentageCalculated = true
                editText.append("%")
                operators.add('*')
                return
            }

            Toast.makeText(editText.context, "Geçersiz yüzde işlemi", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(editText.context, sayiGirilmedi, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("DefaultLocale")
    fun showResult(value: Double) {
        val formatted = if (value % 1 == 0.0) {
            value.toLong().toString()
        } else {
            String.format("%.10f", value).trimEnd('0').trimEnd('.').let { if (it.startsWith(".")) "0$it" else it }
        }
        editText2.setText(formatted)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun multiplyCombination() {
        if (editText.text.isNotEmpty() && (editText.text.last() == '+' || editText.text.last() == '-' || editText.text.last() == '÷')) {
            editText.text.delete(editText.text.length - 1, editText.text.length)
            operators.clear()
            operators.add('*')
            editText.append("×")
        } else if (sayi.isNotBlank() || percentageCalculated) {
            if (sayi.isNotBlank()) {
                numbers.add(sayi.toDouble())
                sayi = ""
            }
            percentageCalculated = false

            while (operators.isNotEmpty()) {
                when (operators.last()) {
                    '%' -> {
                        if (numbers.size >= 2) {
                            val base = numbers[numbers.size - 2]
                            val percentage = numbers.last()
                            val result = (base / 100) * percentage
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '*' -> {
                        if (numbers.size >= 2) {
                            val result = numbers[numbers.size - 2] * numbers.last()
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '/' -> {
                        if (numbers.size >= 2) {
                            val divisor = numbers.last()
                            if (divisor == 0.0) {
                                Toast.makeText(editText.context, sifiraBolunemez, Toast.LENGTH_SHORT).show()
                                return
                            }
                            val result = numbers[numbers.size - 2] / divisor
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    else -> break
                }
            }

            val result = numbers.last()
            showResult(result)

            operators.add('*')
            editText.append("×")
        } else {
            Toast.makeText(editText.context, sayiGirilmedi, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun plusCombination() {
        if (editText.text.isNotEmpty() && (editText.text.last() == '×' || editText.text.last() == '-' || editText.text.last() == '÷')) {
            editText.text.delete(editText.text.length - 1, editText.text.length)
            operators.clear()
            operators.add('+')
            editText.append("+")
        } else if (sayi.isNotBlank() || percentageCalculated) {
            if (sayi.isNotBlank()) {
                numbers.add(sayi.toDouble())
                sayi = ""
            }
            percentageCalculated = false

            while (operators.isNotEmpty()) {
                when (operators.last()) {
                    '%' -> {
                        if (numbers.size >= 2) {
                            val base = numbers[numbers.size - 2]
                            val percentage = numbers.last()
                            val result = (base / 100) * percentage
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '*' -> {
                        if (numbers.size >= 2) {
                            val result = numbers[numbers.size - 2] * numbers.last()
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '/' -> {
                        if (numbers.size >= 2) {
                            val divisor = numbers.last()
                            if (divisor == 0.0) {
                                Toast.makeText(editText.context, sifiraBolunemez, Toast.LENGTH_SHORT).show()
                                return
                            }
                            val result = numbers[numbers.size - 2] / divisor
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '-' -> {
                        if (numbers.size >= 2) {
                            val result = numbers[numbers.size - 2] - numbers.last()
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    '+' -> {
                        if (numbers.size >= 2) {
                            val result = numbers[numbers.size - 2] + numbers.last()
                            numbers[numbers.size - 2] = result
                            numbers.removeAt(numbers.lastIndex)
                            operators.removeAt(operators.lastIndex)
                        } else break
                    }
                    else -> break
                }
            }

            val result = numbers.last()
            showResult(result)

            operators.add('+')
            editText.append("+")
        } else {
            Toast.makeText(editText.context, sayiGirilmedi, Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun minusCombination() {
        if (editText.text.isNotEmpty() && (editText.text.last() == '×' || editText.text.last() == '+' || editText.text.last() == '÷')) {
            editText.text.delete(editText.text.length - 1, editText.text.length)
            operators.clear()
            operators.add('-')
            editText.append("-")
        } else if (sayi.isNotBlank() || percentageCalculated) {
            if (sayi.isNotBlank()) {
                numbers.add(sayi.toDouble())
                sayi = ""
            }
            percentageCalculated = false

            var i = 0
            while (i < operators.size) {
                when (operators[i]) {
                    '*' -> {
                        val result = numbers[i] * numbers[i + 1]
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    '/' -> {
                        val divisor = numbers[i + 1]
                        if (divisor == 0.0) {
                            Toast.makeText(editText.context, sifiraBolunemez, Toast.LENGTH_SHORT).show()
                            return
                        }
                        val result = numbers[i] / divisor
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    '%' -> {
                        val base = numbers[i]
                        val percentage = numbers[i + 1]
                        val result = (base / 100) * percentage
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                    else -> i++
                }
            }

            var result = numbers[0]
            for (j in operators.indices) {
                when (operators[j]) {
                    '+' -> result += numbers[j + 1]
                    '-' -> result -= numbers[j + 1]
                }
            }
            showResult(result)

            numbers.clear()
            numbers.add(result)
            operators.clear()
            operators.add('-')
            editText.append("-")
        } else {
            Toast.makeText(editText.context, sayiGirilmedi, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun equalsCombination(activity: MainActivity) {

        active = true
        var islemvesonuc = editText.text.toString()

        if (percentageCalculated && sayi.isBlank()) {
        } else if (sayi.isNotBlank()) {
            numbers.add(sayi.toDouble())
        } else {
            Toast.makeText(editText.context, sayiGirilmedi, Toast.LENGTH_SHORT).show()
            return
        }

        percentageCalculated = false

        var i = 0
        while (i < operators.size) {
            when (operators[i]) {
                '%' -> {
                    if (i > 0 && i + 1 < numbers.size) {
                        val prevOperator = operators[i - 1]
                        val base = numbers[i - 1]
                        val percentValue = numbers[i]
                        val percentResult = (base * percentValue) / 100
                        when (prevOperator) {
                            '+' -> numbers[i - 1] = base + percentResult
                            '-' -> numbers[i - 1] = base - percentResult
                            '*' -> numbers[i - 1] = base * percentResult
                            '/' -> {
                                if (percentResult == 0.0) {
                                    Toast.makeText(editText.context, sifiraBolunemez, Toast.LENGTH_SHORT).show()
                                    return
                                }
                                numbers[i - 1] = base / percentResult
                            }
                        }
                        numbers.removeAt(i)
                        operators.removeAt(i - 1)
                        operators.removeAt(i - 1)
                        i = 0
                        continue
                    } else {
                        val base = numbers[i]
                        val percentage = numbers[i + 1]
                        val result = (base / 100) * percentage
                        numbers[i] = result
                        numbers.removeAt(i + 1)
                        operators.removeAt(i)
                    }
                }
                '*' -> {
                    val result = numbers[i] * numbers[i + 1]
                    numbers[i] = result
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                }
                '/' -> {
                    if (numbers[i + 1] == 0.0) {
                        Toast.makeText(editText.context, sifiraBolunemez, Toast.LENGTH_SHORT).show()
                        return
                    }
                    val result = numbers[i] / numbers[i + 1]
                    numbers[i] = result
                    numbers.removeAt(i + 1)
                    operators.removeAt(i)
                }
                '√' -> {
                    val result = sqrt(numbers[i + 1])
                    numbers[i + 1] = result
                    numbers.removeAt(i)
                    operators.removeAt(i)
                }
                else -> i++
            }
        }

        var resultv = numbers[0]
        for (j in operators.indices) {
            when (operators[j]) {
                '+' -> resultv += numbers[j + 1]
                '-' -> resultv -= numbers[j + 1]
            }
        }

        val formatted = if (resultv % 1 == 0.0) {
            resultv.toLong().toString()
        } else {
            String.format("%.10f", resultv).trimEnd('0').trimEnd('.')
        }
        editText.setText(formatted)
        editText2.setText(formatted)

        val sharedPreferences = activity.getSharedPreferences("history", Context.MODE_PRIVATE)
        if (islemvesonuc.contains("+") || islemvesonuc.contains("-") || islemvesonuc.contains("×") || islemvesonuc.contains("÷") || islemvesonuc.contains("%")) {
            islemvesonuc = islemvesonuc + " = " + editText2.text.toString()
            history.add(islemvesonuc)
            sharedPreferences.edit() {
                putString("history", history.toString())
            }
        }

        editText2.visibility = View.GONE
        editText5.visibility = View.VISIBLE

        sayi = resultv.toString()
        numbers.clear()
        operators.clear()

        activity.lifecycleScope.launch {
            delay(2500)
            MobileAdsInterstitial().intializeAd(activity)
        }

        if (editText.text.contains(".") || editText.text.contains(",")) {
            val currentText = resultv.toString()
            val parts = currentText.split('.')
            val integerPart = parts[0]
            val decimalPart = if (parts.size > 1) parts[1] else ""
            val limitedDecimalPart = if (decimalPart.length > 4) decimalPart.substring(0, 4) else decimalPart
            val newText = if (decimalPart.isNotEmpty()) {
                "$integerPart.$limitedDecimalPart"
            } else {
                integerPart
            }
            editText.setText(newText)
            sayi = newText
            numbers.clear()
            operators.clear()
        }
    }
}
