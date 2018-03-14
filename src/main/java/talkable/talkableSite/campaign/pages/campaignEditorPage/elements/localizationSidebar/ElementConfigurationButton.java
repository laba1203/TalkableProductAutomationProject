package talkable.talkableSite.campaign.pages.campaignEditorPage.elements.localizationSidebar;

import abstractObjects.AbstractElement;
import org.openqa.selenium.By;

class ElementConfigurationButton extends AbstractElement{
    private static  final By locator = By.cssSelector(".Locale-entries-localizations-filters div:nth-of-type(4)");

    ElementConfigurationButton(){
        setWebElement(locator);
    }
}
