import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Stack;

class ProgrammableCalculator implements ProgrammableCalculatorInterface {

    private BufferedReader codeReader;
    private LineReader stdin;
    private LinePrinter stdout;
    private Map<Integer, String> programCode = new HashMap<>();
    private boolean stopExecution = false;
    private Stack<Integer> StosDoGosub = new Stack<>();
    private int linijkaKodu;

    public void programCodeReader(BufferedReader reader) {
        this.codeReader = reader;
        String line;
        try {
            programCode = new TreeMap<>();
            while ((line = codeReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] podzial = line.split(" ", 2);
                    int aktualnaLinia = Integer.parseInt(podzial[0]);
                    String kod = podzial[1];
                    programCode.put(aktualnaLinia, kod);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public void setStdin(LineReader input) {
        this.stdin = input;

    }

    public void setStdout(LinePrinter output) {
        this.stdout = output;
    }

    public void run(int line) {
        boolean shouldContinue = true;

        for (Integer aktualnaLinia : programCode.keySet()) {

            if (aktualnaLinia >= line && shouldContinue && !stopExecution) {

                linijkaKodu = aktualnaLinia;
                String kod = programCode.get(aktualnaLinia);
                String[] podzielonyKod = kod.split(" ", 2);
                String instrukcja = podzielonyKod[0].toLowerCase();

                if ("end".equals(instrukcja)) {
                    shouldContinue = false;
                    break;
                } else if ("return".equals(instrukcja)) {
                    int powrot = StosDoGosub.pop();
                    run(powrot);
                    stopExecution = true;
                } else {
                    String reszta = podzielonyKod[1];
                    switch (instrukcja) {
                        case "let":
                            wykonajLet(reszta);
                            break;
                        case "print":
                            wykonajPrint(reszta);
                            break;
                        case "goto":
                            wykonajGoto(reszta);
                            break;
                        case "if":
                            wykonajIf(reszta);
                            break;
                        case "input":
                            wykonajInput(reszta);
                            break;
                        case "gosub":
                            wykonajGosub(reszta);
                            break;
                        default:
                            break;

                    }
                }

            }

        }
    }

    Map<String, Integer> zmienne = new HashMap<>();

    public void wykonajLet(String reszta) {
        String[] dziel = reszta.split("\\=", 2);
        String zmienna = dziel[0].trim();
        String wyrazenie = dziel[1].trim();

        if (wyrazenie.contains("+")) {
            String[] dodawanie = wyrazenie.split("\\+", 2);
            String war1 = dodawanie[0].trim();
            String war2 = dodawanie[1].trim();

            if (zmienne.containsKey(war1) && !zmienne.containsKey(war2)) {
                int wartosc = zmienne.get(war1);
                zmienne.put(zmienna, wartosc + Integer.parseInt(war2));

            }
            if (zmienne.containsKey(war2) && !zmienne.containsKey(war1)) {
                int wartosc = zmienne.get(war2);
                zmienne.put(zmienna, wartosc + Integer.parseInt(war1));
            }
            if (zmienne.containsKey(war1) && zmienne.containsKey(war2)) {
                int wartosc1 = zmienne.get(war1);

                int wartosc2 = zmienne.get(war2);
                zmienne.put(zmienna, wartosc1 + wartosc2);
            }

        } else if (wyrazenie.contains("-")) {
            String[] odejmowanie = wyrazenie.split("\\-", 2);
            String war1 = odejmowanie[0].trim();
            String war2 = odejmowanie[1].trim();
            if (zmienne.containsKey(war1) && !zmienne.containsKey(war2)) {
                int wartosc = zmienne.get(war1);
                zmienne.put(zmienna, wartosc - Integer.parseInt(war2));
            }
            if (zmienne.containsKey(war2) && !zmienne.containsKey(war1)) {
                int wartosc = zmienne.get(war2);
                zmienne.put(zmienna, Integer.parseInt(war1) - wartosc);
            }
            if (zmienne.containsKey(war1) && zmienne.containsKey(war2)) {
                int wartosc1 = zmienne.get(war1);
                int wartosc2 = zmienne.get(war2);
                zmienne.put(zmienna, wartosc1 - wartosc2);
            }
        } else if (wyrazenie.contains("*")) {
            String[] razy = wyrazenie.split("\\*", 2);
            String war1 = razy[0].trim();
            String war2 = razy[1].trim();
            if (zmienne.containsKey(war1) && !zmienne.containsKey(war2)) {
                int wartosc = zmienne.get(war1);
                zmienne.put(zmienna, wartosc * Integer.parseInt(war2));
            }
            if (zmienne.containsKey(war2) && !zmienne.containsKey(war1)) {
                int wartosc = zmienne.get(war2);
                zmienne.put(zmienna, Integer.parseInt(war1) * wartosc);
            }
            if (zmienne.containsKey(war1) && zmienne.containsKey(war2)) {
                int wartosc1 = zmienne.get(war1);
                int wartosc2 = zmienne.get(war2);
                zmienne.put(zmienna, wartosc1 * wartosc2);
            }
        } else if (wyrazenie.contains("/")) {
            String[] dzielic = wyrazenie.split("\\/", 2);
            String war1 = dzielic[0].trim();
            String war2 = dzielic[1].trim();
            if (zmienne.containsKey(war1) && !zmienne.containsKey(war2)) {
                int wartosc = zmienne.get(war1);
                zmienne.put(zmienna, wartosc / Integer.parseInt(war2));
            }
            if (zmienne.containsKey(war2) && !zmienne.containsKey(war1)) {
                int wartosc = zmienne.get(war2);
                zmienne.put(zmienna, Integer.parseInt(war1) / wartosc);
            }
            if (zmienne.containsKey(war1) && zmienne.containsKey(war2)) {
                int wartosc1 = zmienne.get(war1);
                int wartosc2 = zmienne.get(war2);
                zmienne.put(zmienna, wartosc1 / wartosc2);
            }
        } else {
            zmienne.put(zmienna, Integer.parseInt(wyrazenie.trim()));
        }

    }

    public void wykonajGoto(String reszta) {
        int gdzie = Integer.parseInt(reszta.trim());
        run(gdzie);
        stopExecution = true;
    }

    public void wykonajGosub(String reszta) {
        StosDoGosub.push(linijkaKodu + 1);
        wykonajGoto(reszta.trim());
    }

    public void wykonajIf(String reszta) {
        String reszta1 = reszta.toLowerCase();
        String[] podzial;
        podzial = reszta1.split("goto", 2);
        String wyrazenie = podzial[0];
        String gdzie = podzial[1].trim();
        if (wyrazenie.contains(">")) {
            String[] podzialWyrazenia = wyrazenie.split(">", 2);
            String zmienna1 = podzialWyrazenia[0].trim();
            String zmienna2 = podzialWyrazenia[1].trim();
            if (zmienne.containsKey(zmienna1) && !zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = Integer.parseInt(zmienna2);
                if (zm1 > zm2) {
                    wykonajGoto(gdzie);
                }
            }
            if (zmienne.containsKey(zmienna2) && !zmienne.containsKey(zmienna1)) {
                int zm1 = Integer.parseInt(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 > zm2) {
                    wykonajGoto(gdzie);
                }
            }
            if (zmienne.containsKey(zmienna1) && zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 > zm2) {
                    wykonajGoto(gdzie);
                }
            }
        } else if (wyrazenie.contains("<")) {
            String[] podzialWyrazenia = wyrazenie.split("<", 2);
            String zmienna1 = podzialWyrazenia[0].trim();
            String zmienna2 = podzialWyrazenia[1].trim();
            if (zmienne.containsKey(zmienna1) && !zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = Integer.parseInt(zmienna2);
                if (zm1 < zm2) {
                    wykonajGoto(gdzie);
                }

            }
            if (zmienne.containsKey(zmienna2) && !zmienne.containsKey(zmienna1)) {
                int zm1 = Integer.parseInt(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 < zm2) {
                    wykonajGoto(gdzie);
                }
            }
            if (zmienne.containsKey(zmienna1) && zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 < zm2) {
                    wykonajGoto(gdzie);
                }
            }
        } else if (wyrazenie.contains("=")) {
            String[] podzialWyrazenia = wyrazenie.split("=", 2);
            String zmienna1 = podzialWyrazenia[0].trim();
            String zmienna2 = podzialWyrazenia[1].trim();

            if (zmienne.containsKey(zmienna1) && !zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = Integer.parseInt(zmienna2);
                if (zm1 == zm2) {
                    wykonajGoto(gdzie);
                }
            }
            if (zmienne.containsKey(zmienna2) && !zmienne.containsKey(zmienna1)) {
                int zm1 = Integer.parseInt(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 == zm2) {
                    wykonajGoto(gdzie);
                }
            }
            if (zmienne.containsKey(zmienna1) && zmienne.containsKey(zmienna2)) {
                int zm1 = zmienne.get(zmienna1);
                int zm2 = zmienne.get(zmienna2);
                if (zm1 == zm2) {
                    wykonajGoto(gdzie);
                }
            }
        }

    }

    public void wykonajPrint(String reszta) {
        if (reszta.contains("\"")) {
            char firstChar = reszta.charAt(0);
            char lastChar = reszta.charAt(reszta.length() - 1);
            String bezCudzyslowia = reszta.substring(1, reszta.length() - 1);
            stdout.printLine(bezCudzyslowia);
        }

        else {
            stdout.printLine(String.valueOf(zmienne.get(reszta)));
        }
    }

    public void wykonajInput(String reszta) {
        int wartoscZczytana = Integer.parseInt(stdin.readLine());
        zmienne.put(reszta, wartoscZczytana);
    }
}
