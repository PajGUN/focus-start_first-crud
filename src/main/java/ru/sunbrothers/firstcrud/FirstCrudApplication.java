package ru.sunbrothers.firstcrud;

import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.sunbrothers.firstcrud.model.House;
import ru.sunbrothers.firstcrud.repository.HouseRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootApplication
public class FirstCrudApplication implements CommandLineRunner {

    @Autowired
    private HouseRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(FirstCrudApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        House house0 = new House("Комендантский 20", 1986);
//        House house1 = new House("Комендантский 18", 1986);
//        House house2 = new House("Комендантский 22", 1987);
//        repository.save(house0);
//        repository.save(house1);
//        repository.save(house2);
        @Cleanup
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        printMain();
        String line;
        while (!(line = reader.readLine()).equalsIgnoreCase("Exit")){
            switch (line.toUpperCase()){
                case "CREATE":
                    System.out.println("    БЛОК CREATE");
                    System.out.println("    Для создания здания введите - CREATE={адрес}={год постройки}");
                    System.out.println("    Пример создания здания - CREATE=Проспект Науки 7=1995");
                    System.out.println("    Для выхода в предыдущее меню введите - Back");
                    while (!(line = reader.readLine()).equalsIgnoreCase("Back")) {
                        try {
                            String[] strings = line.split("=");
                            switch (strings[0].toUpperCase()){
                                case "CREATE":
                                    House house = new House(strings[1],Integer.valueOf(strings[2]));
                                    repository.save(house);
                                    System.out.println("Здание было создано.");
                                    break;
                                default:
                                    System.out.println("Введите корректный параметр пункта меню!");
                                    break;

                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.out.println("Ошибка ввода параметра обновления, повторите ввод!");
                        } catch (NoSuchElementException e) {
                            System.out.println("Не удалось обнаружить здание для обновления с таким идентификатором.");
                        }
                    }
                    printMain();
                    break;

                case "FIND":
                    System.out.println("    БЛОК FIND");
                    System.out.println("    Для вывода всех зданий введите - All");
                    System.out.println("    Для поиска здания по его id введите - ID={номер здания в базе без кавычек}");
                    System.out.println("    Для поиска здания по году постройки введите - Y={год постройки без кавычек}");
                    System.out.println("    Пример поиска по году - Y=1986");
                    System.out.println("    Для выхода в предыдущее меню введите - Back");
                    while (!(line = reader.readLine().toUpperCase()).equalsIgnoreCase("Back")) {

                        try {
                            if (line.equalsIgnoreCase("All")){
                                for (House h : repository.findAll()) {System.out.println(h);}
                            }
                            else if (line.contains("ID=")){
                                System.out.println(repository.findById(Long.valueOf(line.replace("ID=",""))).get());
                            }
                            else if (line.contains("Y=")){
                                List<House> byBuildDate = repository.findByBuildDate(Integer.valueOf(line.replace("Y=", "")));
                                if (byBuildDate.size()>0) {
                                    for (House house : byBuildDate) {
                                        System.out.println(house);
                                    }
                                } else {
                                    System.out.println("Не удалось обнаружить здания с такими параметрами.");
                                }
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода параметра поиска, повторите ввод!");
                        } catch (NoSuchElementException e) {
                            System.out.println("Не удалось обнаружить здание с такими параметрами.");
                        }
                    }
                    printMain();
                    break;

                case "UPDATE":
                    System.out.println("    БЛОК UPDATE");
                    System.out.println("    Для изменения адреса введите - UPDATE ADDRESS={id}={адрес}");
                    System.out.println("    Для изменения года постройки введите - UPDATE BUILT={id}={год}");
                    System.out.println("    Для изменения района введите - UPDATE DISTRICT={id}={район}");
                    System.out.println("    Для изменения количества этажей введите - UPDATE FLOORS={id}={количество этажей}");
                    System.out.println("    Для изменения наличия парковки введите - UPDATE PARKING={id}={true or false}");
                    System.out.println("    Пример изменения района - UPDATE DISTRICT=66=Калининский");
                    System.out.println("    Пример изменения наличия паркинга - UPDATE PARKING=7=false");
                    System.out.println("    Для выхода в предыдущее меню введите - Back");
                    while (!(line = reader.readLine()).equalsIgnoreCase("Back")) {
                        try {
                            String[] strings = line.split("=");
                            House house;
                            switch (strings[0].toUpperCase()){
                                case "UPDATE ADDRESS":
                                    house = repository.findById(Long.valueOf(strings[1])).get();
                                    house.setAddress(strings[2]);
                                    repository.save(house);
                                    System.out.println("Адрес был изменён.");
                                    break;
                                case "UPDATE BUILT":
                                    house = repository.findById(Long.valueOf(strings[1])).get();
                                    house.setBuildDate(Integer.valueOf(strings[2]));
                                    repository.save(house);
                                    System.out.println("Дата была изменёна.");
                                    break;
                                case "UPDATE DISTRICT":
                                    house = repository.findById(Long.valueOf(strings[1])).get();
                                    house.setDistrictName(strings[2]);
                                    repository.save(house);
                                    System.out.println("Район был изменён.");
                                    break;
                                case "UPDATE FLOORS":
                                    house = repository.findById(Long.valueOf(strings[1])).get();
                                    house.setNumberOfFloors(Integer.valueOf(strings[2]));
                                    repository.save(house);
                                    System.out.println("Информация о количестве этажей былы изменена.");
                                    break;
                                case "UPDATE PARKING":
                                    house = repository.findById(Long.valueOf(strings[1])).get();
                                    house.setOwnParking(Boolean.valueOf(strings[2]));
                                    repository.save(house);
                                    System.out.println("Информация о наличии парковки была изменёна.");
                                    break;
                                default:
                                    System.out.println("Введите корректный параметр пункта меню!");
                                    break;

                            }
                        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                            System.out.println("Ошибка ввода параметра обновления, повторите ввод!");
                        } catch (NoSuchElementException e) {
                            System.out.println("Не удалось обнаружить здание для обновления с таким идентификатором.");
                        }
                    }
                    printMain();
                    break;
                case "DELETE":
                    System.out.println("    БЛОК DELETE");
                    System.out.println("    Для удаления здания введите - DELETE={id}");
                    System.out.println("    Для удаления всех зданий введите - DELETE={ALL}");
                    System.out.println("    Пример удаления по id - DELETE=12");
                    System.out.println("    Для выхода в предыдущее меню введите - Back");
                    while (!(line = reader.readLine().toUpperCase()).equalsIgnoreCase("Back")) {
                        try {
                            switch (line.toUpperCase()){
                                case "DELETE=ALL":
                                    repository.deleteAll();
                                    System.out.println("Все здания были удалены!");
                                    break;
                                default:
                                    repository.deleteById(Long.valueOf(line.replace("DELETE=","")));
                                    System.out.println("Здание удалено!");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка ввода параметра удаления, повторите ввод!");
                        } catch (EmptyResultDataAccessException e) {
                            System.out.println("Не удалось обнаружить здание для удаления с таким идентификатором.");
                        }
                    }
                    printMain();
                    break;
                default:
                    System.out.println("Введите корректный параметр пункта меню!");
                    break;
            }

        }
    }

    private void printMain() {
        System.out.println("ГЛАВНОЕ МЕНЮ");
        System.out.println("Для добавления нового здания введите - Create");
        System.out.println("Для поиска здания введите - Find");
        System.out.println("Для внесения изменений у здания введите - Update");
        System.out.println("Для удаления здания введите - Delete");
        System.out.println("Для выхода введите - Exit");
    }
}
