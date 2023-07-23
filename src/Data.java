import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол

Форматы данных:
фамилия, имя, отчество - строки
датарождения - строка формата dd.mm.yyyy
номертелефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.

Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть
код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.

Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы
данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы
java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией,
что именно неверно.

Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку
должны записаться полученные данные, вида
<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь
должен увидеть стектрейс ошибки.

Иванов Иван Иваныч 13.07.2005 89451312239 m
Иванов Иван Иваныч 13.07.2005 89451312239
Иванов Иван Иваныч 13.07.2005 88452226308 m
Иванова Иванна Ивановна 12.11.2011 89517318827 f
Иванов Иван Иваныч 13.0.2005 89451312 s
 */

public class Data extends NumberFormatException {

    public static void main(String[] args) {
        data();
    }

    public static void data() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные через пробел " +
                "(Фамилия Имя Отчество датарождения номертелефона пол): ");
        String stringData = scanner.nextLine().trim();
        String[] splitArray = stringData.split(" ");

        if (splitArray.length != 6) {
            throw new IllegalArgumentException("Вы ввели неверное количество данных.Повторите ввод.");
        }

        if (splitArray[3].length() == 10) {
            try {
                Date date = new SimpleDateFormat("dd.MM.yyyy").parse(splitArray[3]);
                System.out.println(date + " Вы ввели верный формат даты");
            } catch (Exception e) {
                throw new IllegalArgumentException("Неверная дата.");
            }
        }

        if (splitArray[4].length() == 11) {
            try {
                long phone = Long.parseLong(splitArray[4]);
                System.out.println(phone + " Вы ввели верный формат телефона");
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Неверный формат номера телефона");
            }
        }

        if (!Objects.equals(splitArray[5], "f") || !Objects.equals(splitArray[5], "m")) {
            try {
                char[] genderArray = splitArray[5].toCharArray();
                System.out.println(genderArray[0] + " Вы ввели ввели верный формат пола");
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Неверное значение пола");
            }
        }

        String fileName = "file.txt";
        if (splitArray[3].length() == 10 && splitArray[4].length() == 11
                && Objects.equals(splitArray[5], "f") || Objects.equals(splitArray[5], "m")) {
            try (FileWriter fw = new FileWriter(fileName, true)) {
                fw.write(String.join(", ", splitArray));
                fw.append('\n');
                System.out.println("Данные записаны в файл.");
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("Ошибка записи в файл");
                e.printStackTrace();
            }
        } else {
            System.out.println("Данные введеные неверно, повторите ввод.");
            data();
            scanner.close();
        }
    }
}





