package store.chinaotec.com.medicalcare.javabean;

import java.io.Serializable;

/**
 * Created by hxk on 2017/7/13 0013 17:23
 * 注册账户时输入的银行,银行卡等信息
 */

public class BankInputInfo implements Serializable {
    public String banckCode;  //银行编码
    public String bankName;   //银行名字
    public String branchName; //支行名字
    public String banckCard;  //银行卡号\
    public String cardOwener; //银行卡开户人姓名

    public BankInputInfo(String banckCode, String bankName, String branchName, String banckCard, String cardOwener) {
        this.banckCode = banckCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.banckCard = banckCard;
        this.cardOwener = cardOwener;
    }

    public String getBanckCode() {
        return banckCode;
    }

    public void setBanckCode(String banckCode) {
        this.banckCode = banckCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBanckCard() {
        return banckCard;
    }

    public void setBanckCard(String banckCard) {
        this.banckCard = banckCard;
    }

    public String getCardOwener() {
        return cardOwener;
    }

    public void setCardOwener(String cardOwener) {
        this.cardOwener = cardOwener;
    }

    @Override
    public String toString() {
        return "BankInputInfo{" +
                "banckCode='" + banckCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", banckCard='" + banckCard + '\'' +
                ", cardOwener='" + cardOwener + '\'' +
                '}';
    }
}
