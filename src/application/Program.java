package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		List<Sale> sale = new ArrayList<Sale>();

		System.out.print("Enter the file path: ");
		String sourceFile = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))) {

			String itemCSV = br.readLine();
			while (itemCSV != null) {
				String[] fields = itemCSV.split(",");
				sale.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2],
						Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				itemCSV = br.readLine();
			}

			Double priceAvg = sale.stream().map(x -> x.avaragePrice()).reduce(0.0, (x, y) -> x + y / sale.size());

			Comparator<Sale> check = Comparator.comparing(Sale::avaragePrice);

			List<Sale> topFiveSales = sale.stream().filter(x -> x.avaragePrice() > priceAvg && x.getYear() == 2016)
					.sorted(check.reversed()).limit(5).toList();

			List<Sale> loganSalesList = sale.stream().filter(x -> x.getSeller().equals("Logan")).toList();

			Double loganTotalSales = loganSalesList.stream().filter(x -> x.getMonth() == 1 || x.getMonth() == 7)
					.map(x -> x.getTotal()).reduce(0.0, (x, y) -> x + y);

			System.out.println();
			System.out.println("Cinco primeiras vendas de 2016 de maior preço médio:");
			topFiveSales.forEach(System.out::println);
			System.out.printf("Valor total vendido pelo vendedor Logan nos meses 1 e 7: %.2f", loganTotalSales);

		} catch (IOException e) {
			System.out.println("Error wiriting file " + e.getMessage());
		}

		sc.close();

	}

}
