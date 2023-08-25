package ex.payment;

public class PayMethod {

    private Integer id;
    private String userId;
    private String cardNo;
    private String holder;

    public PayMethod(Integer id, String userId, String cardNo, String holder) {
        this.id = id;
        this.userId = userId;
        this.cardNo = cardNo;
        this.holder = holder;
    }

    public Integer getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getHolder() {
        return holder;
    }

    public void setId(Integer Id) {
        this.id = Id;
    }
}
