package customerSite.talkableFrame.floatingWidget.advocateSharePage;

import abstractObjects.AbstractElement;
import org.openqa.selenium.By;

class ElmntShareViaEmailButton extends AbstractElement{

    private static  final By locator = By.cssSelector(".ac-share-via-email");

    ElmntShareViaEmailButton(){
        setWebElement(locator);
    }
}
