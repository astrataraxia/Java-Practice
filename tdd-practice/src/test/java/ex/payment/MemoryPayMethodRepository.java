package ex.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class MemoryPayMethodRepository implements PayMethodRepository {

    private Map<Integer, PayMethod> map = new HashMap<>();

    @Override
    public void save(PayMethod payMethod) {
        if (payMethod.getId() == null) {
            payMethod.setId(nextId());
        }
        map.put(payMethod.getId(), payMethod);
    }

    private Integer nextId() {
        OptionalInt maxOpt = map.values().stream().mapToInt(pay -> pay.getId()).max();
        return maxOpt.orElse(0) + 1;
    }

    @Override
    public PayMethod findByUserIdAndCardNo(String userId, String cardNo) {
        Optional<PayMethod> payOpt = map.values()
                .stream().filter(pay -> pay.getUserId().equals(userId) && pay.getCardNo().equals(cardNo))
                .findFirst();
        return payOpt.orElse(null);
    }

    @Override
    public PayMethod findById(Integer id) {
        return map.get(id);
    }
}
