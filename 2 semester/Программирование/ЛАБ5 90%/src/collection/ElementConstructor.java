package collection;

import collection.flat.Flat;
import collection.flat.Furnish;
import collection.flat.Transport;
import collection.flat.View;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * The class ElementConstructor() is class for create new Flat object when program has started
 */
public class ElementConstructor {
    Scanner scanner;

    Flat flat;

    String value;

    public Flat elementConstruct() {
        flat = construct();

        flat.creationDateGenerator();
        flat.idGenerator();

        return flat;
    }
    public Flat elementConstruct(int saveId) {
        flat = construct();

        flat.creationDateGenerator();
        flat.setId(saveId);

        return flat;
    }

    public Flat elementConstruct(Scanner scanner) {
        flat = construct(scanner);
        if(flat.equals(null)) {
            System.out.println("Error creating object");
            return null;
        }
        flat.creationDateGenerator();
        flat.idGenerator();

        return flat;
    }

    public Flat elementConstruct(int saveId, Scanner scanner) {
        flat = construct(scanner);
        if(flat.equals(null)) {
            System.out.println("Error creating object");
            return null;
        }
        flat.creationDateGenerator();
        flat.setId(saveId);

        return flat;
    }

    private Flat construct() {
        scanner = new Scanner(System.in);

        flat = new Flat();


        System.out.print("Введите название квартиры: ");
        value = scanner.nextLine();
        while(value == null || value.trim().isEmpty()) {
            System.out.print("Введите название квартиры корректно: ");
            value = scanner.nextLine();
        }
        flat.setName(value);


        System.out.print("Введите координату X: ");
        while(true) {
            value = scanner.nextLine();
            try {
                flat.setCoordinatesX(Float.parseFloat(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
            }
        }


        System.out.print("Введите координату Y: ");
        while(true) {
            value = scanner.nextLine();
            try {
                flat.setCoordinatesY(Float.parseFloat(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
            }
        }


        System.out.print("Введите площадь квартиры (от 0): ");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 0) {
                    System.out.print("Введите площадь квартиры корректно: ");
                    continue;
                }
                flat.setArea(Integer.parseInt(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите площадь квартиры корректно: ");
            }
        }


        System.out.print("Введите количество комнат (от 0 до 7): ");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 0 | Integer.parseInt(value) > 7) {
                    System.out.print("Введите количество комнат корректно: ");
                    continue;
                }
                flat.setNumberOfRooms(Integer.parseInt(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите количество комнат корректно: ");
            }
        }


        System.out.println("Введите номер обстановки мебели в квартире: ");
        System.out.println("1 - DESIGNER, 2 - NONE, 3 - FINE, 4 - BAD, 5 - LITTLE");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 1 | Integer.parseInt(value) > 5) {
                    System.out.print("Введите номер варианта корректно: ");
                    continue;
                } else {
                    if (Integer.parseInt(value) == 1) flat.setFurnish(Furnish.DESIGNER);
                    if (Integer.parseInt(value) == 2) flat.setFurnish(Furnish.NONE);
                    if (Integer.parseInt(value) == 3) flat.setFurnish(Furnish.FINE);
                    if (Integer.parseInt(value) == 4) flat.setFurnish(Furnish.BAD);
                    if (Integer.parseInt(value) == 5) flat.setFurnish(Furnish.LITTLE);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите номер варианта корректно: ");
            }
        }


        System.out.println("Введите номер вида из квартиры: ");
        System.out.println("1 - YARD, 2 - PARK, 3 - BAD, 4 - GOOD");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 1 | Integer.parseInt(value) > 4) {
                    System.out.print("Введите номер варианта корректно: ");
                    continue;
                } else {
                    if(Integer.parseInt(value) == 1)  flat.setView(View.YARD);
                    if(Integer.parseInt(value) == 2)  flat.setView(View.PARK);
                    if(Integer.parseInt(value) == 3)  flat.setView(View.BAD);
                    if(Integer.parseInt(value) == 4)  flat.setView(View.GOOD);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите номер варианта корректно: ");
            }
        }


        System.out.println("Введите номер доступности транспорта: ");
        System.out.println("1 - FEW, 2 - NONE, 3 - ENOUGH");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 1 | Integer.parseInt(value) > 3) {
                    System.out.print("Введите номер варианта корректно: ");
                    continue;
                } else {
                    if(Integer.parseInt(value) == 1)  flat.setTransport(Transport.FEW);
                    if(Integer.parseInt(value) == 2)  flat.setTransport(Transport.NONE);
                    if(Integer.parseInt(value) == 3)  flat.setTransport(Transport.ENOUGH);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите номер варианта корректно: ");
            }
        }


        System.out.print("Введите название дома: ");
        value = scanner.nextLine();
        while(value == null || value.trim().isEmpty()) {
            System.out.print("Введите название дома корректно: ");
            value = scanner.nextLine();
        }
        flat.setHouseName(value);


        System.out.print("Введите возраст дома (от 0 до 874): ");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 0 | Integer.parseInt(value) > 874) {
                    System.out.print("Введите возраст дома корректно: ");
                    continue;
                }
                flat.setHouseYear(Integer.parseInt(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите возраст дома корректно: ");
            }
        }


        System.out.print("Введите количество лифтов в доме (от 0): ");
        while(true) {
            value = scanner.nextLine();
            try {
                if(Integer.parseInt(value) < 0) {
                    System.out.print("Введите количество лифтов корректно: ");
                    continue;
                }
                flat.setHouseNumberOfLifts(Integer.parseInt(value));
                break;
            } catch (NumberFormatException e) {
                System.out.print("Введите количество лифтов корректно: ");
            }
        }

        return flat;
    }

    // with scanner
    private Flat construct(Scanner scanner) {
        this.scanner = scanner;
        flat = new Flat();

        try {
            value = scanner.nextLine();
            if (value == null || value.trim().isEmpty()) {
                System.out.print("Error creating object.");
                return null;
            } else {
                flat.setName(value);
                System.out.println("+Flat name is set");
            }


//            System.out.print("Введите координату X: ");
            while (true) {
                value = scanner.nextLine();
                try {
                    flat.setCoordinatesX(Float.parseFloat(value));
                    System.out.println("+Coordinate X is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.print("Введите координату Y: ");
            while (true) {
                value = scanner.nextLine();
                try {
                    flat.setCoordinatesY(Float.parseFloat(value));
                    System.out.println("+Coordinate Y is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.print("Введите площадь квартиры (от 0): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Error creating object.");
                        return null;
                    }
                    flat.setArea(Integer.parseInt(value));
                    System.out.println("+Flat area is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.print("Введите количество комнат (от 0 до 7): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 7) {
                        System.out.print("Error creating object.");
                        return null;
                    }
                    flat.setNumberOfRooms(Integer.parseInt(value));
                    System.out.println("+Number of rooms is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.println("Введите номер обстановки мебели в квартире: ");
//            System.out.println("1 - DESIGNER, 2 - NONE, 3 - FINE, 4 - BAD, 5 - LITTLE");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 5) {
                        System.out.print("Error creating object.");
                        return null;
                    } else {
                        if (Integer.parseInt(value) == 1) flat.setFurnish(Furnish.DESIGNER);
                        if (Integer.parseInt(value) == 2) flat.setFurnish(Furnish.NONE);
                        if (Integer.parseInt(value) == 3) flat.setFurnish(Furnish.FINE);
                        if (Integer.parseInt(value) == 4) flat.setFurnish(Furnish.BAD);
                        if (Integer.parseInt(value) == 5) flat.setFurnish(Furnish.LITTLE);
                        System.out.println("+Furnish type is set");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.println("Введите номер вида из квартиры: ");
//            System.out.println("1 - YARD, 2 - PARK, 3 - BAD, 4 - GOOD");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 4) {
                        System.out.print("Error creating object.");
                        return null;
                    } else {
                        if (Integer.parseInt(value) == 1) flat.setView(View.YARD);
                        if (Integer.parseInt(value) == 2) flat.setView(View.PARK);
                        if (Integer.parseInt(value) == 3) flat.setView(View.BAD);
                        if (Integer.parseInt(value) == 4) flat.setView(View.GOOD);
                        System.out.println("+View type is set");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


//            System.out.println("Введите номер доступности транспорта: ");
//            System.out.println("1 - FEW, 2 - NONE, 3 - ENOUGH");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 3) {
                        System.out.print("Error creating object.");
                        return null;
                    } else {
                        if (Integer.parseInt(value) == 1) flat.setTransport(Transport.FEW);
                        if (Integer.parseInt(value) == 2) flat.setTransport(Transport.NONE);
                        if (Integer.parseInt(value) == 3) flat.setTransport(Transport.ENOUGH);
                        System.out.println("+View type is set");
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


            //System.out.print("Введите название дома: ");
            value = scanner.nextLine();
            if(value == null || value.trim().isEmpty()) {
                System.out.print("Error creating object.");
                return null;
            }
            flat.setHouseName(value);
            System.out.println("+House name is set");


            //System.out.print("Введите возраст дома (от 0 до 874): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 874) {
                        System.out.print("Error creating object.");
                        return null;
                    }
                    flat.setHouseYear(Integer.parseInt(value));
                    System.out.println("+House age is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }


            //System.out.print("Введите количество лифтов в доме (от 0): ");
            while (true) {
                value = scanner.nextLine();
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Error creating object.");
                        return null;
                    }
                    flat.setHouseNumberOfLifts(Integer.parseInt(value));
                    System.out.println("+House number of lifts is set");
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Error creating object.");
                    return null;
                }
            }

            return flat;
        } catch (NoSuchElementException e) {
            System.out.print("Error creating object.");
            return null;
        }
    }
}
