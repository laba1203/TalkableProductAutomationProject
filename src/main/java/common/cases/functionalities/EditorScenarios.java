package common.cases.functionalities;

import common.cases.CommonScenarios;

import org.openqa.selenium.By;
import org.testng.Assert;
import talkable.talkableSite.campaign.pages.campaignNavigationMenu.CampaignNavigationMenu;
import talkable.talkableSite.campaign.pages.editorPage.AbstractEditorPage;
import talkable.talkableSite.campaign.pages.editorPage.HtmlEditorPage;
import talkable.talkableSite.campaign.pages.editorPage.SimpleEditorPage;
import talkable.talkableSite.campaignsPage.Table;
import util.logging.Log;

public class EditorScenarios extends CommonScenarios{


    public static void openCampaignDetailsFromEditor(){
        new AbstractEditorPage()
                .campaignNavigationMenu
                .openDetailsPage();
        Log.logRecord("Campaign Details page is opened.");
    }

    public static void openSimpleEditorForCampaign(String campaignName, Table.Status campaignStatus){
        openCampaignDetailsPageFor(campaignName, campaignStatus);
        openSimpleEditor();
    }

    public static void openSimpleEditor(){
        new CampaignNavigationMenu().openEditorPage();
        Log.logRecord("Simple Editor is opened");
    }

    public static void openHtmlEditor(){
        new CampaignNavigationMenu()
                .openEditorPage()
                .openHtmlEditor();
        Log.logRecord("HTML & CSS Editor page is opened.");
    }

    public static void switchToSimpleEditor(){
        new HtmlEditorPage().openSimpleEditor();
    }

    public static void createNewView(String viewType){
        new HtmlEditorPage()
                .clickCreateNewView()
                .createNewView(viewType);
        Log.logRecord("New view created on HTML Editor. View type <" + viewType + ">");
    }

    public static void deleteView(String viewName){
        new HtmlEditorPage().deleteView(viewName);
        Log.logRecord("View <" + viewName + "> has been deleted.");
    }

    public static boolean isViewPresent(String viewName){
        return new AbstractEditorPage().isViewPresent(viewName);
    }



    public static void updateLocalization(SimpleEditorPage.LocalizationType type, String view, String localizationName, String newValue){
        SimpleEditorPage editorPage = new SimpleEditorPage(type);
        editorPage = editorPage.switchViewByNameOnSimpleEditor(view);
        editorPage.updateLocalization(type, localizationName, newValue);
        Log.logRecord("Localization <" + type + "." + localizationName + "> changed to <" + newValue + "> on view <" + view + ">");
    }

    public static void switchViewByName(String viewName){
        new AbstractEditorPage().switchView(viewName);
    }

    public static String getLocalizationValue(SimpleEditorPage.LocalizationType type, String localizationName){
        return new SimpleEditorPage(type)
                .getLocalizationValue(localizationName);
    }

    public static void createNewPreset(String presetName, String jsonVariables){
        HtmlEditorPage page = new AbstractEditorPage()
                .createNewPreset(presetName, jsonVariables);
        Assert.assertEquals(
                page.getSelectedPresetName(),
                presetName,
                "FAILED: New View Preset is not created. ");
        Log.logRecord("New Preset created. Name = <" + presetName + ">");
    }

    public static void deletePresetOnSimpleEditor(String presetName){
        new SimpleEditorPage().deletePresetOnSimpleEditor(presetName);
    }

    public static void deletePresetOnHtmlEditor(String presetName){
        new HtmlEditorPage().deletePresetOnHtmlEditor(presetName);
    }

    public static boolean isPresetPresent(String presetName){
        boolean result = new AbstractEditorPage().isPresetPreset(presetName);
        new AbstractEditorPage().clickToPresetDropDown();

        return result;
    }

    public static String getSelectedView(){
        String selectedView = new AbstractEditorPage().getSelectedViewName();
//        Log.logRecord("Selected view <" + selectedView + ">.");
        return selectedView;
    }

    public static void updateExtraEmailSubject(String newSubject){
        new HtmlEditorPage().updateExtraEmailSubject(newSubject);
    }

    public static String getElementTextFromPreview(By byInIFrame){
        return new AbstractEditorPage().getElementTextFromPreviewFrame(byInIFrame);
    }

    public static String getEmailSubjectFromPreview(){
        return new AbstractEditorPage().getEmailSubjectFromPreview();
    }

    public static void addNewHtmlCode(String html){
        new HtmlEditorPage().clearAndAddHtml(html);
    }

    public static void addNewCssCode(String css){
        new HtmlEditorPage().addCSS(css);
    }

    public static String getFirstCssRow(){
        return new HtmlEditorPage().getFirstCssRow();
    }

    public static void assertFirstCssRow(String expectedCssCode){
        new HtmlEditorPage().waitTillCssSectionLoaded();
        Assert.assertEquals(
                EditorScenarios.getFirstCssRow(),
                expectedCssCode,
                "CSS code was not updated in the row#1 on the HTML Editor page. <" + getSelectedView() + "> view.");
    }

    public static void searchLocalization(String localeName, SimpleEditorPage.LocalizationType mode){
        new SimpleEditorPage().searchLocale(localeName, mode);

        Assert.assertEquals(
                new SimpleEditorPage(mode).getFirstLocaleName().toLowerCase(),
                localeName + "#",
                "Incorrect locale is returned in localization grid when Search of Custom Settings was done. "
        );
    }

    public static void uploadNewImage(String name){
        new HtmlEditorPage()
                .uploadNewImage(name);
    }

    public static void uploadNewImageWhichExistsOnServer(String name){
        new HtmlEditorPage()
                .uploadNewImageWhichExistsOnServer(name);
    }

    public static String getFirstImageNameFromFiles(){
        return new HtmlEditorPage()
                .getFirstImageNameFromFiles();
    }

    public static void uploadFont(String fontName, String woffFile, String woff2File){
        new HtmlEditorPage().uploadFont(fontName, woffFile, woff2File);
    }





}
