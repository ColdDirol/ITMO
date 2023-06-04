package collection;

import clientlogic.output.ConsoleOutputMode;

import java.util.ArrayList;
import java.util.Scanner;

public class ElementConstructor {
    ArrayList<String> objectElementsArrayList;

    public ElementConstructor() {
        objectElementsArrayList = new ArrayList<>();
    }

    public ArrayList<String> construct(Scanner scanner, ConsoleOutputMode consoleOutputMode) {
        clearObjectElementsArrayList();
        String value = null;

        if(consoleOutputMode.equals(ConsoleOutputMode.READABLE)) {
            System.out.print("Введите название квартиры: ");
            value = scanner.nextLine();
            while (value == null || value.trim().isEmpty()) {
                System.out.print("Введите название квартиры корректно: ");
                value = scanner.nextLine();
            }
            objectElementsArrayList.add(value);


            System.out.print("Введите координату X: ");
            while (true) {
                value = scanner.nextLine();
                try {
                    objectElementsArrayList.add(String.valueOf(Float.parseFloat(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Пожалуйста, введите число: ");
                }
            }


            System.out.print("Введите координату Y: ");
            while (true) {
                value = scanner.nextLine();
                try {
                    objectElementsArrayList.add(String.valueOf(Float.parseFloat(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Пожалуйста, введите число: ");
                }
            }


            System.out.print("Введите площадь квартиры (от 0): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Введите площадь квартиры корректно: ");
                        continue;
                    }
                    objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите площадь квартиры корректно: ");
                }
            }


            System.out.print("Введите количество комнат (от 0 до 7): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 7) {
                        System.out.print("Введите количество комнат корректно: ");
                        continue;
                    }
                    objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите количество комнат корректно: ");
                }
            }


            System.out.println("Введите номер обстановки мебели в квартире: ");
            System.out.println("1 - DESIGNER, 2 - NONE, 3 - FINE, 4 - BAD, 5 - LITTLE");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 5) {
                        System.out.print("Введите номер варианта корректно: ");
                        continue;
                    } else {
                        objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите номер варианта корректно: ");
                }
            }


            System.out.println("Введите номер вида из квартиры: ");
            System.out.println("1 - YARD, 2 - PARK, 3 - BAD, 4 - GOOD");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 4) {
                        System.out.print("Введите номер варианта корректно: ");
                        continue;
                    } else {
                        objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите номер варианта корректно: ");
                }
            }


            System.out.println("Введите номер доступности транспорта: ");
            System.out.println("1 - FEW, 2 - NONE, 3 - ENOUGH");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 3) {
                        System.out.print("Введите номер варианта корректно: ");
                        continue;
                    } else {
                        objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите номер варианта корректно: ");
                }
            }


            System.out.print("Введите название дома: ");
            value = scanner.nextLine();
            while (value == null || value.trim().isEmpty()) {
                System.out.print("Введите название дома корректно: ");
                value = scanner.nextLine();
            }
            objectElementsArrayList.add(value);


            System.out.print("Введите возраст дома (от 0 до 874): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 874) {
                        System.out.print("Введите возраст дома корректно: ");
                        continue;
                    }
                    objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите возраст дома корректно: ");
                }
            }


            System.out.print("Введите количество лифтов в доме (от 0): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Введите количество лифтов корректно: ");
                        continue;
                    }
                    objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите количество лифтов корректно: ");
                }
            }
        }

        //UNREADABLE
        if(consoleOutputMode.equals(ConsoleOutputMode.UNREADABLE)) {
            try {
                // Flat name
                value = scanner.nextLine();
                objectElementsArrayList.add(value);

                // Coordinate X
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Float.parseFloat(value)));

                // Coordinate Y
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Float.parseFloat(value)));

                // Flat area
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));

                // Flat number of rooms
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));

                // Flat FURNISH
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));

                // Flat VIEW
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));

                // Flat TRANSPORT (availability)
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));

                // House name
                value = scanner.nextLine();
                objectElementsArrayList.add(value);

                // House age
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));


                // House number of lifts
                value = scanner.nextLine();
                objectElementsArrayList.add(String.valueOf(Integer.parseInt(value)));
            } catch (NumberFormatException e) {
                System.out.println("ERROR:");
                System.out.println("Illegal form of input value!");
            }
        }
        //System.out.println(objectElementsArrayList);
        return objectElementsArrayList;
    }

    private void clearObjectElementsArrayList() {
        objectElementsArrayList.clear();
    }
}
