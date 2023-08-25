package ex.payment;

public class PayMethodRegReq {
    //* 사용자 ID
    //* 카드번호
    //* 소유자명
    private String userId;
    private String cardNo;
    private String holderName;

    PayMethodRegReq(String userId, String cardNo, String holderName) {
        this.userId = userId;
        this.cardNo = cardNo;
        this.holderName = holderName;
    }

    public String getUserId() {
        return userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getHolderName() {
        return holderName;
    }
}
