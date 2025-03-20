package view;

import domain.Nota;
import domain.Student;
import domain.Tema;
import service.Service;
import validation.ValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Interfata utilizator de tip consola
 */
public class UI {
    private Service service;

    /**
     * Class constructor
     * @param service - service-ul clasei
     */
    public UI(Service service) {
        this.service = service;
    }

    /**
     *
     * Metoda care ruleaza aplicatia
     */
    public void run() {
        System.out.println("Bine ati venit!");

        while (true) {
            try {


                System.out.println("1.Comenzi student");

                System.out.println("Meniu comenzi: ");
                System.out.println("0.Exit");
                System.out.println("1.Comenzi student");
                System.out.println("2.Comenzi teme");
                System.out.println("3.Comenzi note");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Introduceti comanda: ");
                int comanda = scanner.nextInt();
                if (comanda == 0) {
                    System.out.println("La revedere!");
                    break;
                } else if (comanda == 1) {
                    meniuStudent();
                } else if (comanda == 2) {
                    meniuTeme();
                } else if (comanda == 3) {
                    meniuNote();
                } else {
                    System.out.println("Comanda invalida!");
                }
            } catch (ValidationException exception) {
                System.out.println(exception.getMessage());
            } catch (InputMismatchException exception) {
                System.out.println("Date introduse gresit!");
            } catch (ArrayIndexOutOfBoundsException exception) {
                System.out.println("Eroare la introducerea datelor!");
            }
        }
    }

    /**
     * Afiseaza meniul de comenzi asupra studentilor
     */
    private void meniuStudent() {
        while (true) {
            System.out.println("\n0.Iesire meniu student");
            System.out.println("1.Introducere student");
            System.out.println("2.Stergere student");
            System.out.println("3.Cautare student");
            System.out.println("4.Modificare student");
            System.out.println("5.Afisare lista studenti");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduceti comanda: ");
            int comanda = scanner.nextInt();
            if (comanda == 0) {
                break;
            } else if (comanda == 1) {
                adaugaStudent();
            } else if (comanda == 2) {
                stergeStudent();
            } else if (comanda == 3) {
                cautareStudent();
            } else if (comanda == 4) {
                updateStudent();
            } else if (comanda == 5) {
                afisareStudenti();
            } else {
                System.out.println("Comanda invalida!");
            }
        }
    }

    /**
     * Adauga un student
     * @throws ValidationException daca datele studentul exista deja
     */
    private void adaugaStudent() {
        Scanner scanner = new Scanner(System.in);
        String idStudent;

        while (true) {
            System.out.print("Introduceti id student: ");
            idStudent = scanner.next();
            if (service.findStudent(idStudent) != null) {
                System.out.println("Studentul exista! Incercati din nou.");
            } else {
                break;
            }
        }

        scanner.nextLine();

        String numeStudent;
        while (true) {
            System.out.print("Introduceti numele: ");
            numeStudent = scanner.nextLine().trim();
            if (!numeStudent.isEmpty()) {
                break;
            } else {
                System.out.println("Numele nu poate fi gol! Incercati din nou.");
            }
        }

        int grupa;
        while (true) {
            System.out.print("Introduceti grupa: ");
            if (scanner.hasNextInt()) {
                grupa = scanner.nextInt();
                break;
            } else {
                System.out.println("Input invalid! Introduceti un numar pentru grupa.");
                scanner.next();
            }
        }

        scanner.nextLine();

        String email;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        while (true) {
            System.out.print("Introduceti email: ");
            email = scanner.next();
            if (Pattern.matches(emailRegex, email)) {
                break;
            } else {
                System.out.println("Format email invalid! Introduceti un email valid.");
            }
        }

        Student student = new Student(idStudent, numeStudent, grupa, email);
        Student student1 = service.addStudent(student);

        if (student1 == null) {
            System.out.println("Student adaugat cu succes!");
        } else {
            System.out.println("Studentul deja exista: " + student1);
        }
    }

    /**
     * Sterge un student
     */
    private void stergeStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul student: pe care doriti sa il stergeti: ");
        String id = scanner.next();
        Student student = service.deleteStudent(id);
        if (student == null) {
            System.out.println("Studentul nu exista!");
        } else {
            System.out.println("Student sters cu succes!");
        }
    }

    /**
     * Cauta un student
     */
    private void cautareStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul studentului: ");
        String id = scanner.next();
        Student student = service.findStudent(id);
        if (student == null) {
            System.out.println("Studentul nu exista!");
        } else {
            System.out.println(student);
        }
    }

    /**
     * Modifica datele unui student
     */
    private void updateStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul studentului pe care doriti sa il modificati: ");
        String id = scanner.next();
        System.out.println("Introduceti datele noi");
        System.out.print("Introduceti numele: ");
        scanner.nextLine();
        String nume = scanner.nextLine();
        System.out.print("Introduceti grupa: ");
        int grupa = scanner.nextInt();
        System.out.print("Introduceti email: ");
        String email = scanner.next();
        Student student = new Student(id, nume, grupa, email);
        Student student1 = service.updateStudent(student);
        if (student1 == null) {
            System.out.print("Studentul nu exista!");
        } else {
            System.out.println("Student modificat cu succes!" + student1);
        }
    }

    /**
     * Afiseaza lista studentilor
     */
    private void afisareStudenti() {
        Iterable<Student> all = service.getAllStudenti();
        all.forEach(student ->
                System.out.println(student)
        );
    }

    /**
     * Afiseaza comenzile pentru teme
     */
    private void meniuTeme() {
        while (true) {
            System.out.println("\n0.Iesire meniu teme");
            System.out.println("1.Introducere tema");
            System.out.println("2.Prelungire deadline");
            System.out.println("3.Stergere tema");
            System.out.println("4.Cautare tema");
            System.out.println("5.Modificare tema");
            System.out.println("6.Afisare lista teme");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduceti comanda: ");
            int comanda = scanner.nextInt();
            if (comanda == 0) {
                break;
            } else if (comanda == 1) {
                adaugaTema();
            }
            else if(comanda==2) {
                prelungireDeadline();
            }
            else if (comanda == 3) {
                stergeTema();
            } else if (comanda == 4) {
                cautareTema();
            } else if (comanda == 5) {
                updateTema();
            } else if (comanda == 6) {
                afisareTeme();
            } else {
                System.out.println("Comanda invalida!");
            }
        }
    }

    /**
     * Adauga o tema
     * @throws ValidationException daca tema exista deja
     */

    private void adaugaTema() throws ValidationException {
        Scanner scanner = new Scanner(System.in);
        String nrTema;

        while (true) {
            System.out.print("Introduceti nr tema: ");
            nrTema = scanner.next();
            if (service.findTema(nrTema) != null) {
                System.out.println("Tema exista deja! Incercati din nou.");
            } else {
                break;
            }
        }

        scanner.nextLine();

        String descriere;
        while (true) {
            System.out.print("Introduceti descrierea: ");
            descriere = scanner.nextLine().trim();
            if (!descriere.isEmpty()) {
                break;
            }
            else {
                System.out.println("The description must not be empty!");
            }
        }

        int deadline;
        while (true) {
            System.out.print("Introduceti deadline-ul (nr saptamanii): ");
            if (scanner.hasNextInt()) {
                deadline = scanner.nextInt();
                if (deadline >= 1 && deadline <= 14) {
                    break;
                }
               else {
                   System.out.println("The deadline value must be between 1 and 14!");
                }

            } else {
                System.out.println("Input invalid! Introduceti un numar valid pentru deadline.");
                scanner.next();
            }
        }

        int primire;
        while (true) {
            System.out.print("Introduceti saptamana primirii: ");
            if (scanner.hasNextInt()) {
                primire = scanner.nextInt();
                if (primire >= 1 && primire <= 14) {
                    if (primire <= deadline) {
                        break;
                    } else {
                        System.out.println("Saptamana primirii trebuie sa fie inainte de deadline! Incercati din nou.");
                    }
                } else {
                    System.out.println("Saptamana primirii invalida! Introduceti un numar intre 1 si 14.");
                }
            } else {
                System.out.println("Input invalid! Introduceti un numar valid pentru saptamana primirii.");
                scanner.next();
            }
        }

        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        tema = service.addTema(tema);

        if (tema == null) {
            System.out.println("Tema adaugata cu succes!");
        } else {
            System.out.println("Tema deja exista: " + tema);
        }
    }



    /**
     * Prelungeste deadline-ul unei teme
     */
    private void prelungireDeadline(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id tema: ");
        String nrTema = scanner.next();
        System.out.print("Introduceti deadline nou: ");
        int deadline;
        while (true) {
            if (scanner.hasNextInt()) {
                deadline = scanner.nextInt();
                if (deadline >= 1 && deadline <= 14) {
                    break;
                } else {
                    System.out.println("The deadline value must be between 1 and 14!");
                }

            } else {
                System.out.println("Input invalid! Introduceti un numar valid pentru deadline.");
                scanner.next();
            }
        }
        service.prelungireDeadline(nrTema, deadline);
        System.out.println("Dealine prelungit!");
    }

    /**
     * Sterge o tema
     */
    private void stergeTema() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul temei: pe care doriti sa o stergeti: ");
        String id = scanner.next();
        Tema tema = service.deleteTema(id);
        if (tema == null) {
            System.out.println("Tema nu exista!");
        } else {
            System.out.println("Tema stearsa cu succes!");
        }
    }

    /**
     * Cauta o tema
     */
    private void cautareTema() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul temei: ");
        String id = scanner.next();
        Tema tema = service.findTema(id);
        if (tema == null) {
            System.out.println("Tema nu exista!");
        } else {
            System.out.println(tema);
        }
    }

    /**
     * Modifica o tema
     */
    private void updateTema() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul temei pe care doriti sa o modificati: ");
        String id = scanner.next();
        System.out.println("Introduceti datele noi");
        System.out.print("Introduceti descrierea: ");
        scanner.nextLine();
        String descriere = scanner.nextLine();
        System.out.print("Introduceti deadline: ");
        int deadline = scanner.nextInt();
        System.out.print("Introduceti saptamana primire: ");
        int primire = scanner.nextInt();
        Tema tema = new Tema(id, descriere, deadline, primire);
        Tema tema1 = service.updateTema(tema);
        if (tema1 == null) {
            System.out.println("Tema nu exista!");
        } else {
            System.out.println("Tema modificata cu succes!" + tema1);
        }
    }

    /**
     * Afiseaza toate temele
     */
    private void afisareTeme(){
        Iterable<Tema> all = service.getAllTeme();
        //for(Tema tema: all){
        //    System.out.println(tema);
        //}
        all.forEach(tema -> System.out.println(tema));
    }

    /**
     * Afiseaza comenzile disponibile pentru note
     */
    private void meniuNote() {
        while (true) {
            System.out.println("\n0.Iesire meniu note");
            System.out.println("1.Introducere nota");
            System.out.println("2.Stergere nota");
            System.out.println("3.Cautare nota");
            System.out.println("4.Afisare lista note");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduceti comanda: ");
            int comanda = scanner.nextInt();
            if (comanda == 0) {
                break;
            } else if (comanda == 1) {
                adaugaNota();
            } else if (comanda == 2) {
                stergeNota();
            } else if (comanda == 3) {
                cautareNota();
            } else if (comanda == 4) {
                afisareNote();
            } else {
                System.out.println("Comanda invalida!");
            }
        }
    }

    /**
     * Adauga o nota
     * @throws ValidationException daca nota exista deja
     */
    private void adaugaNota() throws ValidationException {
        Scanner scanner = new Scanner(System.in);
        String idStudent, nrTema;

        while (true) {
            System.out.print("Introduceti id student: ");
            idStudent = scanner.next();
            if (!idStudent.isEmpty()) {
                break;
            } else {
                System.out.println("ID-ul studentului nu poate fi gol! Incercati din nou.");
            }
        }

        while (true) {
            System.out.print("Introduceti numarul temei: ");
            nrTema = scanner.next();
            if (!nrTema.isEmpty()) {
                break;
            } else {
                System.out.println("Numarul temei nu poate fi gol! Incercati din nou.");
            }
        }

        String idNota = idStudent + "#" + nrTema;
        if (service.findNota(idNota) != null) {
            throw new ValidationException("Nota exista deja!");
        }

        double nota;
        while (true) {
            System.out.print("Introduceti nota: ");
            if (scanner.hasNextDouble()) {
                nota = scanner.nextDouble();
                if (nota >= 1 && nota <= 10) {
                    break;
                } else {
                    System.out.println("Nota invalida! Introduceti o valoare intre 1 si 10.");
                }
            } else {
                System.out.println("Input invalid! Introduceti o valoare numerica pentru nota.");
                scanner.next();
            }
        }

        LocalDate dataPredare;
        while (true) {
            System.out.print("Introduceti data predarii temei (format: YYYY-MM-DD): ");
            String data = scanner.next();
            try {
                dataPredare = LocalDate.parse(data);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data introdusa este invalida! Folositi formatul corect (YYYY-MM-DD).");
            }
        }

        scanner.nextLine();

        String feedback;
        while (true) {
            System.out.print("Introduceti feedback: ");
            feedback = scanner.nextLine().trim();
            if (!feedback.isEmpty()) {
                break;
            } else {
                System.out.println("Feedback-ul nu poate fi gol! Incercati din nou.");
            }
        }

        Nota notaCatalog = new Nota(idNota, idStudent, nrTema, nota, dataPredare);
        double notaFinala = service.addNota(notaCatalog, feedback);

        System.out.println("Nota maxima pe care o poate primi studentul este: " + notaFinala);
    }

    /**
     * Sterge o nota
     */
    private void stergeNota() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul studentului: ");
        String idStudent = scanner.next();
        System.out.print("Introduceti nr-ul temei: ");
        int nrTema;
        while (true) {
            if (scanner.hasNextInt()) {
                nrTema = scanner.nextInt();
                break;
            } else {
                System.out.println("Input invalid! Introduceti un numar valid pentru deadline.");
                scanner.next();
            }
        }
        String idNota = idStudent + "#" + nrTema;
        Nota nota = service.deleteNota(idNota);
        if (nota == null) {
            System.out.println("Nota nu exista!");
        } else {
            System.out.println("Nota stearsa cu succes!");
        }
    }

    /**
     * Cauta o nota
     */
    private void cautareNota() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti id-ul studentului: ");
        String idStudent = scanner.next();
        System.out.print("Introduceti nr-ul temei: ");
        String nrTema = scanner.next();
        String idNota = idStudent + "#" + nrTema;
        Nota nota = service.findNota(idNota);
        if (nota == null) {
            System.out.println("Nota nu exista!");
        } else {
            System.out.println(nota);
        }
    }

    /**
     * Afiseaza toate notele
     */
    private void afisareNote() {
        Iterable<Nota> all = service.getAllNote();
        all.forEach(nota ->
                System.out.println(nota)
        );
    }
}
