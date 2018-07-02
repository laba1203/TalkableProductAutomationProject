package talkable.talkableSite.campaign.pages.editorPage;

import abstractObjects.AbstractElementsContainer;
import abstractObjects.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class ContainerViewRecords extends AbstractElementsContainer{

    private static final By containerElmntLctr = By.xpath("//ul[contains(@class, 'ac-editor-widget-navigation')]");
    private static final By recordsLctr = By.xpath("./div/li");
    private static final By createNewViewLctr = By.xpath("//a[contains(@class, 'create-new-view')]");

    private Element containerElement = new Element(containerElmntLctr);
    private ArrayList<ElmntViewRecord> records = new ArrayList<>();


    ContainerViewRecords(){
        setRecords();
    }

    private void setRecords(){
        List<WebElement> webElements = containerElement.getWebElement().findElements(recordsLctr);
        for (WebElement webElement :
                webElements) {
            ElmntViewRecord viewRecord = new ElmntViewRecord(new Element(webElement));
            records.add(viewRecord);
        }
    }

    public void selectViewByText(String viewName){
        Objects.requireNonNull(getViewRecord(viewName)).viewName.click();
    }

//    public void selectByIndex(int index){
//        records.get(index - 1).viewName.click();
//    }


    private ElmntViewRecord getViewRecord(String name){
        for (ElmntViewRecord record : records) {
            if(record.viewName.getText().equals(name)){
                return  record;
            }
        }
        Assert.fail("FAILED: There is no view records with name : <" + name + ">");
        return null;
    }

    PopupNewViewWarning clickCreateNewView(){
        new Element(createNewViewLctr, "Create New View Btn").click();
        return new PopupNewViewWarning();
    }




}
