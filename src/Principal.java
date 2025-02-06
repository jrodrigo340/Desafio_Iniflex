import industrial.Funcionario;
import industrial.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Principal {

    public static void main(String[] args) {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        System.out.println("Inserindo funcionários...");
        Collections.addAll(funcionarios,
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        System.out.println("Removendo o funcionário João...");
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        DecimalFormat formato = new DecimalFormat("#,##0.00");

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formato.setDecimalFormatSymbols(symbols);

        System.out.println("Aumento de salário em 10%...");
        funcionarios.forEach(f -> f.aumentarSalario(10));

        System.out.println("\nExibindo todos os funcionários...");
        funcionarios.forEach(f -> System.out.printf(
                "Nome: %s | Data de Nascimento: %s | Salario: %s | Função: %s%n",
                f.getNome(),
                f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                formato.format(f.getSalario()),
                f.getFuncao()));

        System.out.println("\nAgrupando os funcionários por função: ");
        Map<String, ArrayList<Funcionario>> funcionariosPorFuncao = new HashMap<>();

        for (Funcionario f : funcionarios) {
            funcionariosPorFuncao.computeIfAbsent(f.getFuncao(), k -> new ArrayList<>()).add(f);
        }

        funcionariosPorFuncao.forEach((funcao, lista) ->{
            System.out.println("\nFunção: " + funcao);
            lista.forEach(f -> System.out.println(" - " + f.getNome()));
        });

        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:");
        funcionarios.stream()
                .filter(f -> {
                    int mes = f.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                })
                .forEach(f -> System.out.printf(
                        "Nome %s | Data de Nascimento: %s | Salário: %s | Funções: %s%n",
                        f.getNome(),
                        f.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        formato.format(f.getSalario()),
                        f.getFuncao()));

        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Pessoa::getDataNascimento));
        int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
        System.out.println("\nA pessoa com mais idade é: " + maisVelho.getNome() + " | Idade: " + idade);

        funcionarios.sort(Comparator.comparing(Pessoa::getNome));
        System.out.println("\nLista ordenada dos funcionarios: ");
        funcionarios.forEach(f -> System.out.println(f.getNome()));

        BigDecimal totalSalarios = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários: " + formato.format(totalSalarios));

        BigDecimal salarioMinimo = new BigDecimal("1212.00");

        System.out.println("\nQuantidade de salário mínimo que cada funcionário recebe: ");
        funcionarios.forEach(f -> {
            BigDecimal quantSalarioMin = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " recebe " + quantSalarioMin + " salários mínimos.");
        });
    }
}