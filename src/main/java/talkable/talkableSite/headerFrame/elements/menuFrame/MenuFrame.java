package talkable.talkableSite.headerFrame.elements.menuFrame;

import abstractObjects.AbstractElementsContainer;
import abstractObjects.Element;
import org.openqa.selenium.By;
import org.testng.Assert;
import talkable.addYourSitePage.AddSitePage;
import talkable.site.homePage.TkblHomePage;
import talkable.talkableSite.camapignPlacements.PageCampaignPlacements;
import talkable.talkableSite.createNewCampaignPage.CreateNewCampaignPage;
import talkable.talkableSite.fraud.settings.FraudSettingsPage;
import talkable.talkableSite.integrationPage.IntegrationPage;
import talkable.talkableSite.siteSettings.basic.SiteSettingsBasicTab;
import talkable.talkableSite.usersAndPrivileges.UsersAndPrivilegesPage;

public class MenuFrame extends AbstractElementsContainer {

    private static final By integrationBtnLctr = By.xpath("//ul[contains(@class, 'base-dropdown')]//*[contains(text(),'Integration')]");
    private static final By menuIsOpenedElement = By.cssSelector(".dropdown.open");
    private static final By usersAndPrivilegesLctr = By.xpath("//*[text()='Users & Privileges']");
    private ElmntLogoutButton logoutButton;


    public MenuFrame(){
        verifyIfMenuIsOpened();
        logoutButton = new ElmntLogoutButton();
    }

    private void verifyIfMenuIsOpened(){
        if(driver.findElements(menuIsOpenedElement).size() == 0){
            Assert.fail ("Menu popup is not opened");
        }
        System.out.println("Menu drop down is opened");
    }

    public AddSitePage clickCreateNewSiteButton(){
        new ElmntCreateNewSiteButton().click();
        return new AddSitePage();
    }

    public CreateNewCampaignPage clickCreateNewCampaignButton() {
        new ElmntCreateNewCampaignButton().click();
        return new CreateNewCampaignPage();
    }

    public PageCampaignPlacements clickCampaignPlacementsButton(){
        new ElmntCampaignPlacementsButton().click();
        return new PageCampaignPlacements();
    }

    public TkblHomePage clickLogout(){
        logoutButton = new ElmntLogoutButton();
        logoutButton.click();
        return new TkblHomePage();
    }

    public FraudSettingsPage clickFraudSettingsButton(){
        new ElmntFraudSettingsButton().click();
        return new FraudSettingsPage();
    }

    public SiteSettingsBasicTab clickSiteSettings(){
        new ElmntSiteSettingsButton().click();
        return new SiteSettingsBasicTab();
    }

    public IntegrationPage clickIntegration(){
        new Element(integrationBtnLctr, "Integration button").click();
        return new IntegrationPage();
    }

    public void clickToUsersAndPrivileges(){
        new Element(usersAndPrivilegesLctr, "'User & Privileges' button.").click();
    }

    public UsersAndPrivilegesPage openUserAndPrivilegesPage(){
        clickToUsersAndPrivileges();
        return new UsersAndPrivilegesPage();
    }


}
