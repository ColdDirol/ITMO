package collection.flatcollection;

import collection.flatcollection.flat.Flat;
import collection.flatcollection.flat.Furnish;
import collection.flatcollection.flat.Transport;
import collection.flatcollection.flat.View;
import org.json.simple.parser.ParseException;
import serverlogic.outputchannel.ResponseArrayList;
import serverlogic.outputchannel.ResponseLogic;

import java.io.IOException;
import java.util.Scanner;


/**
 * The class ElementConstructor() is class for create new Flat object when program has started
 */
public class ElementConstructor {
    Scanner scanner;

    Flat flat;

    String value;

    public Flat elementConstruct(Integer clientId, Integer userId) {
        try {
            flat = construct(clientId, userId);

            flat.creationDateGenerator();
            flat.setId(getNextId());
            flat.setUser_id(userId);

            return flat;
        } catch (NullPointerException e) {
            System.out.println("client " + clientId + " has been disconnected!");
            return null;
        }
    }

    public Flat elementConstruct(Integer clientId, Integer userId, int saveId) {
        try {
            flat = construct(clientId, userId);

            flat.creationDateGenerator();
            flat.setId(saveId);
            flat.setUser_id(userId);

            return flat;
        } catch (NullPointerException e) {
            System.out.println("client " + clientId + " has been disconnected!");
            return null;
        }
    }

    private Flat construct(Integer clientId, Integer userId) {
        try {
            ResponseLogic responseLogic = new ResponseLogic();
            ResponseArrayList responseArrayList = new ResponseArrayList();

            scanner = new Scanner(System.in);

            flat = new Flat();


            System.out.print("Введите название квартиры: ");
            responseArrayList.addStringToResponseArrayList("Введите название квартиры: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            //value = scanner.nextLine();
            value = responseLogic.getRequestFromCreds(clientId, userId);
            System.out.println(value);
            while (value == null || value.trim().isEmpty()) {
                System.out.print("Введите название квартиры корректно: ");
                responseArrayList.addStringToResponseArrayList("Введите название квартиры: ");
                responseLogic.sendResponseArrayListAsJSONObject(clientId);
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
            }
            flat.setName(value);


            System.out.print("Введите координату X: ");
            responseArrayList.addStringToResponseArrayList("Введите координату X: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    flat.setCoordinatesX(Long.parseLong(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Пожалуйста, введите число: ");
                    responseArrayList.addStringToResponseArrayList("Пожалуйста, введите число: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.print("Введите координату Y: ");
            responseArrayList.addStringToResponseArrayList("Введите координату Y: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    flat.setCoordinatesY(Long.parseLong(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Пожалуйста, введите число: ");
                    responseArrayList.addStringToResponseArrayList("Пожалуйста, введите число: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.print("Введите площадь квартиры (от 0): ");
            responseArrayList.addStringToResponseArrayList("Введите площадь квартиры (от 0): ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Введите площадь квартиры корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите площадь квартиры корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    }
                    flat.setArea(Integer.parseInt(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите площадь квартиры корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите площадь квартиры корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.print("Введите количество комнат (от 0 до 7): ");
            responseArrayList.addStringToResponseArrayList("Введите количество комнат (от 0 до 7): ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 7) {
                        System.out.print("Введите количество комнат корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите количество комнат корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    }
                    flat.setNumberOfRooms(Integer.parseInt(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите количество комнат корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите количество комнат корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.println("1 - DESIGNER, 2 - NONE, 3 - FINE, 4 - BAD");
            responseArrayList.addStringToResponseArrayList("1 - DESIGNER, 2 - NONE, 3 - FINE, 4 - BAD");
            System.out.println("Введите номер обстановки мебели в квартире: ");
            responseArrayList.addStringToResponseArrayList("Введите номер обстановки мебели в квартире: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 5) {
                        System.out.print("Введите номер варианта корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
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
                    responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }

            System.out.println("1 - YARD, 2 - PARK, 3 - BAD, 4 - GOOD");
            responseArrayList.addStringToResponseArrayList("1 - YARD, 2 - PARK, 3 - BAD, 4 - GOOD");
            System.out.println("Введите номер вида из квартиры: ");
            responseArrayList.addStringToResponseArrayList("Введите номер вида из квартиры: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 4) {
                        System.out.print("Введите номер варианта корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    } else {
                        if (Integer.parseInt(value) == 1) flat.setView(View.YARD);
                        if (Integer.parseInt(value) == 2) flat.setView(View.PARK);
                        if (Integer.parseInt(value) == 3) flat.setView(View.BAD);
                        if (Integer.parseInt(value) == 4) flat.setView(View.GOOD);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите номер варианта корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.println("1 - FEW, 2 - NONE, 3 - ENOUGH");
            responseArrayList.addStringToResponseArrayList("1 - FEW, 2 - NONE, 3 - ENOUGH");
            System.out.println("Введите номер доступности транспорта: ");
            responseArrayList.addStringToResponseArrayList("Введите номер доступности транспорта: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 1 | Integer.parseInt(value) > 3) {
                        System.out.print("Введите номер варианта корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    } else {
                        if (Integer.parseInt(value) == 1) flat.setTransport(Transport.FEW);
                        if (Integer.parseInt(value) == 2) flat.setTransport(Transport.NONE);
                        if (Integer.parseInt(value) == 3) flat.setTransport(Transport.ENOUGH);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите номер варианта корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите номер варианта корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.print("Введите название дома: ");
            responseArrayList.addStringToResponseArrayList("Введите название дома: ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            //value = scanner.nextLine();
            value = responseLogic.getRequestFromCreds(clientId, userId);
            System.out.println(value);
            while (value == null || value.trim().isEmpty()) {
                System.out.print("Введите название дома корректно: ");
                responseArrayList.addStringToResponseArrayList("Введите название дома корректно: ");
                responseLogic.sendResponseArrayListAsJSONObject(clientId);
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
            }
            flat.setHouseName(value);


            System.out.print("Введите возраст дома (от 0 до 874): ");
            responseArrayList.addStringToResponseArrayList("Введите возраст дома (от 0 до 874): ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 0 | Integer.parseInt(value) > 874) {
                        System.out.print("Введите возраст дома корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите возраст дома корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    }
                    flat.setHouseYear(Integer.parseInt(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите возраст дома корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите возраст дома корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }


            System.out.print("Введите количество лифтов в доме (от 0): ");
            responseArrayList.addStringToResponseArrayList("Введите количество лифтов в доме (от 0): ");
            responseLogic.sendResponseArrayListAsJSONObject(clientId);
            while (true) {
                //value = scanner.nextLine();
                value = responseLogic.getRequestFromCreds(clientId, userId);
                System.out.println(value);
                try {
                    if (Integer.parseInt(value) < 0) {
                        System.out.print("Введите количество лифтов корректно: ");
                        responseArrayList.addStringToResponseArrayList("Введите количество лифтов корректно: ");
                        responseLogic.sendResponseArrayListAsJSONObject(clientId);
                        continue;
                    }
                    flat.setHouseNumberOfLifts(Integer.parseInt(value));
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Введите количество лифтов корректно: ");
                    responseArrayList.addStringToResponseArrayList("Введите количество лифтов корректно: ");
                    responseLogic.sendResponseArrayListAsJSONObject(clientId);
                }
            }
            return flat;
        } catch (IOException e) {
            e.getMessage();
            return null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getNextId() {
        Integer id = ServerCollection.getHahstable().keySet().stream()
                .max(Integer::compareTo)
                .orElse(null);
        if(id != null) return id + 1;
        else return 0;
    }
}
