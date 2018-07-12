package talkable.talkableSite.reports.couponLists;

import abstractObjects.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import talkable.talkableSite.AbstractTalkableSitePage;

import java.util.ArrayList;

public class CouponListPage extends AbstractTalkableSitePage{

    private static final By totalCouponsLctr = By.xpath("//dt[text()='Total Coupons']/following::dd[1]");
    private static final By statusLctr = By.xpath("//dt[text()='Status']/following::dd[1]/div");
    private static final By addCouponsBtnLctr = By.xpath("//a[contains(text(), 'Add coupons')]");
    private static final By listNameLctr = By.xpath("//h2");
    private static final By couponRowsLctr = By.xpath("//table[contains(@class, 'coupons-grid')]//tbody/tr");

    private Element totalCoupons = new Element(totalCouponsLctr);
    private Element status = new Element(statusLctr);
    private Element addCouponsBtn = new Element(addCouponsBtnLctr, "Add coupons button");
    private Element listName = new Element(listNameLctr);

    public String getCouponsTotalCount(){
        return totalCoupons.getText();
    }

    public String getStatus(){
        return status.getText();
    }

    public String getListName(){
        return listName.getText().substring(13);
    }

    public CouponListPage addCouponsManually(String coupons){
        addCouponsBtn.click();
        return new AddCouponsPage()
                .populateCouponCodesManually(coupons)
                .saveChanges();
    }

    public boolean isCouponPreset(String couponCode){
        for (Row row :
                getRows()) {
            if (row.getCode().equals(couponCode)){
                return true;
            }
        }
        return false;
    }


//    private Row findRow(String name){
//        for (Row row :
//                getRows()) {
//            if (row.getCode().equals(name)){
//                return row;
//            }
//        }
//        Assert.fail("Coupon list with name <" + name + "> was not found on the Coupon Lists report page.");
//        return null;
//    }

    private ArrayList<Row> getRows(){
        ArrayList<Row> rows = new ArrayList<>();
        for (Element element :
                getElementsList(couponRowsLctr)) {
            rows.add(new Row(element));
        }
        return rows;
    }


    private class Row{

        private By couponCodeLctr = By.xpath("./td[contains(@class, 'datagrid-code')]/a");

        WebElement element;


        Row(Element element){
            this.element = element.getWebElement();
        }

        String getCode(){
            return new Element(element.findElement(couponCodeLctr)).getText();
        }

//        void clickToCode(){
//            new Element(element.findElement(couponCodeLctr)).click();
//        }



    }


}