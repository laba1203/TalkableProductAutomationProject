package customerSite.talkableFrame.commonPages.advocateSharePage.invite;

import abstractObjects.AbstractElement;
import org.openqa.selenium.By;

class ElmntFriendEmailInput extends AbstractElement{

    private static  final By locator = By.cssSelector("[name = 'email_recipient_list']");

    ElmntFriendEmailInput(){
        setWebElement(locator);
    }
}
