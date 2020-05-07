package com.example.calculatorerchihin2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private TextView numberField;
    private TextView operationField;
    String lastOperation = "=";
    Double operand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnClickButton();
    }

    private void initViews() {
        result = findViewById(R.id.result);
        numberField = findViewById(R.id.numberField);
        operationField = findViewById(R.id.operationField);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) { // сохранение состояния
        outState.putString("OPERATION", lastOperation);
        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        result.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    private View.OnClickListener numberButtonListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Button buttonNumber = (Button) v;
            numberField.append(buttonNumber.getText());
            if (lastOperation.equals("=") && operand != null) {
                operand = null;
            }
        }
    };

    private void setOnClickButton() {
        findViewById(R.id.btnPoint).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn0).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn1).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn2).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn3).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn4).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn5).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn6).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn7).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn8).setOnClickListener(numberButtonListener);
        findViewById(R.id.btn9).setOnClickListener(numberButtonListener);
        findViewById(R.id.btnDelete).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnPositiveNegative).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnPer).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnDivide).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnMultiplication).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnMinus).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnPlus).setOnClickListener(numberOperationListener);
        findViewById(R.id.btnEquals).setOnClickListener(numberOperationListener);
    }

    private View.OnClickListener numberOperationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button buttonOperation = (Button) v;
            String op = buttonOperation.getText().toString();
            String number = numberField.getText().toString();
            if (op.equals("C")) {
                operationField.setText(null);
                numberField.setText(null);
                result.setText(null);
                operand = null;
            }else {
                if (op.equals("+/-")) {
                    double d = Double.parseDouble(number) * -1;
                    numberField.setText(Double.toString(d));
                } else {
                    if (number.length() > 0) {
                        try {
                            performOperation(Double.valueOf(number), op);
                        } catch (NumberFormatException ex) {
                            numberField.setText("");
                        }
                    }

                    lastOperation = op;
                    operationField.setText(lastOperation);

                }
            }

        }

    };

    private void performOperation(Double number, String operation) {
        // если операнд ранее не был установлен (при вводе самой первой операции)
        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;
            }
            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;
                case "x":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
                case "%":
                    operand *= number / 100;
                    break;

            }

        }

        result.setText(operand.toString());
        numberField.setText("");

    }
}

