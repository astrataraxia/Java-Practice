package ex.payment;

public interface PayMethodRepository {

    void save(PayMethod payMethod);

    PayMethod findByUserIdAndCardNo(String userId, String cardNo);

    PayMethod findById(Integer id);
}
