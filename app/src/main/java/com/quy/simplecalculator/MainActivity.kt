package com.quy.simplecalculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.material.color.DynamicColors
import com.quy.simplecalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //set the content view fit the screen
        //xử lý cho nội dung full màn hình
        WindowCompat.setDecorFitsSystemWindows(window, false)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        DynamicColors.applyToActivityIfAvailable(this)

        //Numbers
        //Các số
        with(binding) {
            num0.setOnClickListener { appendVal("0", false) }
            num1.setOnClickListener { appendVal("1", false) }
            num2.setOnClickListener { appendVal("2", false) }
            num3.setOnClickListener { appendVal("3", false) }
            num4.setOnClickListener { appendVal("4", false) }
            num5.setOnClickListener { appendVal("5", false) }
            num6.setOnClickListener { appendVal("6", false) }
            num7.setOnClickListener { appendVal("7", false) }
            num8.setOnClickListener { appendVal("8", false) }
            num9.setOnClickListener { appendVal("9", false) }
            numDot.setOnClickListener { appendVal(".", false) }
        }

        //Operators
        //Các toán tử
        binding.apply {
            clear.setOnClickListener { appendVal("", true) }
            startBracket.setOnClickListener { appendVal(" ( ", false) }
            closeBracket.setOnClickListener { appendVal(" ) ", false) }
            actionDivide.setOnClickListener { appendVal(" / ", false) }
            actionMultiply.setOnClickListener { appendVal(" * ", false) }
            actionMinus.setOnClickListener { appendVal(" - ", false) }
            actionAdd.setOnClickListener { appendVal(" + ", false) }

            actionBack.setOnClickListener {
                val expression = placeholder.text.toString()
                //check if the expression is empty
                //thực thi nếu biểu thức rỗng
                when {
                    expression.isNotEmpty() -> {
                        placeholder.text = expression.substring(0, expression.length - 1)
                    }
                }


            }

            actionEquals.setOnClickListener {
                //using try catch to handle exception (divide by zero, invalid expression)
                //sử dụng try catch để xử lý ngoại lệ (chia cho 0, biểu thức không hợp lệ)
                try {
                    // Create an Expression (A class from exp4j library)
                    //Tạo một biểu thức (sử dụng thư viện của bên thứ 3)
                    val expression = ExpressionBuilder(placeholder.text.toString()).build()
                    // Calculate the result and display
                    //Tính toán và hiển thị kết quả
                    val result = expression.evaluate()
                    val longResult = result.toLong()
                    when (result) {
                        longResult.toDouble() -> {
                            answer.text = longResult.toString()
                        }
                        else -> answer.text = result.toString()
                    }

                } catch (e: Exception) {
                    Toast.makeText(it.context, e.message, Toast.LENGTH_SHORT).show()

                    Log.d("EXCEPTION", "Message: ${e.message}")
                }

            }
        }

    }

    //function to append value to the expression
    //hàm này thêm giá trị vào biểu thức
    private fun appendVal(string: String, isAllClear: Boolean) {
        //check if the expression is clear
        //thực thi xoá hết
        when {
            isAllClear -> {
                binding.apply {
                    placeholder.text = ""
                    answer.text = ""
                }
            }
            //if not clear, append the value to the expression
            //nếu không xoá hết, thêm giá trị vào biểu thức
            else -> {
                binding.placeholder.append(string)
            }
        }
    }

}