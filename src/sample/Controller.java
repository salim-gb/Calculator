package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

/**
 *
 * Здравствуйте!
 * Знаю что там много повторяющего и лишнего кода, но из за работы время очень ограничено и
 * просто хотелось что бы программа работала!
 * Спасибо за понимания!!!!
 *
 */
public class Controller {
    @FXML
    public VBox textContainer;
    @FXML
    public VBox vboxx;
    @FXML
    public TextArea textResult;
    @FXML
    public Button resetCalc;
    @FXML
    public Button clearLast;

    String num1 = "";
    String num2 = "";
    String result = "";
    String sign = "";
    String sign1 = "";
    boolean isFirstNumTaken = false;

    public void initialize() {
        textResult.setText("0");
        String[][] s = {
                {"1", "2", "3", "÷"},
                {"4", "5", "6", "×"},
                {"7", "8", "9", "-"},
                {"=", "0", ".", "+"}
        };
        for (int i = 0; i < s.length; i++) {
            HBox hbox = new HBox();
            vboxx.getChildren().add(hbox);
            for (int j = 0; j < s[i].length; j++) {
                Button button = new Button("" + s[i][j]);
                button.setId("btnId" + i + j);
                button.getStyleClass().add("button-s");
                hbox.getChildren().add(button);
                button.setOnAction(e -> {
                    String value = button.getText();
                    if (value.equals("=")) {
                        if (isFirstNumTaken && !sign.equals("")) {
                            if (num2.equals("")) {
                                num2 = num1;
                                showResult();
                            } else {
                                if (num2.equals("0") && sign.equals("÷")) {
                                    resetCalc();
                                    textResult.setText("Нельзя делить на ноль");
                                } else {
                                    if (result.equals("0")) {
                                        resetCalc();
                                    } else {
                                        textResult.setText(makeResult());
                                        resultReceived();
                                    }
                                }
                            }
                        }
                    } else if (value.equals(".")) {
                        if (num1.equals("")) {
                            num1 += "0.";
                            textResult.setText(num1);
                        } else {
                            if (isFirstNumTaken) {
                                if (num2.equals("")) {
                                    num2 = "0.";
                                } else {
                                    num2 += ".";
                                }
                                textResult.setText(num1 + sign + num2);
                            } else {
                                num1 += ".";
                                textResult.setText(num1);
                            }
                        }
                    } else if (!isSign(value)) {
                        result = "";
                        if (num1.equals("") && num2.equals("")) {
                            if (!value.equals("0")) {
                                if (!isFirstNumTaken) {
                                    num1 = sign1 + value;
                                } else {
                                    num2 += value;
                                }
                                textResult.setText(num1 + sign + num2);
                            }
                        } else {
                            if (!isFirstNumTaken) {
                                num1 += value;
                            } else {
                                num2 += value;
                            }
                            textResult.setText(num1 + sign + num2);
                        }
                    } else if (isSign(value)) {
                        if (!result.equals("")) {
                            num1 = result;
                        }
                        if (value.equals("-")) {
                            if (num1.equals("")) {
                                sign1 = "-";
                            } else {
                                if (!num2.equals("")) {
                                    result = makeResult();
                                    textResult.setText(result + value);
                                    num1 = result;
                                    num2 = "";
                                } else {
                                    isFirstNumTaken = true;
                                    textResult.setText(num1 + value);
                                }
                                sign = value;
                            }
                        } else {
                            if (!num1.equals("")) {
                                if (!num2.equals("")) {
                                    result = makeResult();
                                    textResult.setText(result + value);
                                    resultReceived();
                                } else {
                                    isFirstNumTaken = true;
                                    textResult.setText(num1 + value);
                                }
                            }
                            sign = value;
                        }
                    }
                });
            }
        }
    }

    public void showResult() {
        result = makeResult();
        if (result.equals("0")) {
            resetCalc();
        } else {
            textResult.setText(result);
            resultReceived();
        }
    }

    public String makeResult() {
        switch (sign) {
            case "÷":
                if (num2.equals("0")) {
                    return result = "";
                }
                return result = improveResult(Double.parseDouble(num1) /
                        Double.parseDouble(num2));
            case "×":
                return result = improveResult(Double.parseDouble(num1) *
                        Double.parseDouble(num2));
            case "+":
                return result = improveResult(Double.parseDouble(num1) +
                        Double.parseDouble(num2));
            case "-":
                return result = improveResult(Double.parseDouble(num1) -
                        Double.parseDouble(num2));
            default:
                return "";
        }
    }

    public void resultReceived() {
        num1 = "";
        sign = "";
        num2 = "";
        isFirstNumTaken = false;
    }

    public boolean isSign(String value) {
        String signs = "÷×+-=";
        return signs.contains(value);
    }

    public String improveResult(double d) {
        if (d % 1 == 0) {
            DecimalFormat df1 = new DecimalFormat("#");
            return String.valueOf(df1.format(d));
        } else {
            DecimalFormat df2 = new DecimalFormat("#.##");
            return String.valueOf(df2.format(d));
        }
    }

    public void resetCalc() {
        textResult.setText("0");
        sign = "";
        result = "";
        num1 = "";
        num2 = "";
        isFirstNumTaken = false;
    }

    public void clearLast() {
        if (!isFirstNumTaken) {
            if (num1 != null && num1.length() > 0) {
                num1 = num1.substring(0, num1.length() - 1);
            }
        } else if (!num2.equals("")) {
            if (num2.length() > 0) {
                num2 = num2.substring(0, num2.length() - 1);
            }
        }
        textResult.setText(num1 + sign + num2);
    }
}
