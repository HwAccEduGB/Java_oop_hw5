package notebook.view;


import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;
        String userId;

        while (true) {
            String command = prompt("Введите команду:\n" +
                    "Добавить нового пользователя -> введите CREATE\n" +
                    "Посмотреть данные пользователя -> введите READ\n" +
                    "Посмотреть всю книгу пользователей -> введите READALL\n" +
                    "Удалить пользователя -> введите DELETE\n" +
                    "Внести изменения в данные пользователя -> введите UPDATE\n" +
                    "Завершить работу с приложением -> введите EXIT\n").toUpperCase();

            com = Commands.valueOf(command);
            switch (com) {
                case EXIT:
                    System.exit(0);
                    break;
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case READALL:
                    System.out.println(userController.readAll());
                    break;
                case UPDATE:
                    userId = prompt("Enter user id: ");
                    userController.updateUser(userId, createUser());
                    break;
                case DELETE:
                    userId = prompt("Enter user id: ");
                    if (userController.deleteUser(userId)){
                        System.out.println("Пользователь успешно удалён");
                    }else System.out.println("Такой пользователь не обнаружен");
                    break;
                default:
                    System.out.println("Введена неверная команда.");
                    break;
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    public String checkLine(String str) {
        str = str.trim().replace(" ", "");
        if (!str.isEmpty()) {
            return str;
        } else {
            System.out.println("Значение не может быть пустым.\n");
            str = prompt("Введите корректные данные: ");
            return checkLine(str);
        }
    }

    private User createUser() {
        String firstName = checkLine(prompt("Имя: "));
        String lastName = checkLine(prompt("Фамилия: "));
        String phone = checkLine(prompt("Номер телефона: "));
        return new User(firstName, lastName, phone);
    }
}
