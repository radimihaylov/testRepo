import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SimpleDataDrivenTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://pragmatic.bg/automation/lecture15/bmicalculator.html");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @DataProvider(name = "blaDataProvider")
    public Object[][] bmiDataProvider() {

        String [][] testData = {
                {"180", "80", "24.7", "Normal"},
                {"180", "50", "15.4", "Underweight"},
                {"180", "90", "27.8", "Overweight"},
                {"180", "120", "37.0", "Obesity"}
        };

        return testData;
    }

    @Test(dataProvider = "blaDataProvider")
    public void testBmiCalculatorNormal(String heightCms, String weightKg, String bmi, String bmiCategory) {
        WebElement heightCmsInputField = driver.findElement(By.id("heightCMS"));
        heightCmsInputField.clear();
        heightCmsInputField.sendKeys(heightCms);

        WebElement weightKgInputField = driver.findElement(By.id("weightKg"));
        weightKgInputField.clear();
        weightKgInputField.sendKeys(weightKg);

        WebElement calculateButton = driver.findElement(By.id("Calculate"));
        calculateButton.click();

        WebElement bmiField = driver.findElement(By.id("bmi"));
        String bmiFieldText = bmiField.getDomProperty("value");
        Assert.assertEquals(bmiFieldText, bmi);

        WebElement bmiCategoryField = driver.findElement(By.id("bmi_category"));
        String bmiCategoryFieldText = bmiCategoryField.getDomProperty("value");
        Assert.assertEquals(bmiCategoryFieldText, bmiCategory);
    }
}
