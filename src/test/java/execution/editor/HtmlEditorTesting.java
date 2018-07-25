package execution.editor;

import common.cases.CommonScenarios;
import common.cases.functionalities.EditorScenarios;
import execution.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import talkable.common.elements.alert.Alert;
import talkable.talkableSite.campaign.pages.detailsPage.CampaignDetailsPage;
import util.EnvFactory;
import util.PropertyLoader;
import util.logging.Log;

import static talkable.common.CampaignPlacement.FloatingWidget;
import static talkable.common.CampaignPlacement.Standalone;
import static talkable.common.CampaignType.Invite;


/** Common Precondition:
 *      1. Accept Cookies Usage.
 *      2. Login
 *      3. Open Campaigns page.
 *      4. Deleted campaigns from the previous execution.
 *      5. Create New Campaign.
 *      6. Get link to campaign Details
 *
 * Scenario#1. Create new view (Advocate Offer Email).
     * 1. Open campaign details.
     * 3. Open Html Editor
     * 4. Create new View
     * 5. Verify new preset in the views list
     *
 *
 * Scenario#2. Delete view.
 *    1. Open campaign details.
 *    2. Open HTML Editor.
 *    3. Delete view.
 *    4. Verify that view is not present in the dropdown list
 *

   Scenario#3. Update email subject in Extra
        1. Open campaign details
        2.Open HTML Editor.
        3. Switch view to 'Friend share email'
        4. Open Extra popup.
        5. Update Email Subject
        6. Verify Email Subject in the preview frame.

 * Scenario#4. Edit HTML.
 *    1. Open campaign details.
 *    2. Open HTML Editor.
 *    3. Add copy locale, config locale and HTML to display those locales
 *    4. Verify copy locale in the preview frame.
 *    5. Verify config locale in the preview frame.
 *
 * Scenario#5. Edit CSS
 *
 *    */


	/*Link to test scenario: https://docs.google.com/spreadsheets/d/148sQEgtaeSuPrwor1s5HIFFqrgG4QEFR2_Gi2K3-r5k/edit#gid=0
     * */
public class HtmlEditorTesting extends BaseTest {

    private String user = PropertyLoader.loadProperty("talkable.user.editor");
    private String pswrd = EnvFactory.getPassword();
    private String standaloneCampaignDetailsPageUrl;
//    private String fwCampaignDetailsPageUrl;


    @BeforeClass
    public void loginAndCreateSite(){
        CommonScenarios.acceptCookiesUsage();
        CommonScenarios.loginAndCreateNewSite(user, pswrd);
    }

    @BeforeGroups("ui-actions")
    public void createStandaloneCampaign(){
        CommonScenarios.navigateToAdminUrl();
        CommonScenarios.openCampaignsPageAndCreateCampaign(Invite, Standalone);
        standaloneCampaignDetailsPageUrl = driver.getCurrentUrl();
    }

//    @BeforeGroups("edit-html-css")
//    public void createFwCampaign(){
//        CommonScenarios.navigateToAdminUrl();
//        CommonScenarios.openCampaignsPageAndCreateCampaign(Invite, FloatingWidget);
//        //todo: incorrect naming
//        standaloneCampaignDetailsPageUrl = driver.getCurrentUrl();
//    }

    @BeforeMethod
    public void openCampaignDetailsPage(){
        try {
            driver.navigate().to(standaloneCampaignDetailsPageUrl);
            new CampaignDetailsPage();
        }catch (UnhandledAlertException e){
            new Alert().confirm();
            openCampaignDetailsPage();
        }
        Log.logRecord("Navigated to Campaign Details page URL: " + standaloneCampaignDetailsPageUrl);
    }


    /*Scenarios1*/
    @Test(groups = {"ui-actions"})
    public void creteNewView(){
        String viewType = "Advocate Offer Email";
        String viewNameInHtmlEditor = "Advocate offer email";

        EditorScenarios.openHtmlEditor();
//        try {
            EditorScenarios.createNewView(viewType);
//        }catch (UnhandledAlertException e){
//            Log.debagRecord(" UnhandledAlertException is returned on EditorScenarios.createNewView().");
//            new Alert().confirm();
//            new CreateNewViewPage().createNewView(viewType);
//        }
        Assert.assertEquals(
                EditorScenarios.getSelectedView(),
                viewNameInHtmlEditor,
                "FAILED: Newly created view <" + viewNameInHtmlEditor + "> is not selected."
        );
    }


    /*Scenarios2*/
    @Test(groups = {"ui-actions"})
    public void deleteView(){
        String viewName = "Advocate share page";

        EditorScenarios.openHtmlEditor();
//        try {
            EditorScenarios.deleteView(viewName);

//        }catch (UnhandledAlertException e){
//            Log.debagRecord(" UnhandledAlertException is returned on EditorScenarios.deleteView().");
//            new Alert().confirm();
//            new HtmlEditorPage().waitViewDestroyedMsg();
//        }

        Assert.assertEquals(
                EditorScenarios.isViewPresent(viewName),
                false,
                "FAILED: View was not deleted."
        );
    }


    /*Scenario3*/
    @Test(groups = {"ui-actions"})
    public void updateEmailSubjectInExtraPopup(){
        String viewName = "Friend share email";
        String newSubject = "Automation test email subject";

        EditorScenarios.openHtmlEditor();
//        try {
            EditorScenarios.switchViewByName(viewName);
//        }catch (UnhandledAlertException e){
//            Log.debagRecord(" UnhandledAlertException is returned on EditorScenarios.updateEmailSubjectInExtraPopup().");
//            new Alert().confirm();
//        }
        EditorScenarios.updateExtraEmailSubject(newSubject);
        Assert.assertEquals(
                EditorScenarios.getEmailSubjectFromPreview(),
                newSubject,
                "FAILED: Email subject is not updated in the HTML Editor Preview frame."
        );

    }


    @Test(groups = {"ui-actions"})
    public void editHTML(){
//        {% assign copy_localization1 = "copy_localization1" | localize: "Text"%}
//        {% assign copy_localization2 = "copy_localization2" | localize: "Text2", "Test3"%}
//        <h1 class = 'copy-field-autotest'>{{copy_localization1}}</h1>
//        <br>
//        <button class = 'copy-field-autotest-2'>
//            {{copy_localization2}}
//        </button>

        //test data:
        String copyVariable = "copy_localization1";
        String copyValue = "TEXT";
        String copyVarClass = "copy-field-autotest";
        String configVariable = "config_localization1";
        String configValue1 = "TEXT1";
        String configValue2 = "TEXT2";
        String confVarClass = "conf-field-autotest";

        String liquidCode =
                "{% assign "+copyVariable+" = \""+copyVariable+"\" | localize: \""+copyValue+"\"%}\n" +
                "{% assign "+configVariable+" = \""+configVariable+"\" | localize: \""+configValue1+"\", \""+configValue2+"\"%}";

        String htmlCode =
                "        <h1 class = '"+copyVarClass+"'>{{"+copyVariable+"}}</h1>\n" +
                "        <br>\n" +
                "        <button class = '"+confVarClass+"'>\n" +
                "            {{"+configVariable+"}}\n" +
                "        </button>";
        String xpathOfCopyLocale = "//h1[@class='"+copyVarClass+"']";
        String xpathOfConfigLocale = "//button[@class='"+confVarClass+"']";

        EditorScenarios.openHtmlEditor();
        EditorScenarios.addNewHtmlCode(liquidCode + "\r\n" + htmlCode + "\r\n");

        Assert.assertEquals(
                EditorScenarios.getElementTextFromPreview(By.xpath(xpathOfCopyLocale)),
                copyValue,
                "Incorrect value in the Copy localization (xPath = '" + xpathOfCopyLocale + "')."
        );

        Assert.assertEquals(
                EditorScenarios.getElementTextFromPreview(By.xpath(xpathOfConfigLocale)),
                configValue1,
                "Incorrect value in the Config localization (xPath = '" + xpathOfConfigLocale + "')."
        );

    }


}
