package part1;

import part1.exceptions.EndOfLineException;
import part1.exceptions.UnknownStateExxception;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int currentIndex = 0,
            currentLine = 0;

    static List<String> text = new ArrayList<>();


    private static Lexeme get_token() throws EndOfLineException, UnknownStateExxception {
        try {
            while (text.get(currentLine).charAt(currentIndex) == ' ') {
                currentIndex++;
                if (currentIndex >= text.get(currentLine).length()) {
                    throw new EndOfLineException();
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new EndOfLineException();
        }
        try {
            char currentChar = text.get(currentLine).charAt(currentIndex);
            if (currentChar == 'b') {
                if (text.get(currentLine).charAt(currentIndex + 1) == 'a') {
                    currentIndex += 2;
                    return Lexeme.IDEN_BA;
                }
                if (text.get(currentLine).substring(currentIndex, currentIndex + "begin".length()).equals("begin")) {
                    currentIndex += "begin".length();
                    return Lexeme.BEGIN;
                } else {
                    throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
                }
            }
            switch (currentChar) {
                case '=': {
                    currentIndex++;
                    return Lexeme.EQUAL;
                }
                case 'a': {
                    if (text.get(currentLine).charAt(currentIndex + 1) == 'b') {
                        currentIndex += 2;
                        return Lexeme.IDEN_AB;
                    } else {
                        throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
                    }
                }
                case ';': {
                    currentIndex++;
                    return Lexeme.POINT_WITH_COMA;
                }
                case '*': {
                    currentIndex++;
                    return Lexeme.MULTIPLY;
                }
                case '/': {
                    currentIndex++;
                    return Lexeme.DIV;
                }
                case '^': {
                    currentIndex++;
                    return Lexeme.POW;
                }
                case 'e': {
                    if (text.get(currentLine).substring(currentIndex, currentIndex + "end;".length()).equals("end;")) {
                        currentIndex += "end;".length();
                        return Lexeme.END;
                    } else {
                        throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
                    }
                }
                case '1': {
                    if (text.get(currentLine).substring(currentIndex, currentIndex + "100".length()).equals("100")) {
                        currentIndex += 3;
                        return Lexeme.C_100;
                    } else if (text.get(currentLine).substring(currentIndex, currentIndex + "10".length()).equals("10")) {
                        currentIndex += 2;
                        return Lexeme.C_10;
                    } else {
                        throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
                    }
                }
                default: {
                    throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            throw new UnknownStateExxception("< (" + currentLine + ":" + currentIndex + ") Unknown lexeme>");
        }

    }

    public static void main(String[] args) {

        try {
            File file = new File("D:\\MyLabs\\CompilationTheory\\Lab2\\CompilationTheoryLab2\\src\\part1\\input.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();

            while (line != null) {
                text.add(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int currentOutputFileIndex = 0;
        while (currentLine < text.size()) {
            if (text.get(currentLine).length() == 0) {
                currentLine++;
                currentIndex = 0;
            } else {
                try (FileWriter writer = new FileWriter("D:\\MyLabs\\CompilationTheory\\Lab2\\CompilationTheoryLab2\\src\\part1\\output" + currentOutputFileIndex + ".txt", true)) {
                    try {
                        writer.write(get_token().getInfo() + "\n");
                    } catch (EndOfLineException endOfLineException) {
                        currentIndex = 0;
                        currentLine++;
                        currentOutputFileIndex++;
                    } catch (UnknownStateExxception unknownStateExxception) {
                        currentOutputFileIndex++;
                        currentIndex = 0;
                        currentLine++;
                        writer.write(unknownStateExxception.getMessage() + "\n");
                    }
                    writer.flush();
                }catch(IOException ex){
                    System.out.println(ex.getMessage());
                }

            }

        }


    }

    enum Lexeme {
        BEGIN {
            @Override
            String getInfo() {
                return "< begin_key_word 'begin' >";
            }
        },
        IDEN_AB {
            @Override
            String getInfo() {
                return "< identifier 'ab'>";
            }
        },
        IDEN_BA {
            @Override
            String getInfo() {
                return "< identifier 'ba'>";
            }
        },
        EQUAL {
            @Override
            String getInfo() {
                return "< assignment_operator '=' >";
            }
        },
        POINT_WITH_COMA {
            @Override
            String getInfo() {
                return "< point_with_coma ';' >";
            }
        },
        END {
            @Override
            String getInfo() {
                return "< end_key_word 'end;' >";
            }
        },
        MULTIPLY {
            @Override
            String getInfo() {
                return "< multiply_operator '*' >";
            }
        },
        DIV {
            @Override
            String getInfo() {
                return "< division_operator '/' >";
            }
        },
        POW {
            @Override
            String getInfo() {
                return "< elevation_to_the_degree_operator '^' >";
            }
        },
        C_10 {
            @Override
            String getInfo() {
                return "< number '10' >";
            }
        },
        C_100 {
            @Override
            String getInfo() {
                return "< number '100' >";
            }
        };

        abstract String getInfo();
    }
}
