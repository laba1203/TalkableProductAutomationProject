import common.cases.CommonScenarios;
import execution.BaseTest;
import org.testng.annotations.Test;
import talkable.talkableSite.siteSettings.basic.SiteSettingsBasicTab;
import util.Util;

public class Testing extends BaseTest {

    @Test
    public void login(){
        CommonScenarios.login("hutornoy@talkable.com", "organ_Telo23" );
        CommonScenarios.openSiteSettingsPage();
        CommonScenarios.updateSiteSettingsBasicTab("nameb2latest", "ididibladqa", "https://blabla.com/","Custom");
    }


        }
