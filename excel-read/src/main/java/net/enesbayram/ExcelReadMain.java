package net.enesbayram;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ExcelReadMain {

	 static final String path = "C:/Users/sergul01/Desktop/test.xlsx";
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		int number=1;
		File file = new File("C:/resimurl.txt");
		FileWriter writer=null;
		
		try {
			FileInputStream fis = new FileInputStream(path);
			Workbook workbook  = new XSSFWorkbook(fis);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			while(sheets.hasNext()) {
				Sheet sheet = sheets.next();
				Iterator<Row> iteratorRow = sheet.iterator();
				while(iteratorRow.hasNext()) {
					Row row = iteratorRow.next();
					Iterator<Cell> iteratorCell = row.iterator();
					while(iteratorCell.hasNext()) {
						Cell cell = iteratorCell.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						if(cellValue.startsWith("http")) {
							try {
								writer = new FileWriter(file,true);
								driver.get(cellValue);
								
								if(cellValue.contains("cagri")) {
									WebElement element = driver.findElement(By.id("imgUrunResim"));
									writer.write(element.getAttribute("src").toString()+"\n");
									System.out.println(number + " ) Ürün URL : "+cellValue + "-----> RESİM URL : " + element.getAttribute("src"));
								}
								else if(cellValue.contains("happycenter")) {
									List<WebElement> findElements = driver.findElements(By.tagName("img"));
									for (WebElement webElement : findElements) {
										if(Integer.parseInt(webElement.getAttribute("width"))==228) {
											writer.write(webElement.getAttribute("src").toString()+"\n");
											System.out.println(number + " ) Ürün URL : "+cellValue + "-----> RESİM URL : " + webElement.getAttribute("src"));
										}
									}
								}
								if(writer!=null) {
									writer.close();
								}
							}catch (FileNotFoundException f) {
								System.out.println("Resim url dosyası bulunamadı");
							}
							catch (Exception e) {
								System.out.println("Ürün resmi bulunamadı");
								writer = new FileWriter(file,true);
								writer.write("Resim Bulunamadı\n");
								if(writer!=null) {
									writer.close();
								}
							}
							Thread.sleep(1000);
						}
					}
					System.out.println("");
					number++;
				}
				driver.quit();
			}
			workbook.close();
//			System.out.println("Toplam ADET : " + adet);
			
			
		} catch (Exception e) {
			System.out.println("HATA Oluştu : " + e.getMessage());
		}
	}
}
