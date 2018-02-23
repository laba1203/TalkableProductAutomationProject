package talkable.loginPage;


import abstractObjects.AbstractElementsContainer;
import talkable.talkableSite.headerFrame.Header;
import talkable.loginPage.elements.EmailInput;
import talkable.loginPage.elements.LoginButton;
import talkable.loginPage.elements.PasswordInput;


public class LoginPage extends AbstractElementsContainer {


    private static final String title = "Login | Talkable";

    //Elements

    private EmailInput emailInput;
    private PasswordInput passwordInput;
    private LoginButton loginButton;



    public LoginPage(){
        //verify if page is opened
        isPageOpened(title);


        //initiate web containers
        emailInput = new EmailInput();
        passwordInput = new PasswordInput();
        loginButton = new LoginButton();

    }



    public Header submitLoginForm(String email, String password){
        //fill Email
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
        return new Header();
    }





}
