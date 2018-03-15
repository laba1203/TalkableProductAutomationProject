package talkable.talkableSite.campaign.pages.campaignRulesPage;

import abstractObjects.AbstractSelectElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

class ElmntFrOfferDeadlineMinutes extends AbstractSelectElement{

    ElmntFrOfferDeadlineMinutes(){
        WebElement webElement = getDriver().findElement(By.xpath("//select[@name='expires_at.hour']"));
        setWebElement(webElement);
    }
}
