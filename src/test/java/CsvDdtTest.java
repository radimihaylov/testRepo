import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvDdtTest {

    private WebDriver driver;

    @DataProvider
    public Object[][] testData() throws IOException {
        return getTestData("src/main/resources/Data.csv");
    }

    public String[][] getTestData(String filePath) throws IOException {
        List<String[]> records = new ArrayList<String[]>();
        String record;
        FileReader fileReader = new FileReader(filePath);
        BufferedReader file = new BufferedReader(fileReader);
        record = file.readLine();
        while (record != null) {
            String fields[] = record.split(",");
            records.add(fields);
            record = file.readLine();
        }

        file.close();

        return records.toArray(new String[records.size()][]);
    }

    @BeforeMethod
    public void setUp() throws Exception {
        // Create a new instance of the Chrome driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://pragmatic.bg/automation/lecture15/bmicalculator.html");
    }

    @Test(dataProvider = "testData")
    public void testBMICalculator(String height, String weight, String expectedBmi, String expectedBmiCategory) throws Exception {
        WebElement heightField = driver.findElement(By.name("heightCMS"));
        heightField.clear();
        heightField.sendKeys(height);

        WebElement weightField = driver.findElement(By.name("weightKg"));
        weightField.clear();
        weightField.sendKeys(weight);

        WebElement calculateButton = driver.findElement(By.id("Calculate"));
        calculateButton.click();

        WebElement bmiLabel = driver.findElement(By.name("bmi"));
        Assert.assertEquals(bmiLabel.getAttribute("value"), expectedBmi);

        WebElement bmiCategoryLabel = driver.findElement(By.name("bmi_category"));
        Assert.assertEquals(bmiCategoryLabel.getAttribute("value"), expectedBmiCategory);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        // Close the browser
        driver.quit();
    }
}
