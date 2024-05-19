package services;

import java.util.Scanner;

public interface CrudService {
    void create(Scanner scanner);
    void read(Scanner scanner);
    void update(Scanner scanner);
    void delete(Scanner scanner);
}
