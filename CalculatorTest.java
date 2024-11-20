
public class CalculatorTest {
    public static void main(String[] args) {
        // Tworzenie instancji klasy ProgrammableCalculator
        ProgrammableCalculator calculator = new ProgrammableCalculator();

        // Ustawienie LineReader i LinePrinter (do testów)
        calculator.setStdin(calculator.new ConsoleLineReader());
        calculator.setStdout(calculator.new ConsoleLinePrinter());

        // Przykład: Odczytywanie kodu programu z ciągu znaków
        String programCode = /*
                              * "10 INPUT n\n" +
                              * "20 IF n > 5 GOTO 40\n" +
                              * "30 PRINT \"n jest mniejsze lub równe 5\"\n" +
                              * "40 PRINT \"n jest większe niż 5\"\n" +
                              * "50 END";
                              */

                "10 INPUT n\n" +
                // "11 PRINT \"Hello moge cie zjesc\"\n" +
                // "28 goto 70\n" +
                        "28 LET sum = 3\n" +
                        // "29 IF n > sum PRINT \"Wygrales\"\n" +
                        "30 LET i = 1\n" +
                        "40 LET sum = sum - i\n" +
                        "41 print sum\n" +
                        "50 LET i = i + 1\n" +
                        "60 GOSUB 10\n" +
                        // "70 PRINT sum\n" +
                        // "71 PRINT i\n" +
                        // "75 if i = 2 goto 80" +
                        // "76 print \"jest źle\"\n" +
                        "80 END";

        // Utworzenie BufferedReader dla kodu programu
        java.io.BufferedReader programReader = new java.io.BufferedReader(new java.io.StringReader(programCode));
        calculator.programCodeReader(programReader);

        // Uruchomienie programu zaczynając od linii 10
        calculator.run(10);
    }
}